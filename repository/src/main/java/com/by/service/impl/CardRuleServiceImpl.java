package com.by.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.form.CouponQueryForm;
import com.by.json.RuleJson;
import com.by.model.Card;
import com.by.model.CardRule;
import com.by.model.RuleCategory;
import com.by.repository.CardRuleRepository;
import com.by.service.CardRuleService;
import com.by.service.RuleService;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-15.
 */
@Service
@Transactional
public class CardRuleServiceImpl implements CardRuleService {
	@Autowired
	private CardRuleRepository repository;
	@Autowired
	private EntityManager em;
	@Autowired
	private RuleService ruleService;

	@Override
	public List<CardRule> findByRuleCategoryAndCardAndValid(RuleCategory category, Card card, ValidEnum valid) {
		return repository.findByRuleCategoryAndCardAndValid(category, card, ValidEnum.VALID);
	}

	@Override
	public List<CardRule> findByRuleCategoryAndCard(RuleCategory category, Card card) {
		return repository.findByRuleCategoryAndCard(category, card);
	}

	@Override
	public CardRule save(CardRule rule) {
		return repository.save(rule);
	}

	@Override
	public CardRule findOne(int id) {
		return repository.findOne(id);
	}

	@Override
	@CachePut(value = "rule", key = "#cardRule.id")
	public CardRule update(CardRule cardRule) {
		CardRule rule = repository.findOne(cardRule.getId());
		rule.setCard(cardRule.getCard());
		rule.setName(cardRule.getName());
		rule.setBeginTime(cardRule.getBeginTime());
		rule.setEndTime(cardRule.getEndTime());
		rule.setUpdatedBy(cardRule.getUpdatedBy());
		rule.setRate(cardRule.getRate());
		rule.setScore(cardRule.getScore());
		return rule;
	}

	@Override
	public CardRule valid(int id) {
		CardRule r = repository.findOne(id);
		Calendar today = Calendar.getInstance();
		if (r.getValid().equals(ValidEnum.INVALID)) {
			//长期有效规则设定
			if(r.getBeginTime() == null && r.getEndTime() == null) {
				r.setValid(ValidEnum.VALID);
			} else {
				if (r.getBeginTime() != null && r.getEndTime() != null && r.getBeginTime().after(today)
						&& r.getEndTime().after(today))
					r.setValid(ValidEnum.VALID);
			}
		} else
			r.setValid(ValidEnum.INVALID);
		return r;
	}

	@Override
	public Page<RuleJson> findAll(CouponQueryForm form, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CardRule> c = cb.createQuery(CardRule.class);
		Root<CardRule> root = c.from(CardRule.class);
		c.select(root);
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(CardRule.class)));
		List<Predicate> ls = ruleService.getPredicateList(form, root, cb);
		if (form.getCard() != null)
			ls.add(cb.equal(root.get("card"), form.getCard()));
		Predicate[] predicates = ls.toArray(new Predicate[0]);
		c.where(predicates);
		c.orderBy(cb.desc(root.get("beginTime")));
		cq.where(predicates);

		List<CardRule> lists = em.createQuery(c).setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize()).getResultList();
		Long count = em.createQuery(cq).getSingleResult();
		List<RuleJson> results = lists.stream().map(i -> new RuleJson(i)).collect(Collectors.toList());
		return new PageImpl<>(results, pageable, count);
	}

	@Override
	public Long countByName(String name) {
		return repository.countByName(name);
	}

	@Override
	public CardRule findByName(String name) {
		return repository.findByName(name);
	}
}
