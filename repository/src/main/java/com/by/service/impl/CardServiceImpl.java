package com.by.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.json.CardJson;
import com.by.model.Card;
import com.by.model.CardRule;
import com.by.model.Member;
import com.by.model.RuleCategory;
import com.by.repository.CardRepository;
import com.by.service.CardRuleService;
import com.by.service.CardService;
import com.by.service.MemberService;
import com.by.typeEnum.ScoreHistoryEnum;
import com.by.typeEnum.ValidEnum;
import com.google.common.collect.Lists;

@Service
@Transactional
public class CardServiceImpl implements CardService {
    private static final RuleCategory registerCategory = new RuleCategory(10);
    private static final String registerReason = "注册";
    @Autowired
    private CardRepository repository;
    @Autowired
    private EntityManager em;
    @Autowired
    private CardRuleService cardRuleService;
    @Autowired
    private MemberService memberService;

    @Override
    @Transactional(readOnly = true)
    public Card findOne(int id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Card> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("card")
    public List<Card> findAllCache() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Card update(Card card) {
        Card source = repository.findOne(card.getId());
        source.setUpdatedBy(card.getUpdatedBy());
        source.setName(card.getName());
        source.setImgHref(card.getImgHref());
        source.setInitScore(card.getInitScore());
        source.setSummary(card.getSummary());
        return source;
    }

    @Override
    @Transactional(readOnly = true)
    public Card findByIdAndValid(int id, ValidEnum valid) {
        return repository.findByIdAndValid(id, valid);
    }

    @Override
    public Card save(Card card) {
        card.setValid(ValidEnum.VALID);
        return repository.save(card);
    }

    @Override
    @Transactional(readOnly = true)
    @CachePut("card")
    public List<Card> refreshCache() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Card save(CardJson json) {
        Card card = new Card();
        card.setName(json.getName());
        card.setInitScore(json.getInitScore());
        return save(card);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAllAuditRevision(Long id) {
        AuditReader auditReader = AuditReaderFactory.get(em);
        return auditReader.createQuery().forRevisionsOfEntity(Card.class, true, true).add(AuditEntity.id().eq(id))
                .addOrder(AuditEntity.revisionNumber().asc()).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Card> findByValid(ValidEnum valid, Pageable pageable) {
        Page<Card> cards;
        if (valid != null) {
            cards = repository.findByValid(valid, pageable);
        } else {
            cards = repository.findAll(pageable);
        }
        return cards;
    }

    @Override
    public Long countByName(String name) {
        return repository.countByName(name);
    }

    @Override
    public Card findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Card validOrInValid(Card c) {
        Card card = repository.findOne(c.getId());
        if (card.getValid().equals(ValidEnum.VALID))
            card.setValid(ValidEnum.INVALID);
        else
            card.setValid(ValidEnum.VALID);
        return card;
    }

    @Override
    public Member addSignUpScore(Member member) {
        Card card = repository.findOne(member.getCard().getId());
        List<CardRule> rules = cardRuleService
                .findByRuleCategoryAndCardAndValid(registerCategory, card, ValidEnum.VALID).stream()
                .collect(Collectors.toList());
        int score = card.getInitScore();
        if (rules.size() > 0) {
            score += Card.getMaxScore(rules);
        }
        return memberService.addScore(member, score, registerReason, ScoreHistoryEnum.SIGNUP);
    }

	@Override
	public List<Card> findByValid(ValidEnum valid) {
		return repository.findByValid(valid);
	}
}
