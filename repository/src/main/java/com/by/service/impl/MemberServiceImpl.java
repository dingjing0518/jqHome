package com.by.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.by.exception.CardNotValidException;
import com.by.exception.MemberNotFoundException;
import com.by.exception.NotEnoughScoreException;
import com.by.exception.NotFoundException;
import com.by.form.AdminMemberForm;
import com.by.json.MemberJson;
import com.by.model.Card;
import com.by.model.Member;
import com.by.model.ScoreAddHistory;
import com.by.repository.MemberRepository;
import com.by.service.CardService;
import com.by.service.MemberService;
import com.by.service.ScoreAddHistoryService;
import com.by.service.ScoreHistoryService;
import com.by.typeEnum.ScoreHistoryEnum;
import com.by.typeEnum.ValidEnum;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberRepository repository;
	@Autowired
	private ScoreAddHistoryService scoreAddHistoryService;
	@Autowired
	private ScoreHistoryService scoreHistoryService;
	@Autowired
	private CardService cardService;
	@Autowired
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public Optional<Member> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Member findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public Member save(Member member) {
		Card card = cardService.findByIdAndValid(member.getCard().getId(), ValidEnum.VALID);
		if (card == null)
			throw new CardNotValidException();
		member.setValid(ValidEnum.VALID);
		return repository.save(member);
	}

	@Override
	public Long countByName(String name) {
		return repository.countByName(name);
	}

	@Override
	public Member addScore(Member member, int total, String reason, ScoreHistoryEnum type) {
		Member source = repository.findOne(member.getId());
		source.setScore(source.getScore() + total);
		scoreAddHistoryService.save(member, total, reason);
		scoreHistoryService.save(member, total, reason, type);
		source.setSumScore(source.getSumScore() + total);
		return source;
	}
	
	@Override
	public Member addScore(Member member, int total, String reason, ScoreHistoryEnum type, String remark) {
		Member source = repository.findOne(member.getId());
		source.setScore(source.getScore() + total);
		scoreAddHistoryService.save(member, total, reason);
		scoreHistoryService.save(member, total, reason, type, remark);
		source.setSumScore(source.getSumScore() + total);
		return source;
	}

	public Member minusScoreBase(Member member, int total, String reason, ScoreHistoryEnum type,
			Consumer<Member> consumer) {
		Member source = repository.findOne(member.getId());
		if (source == null)
			throw new MemberNotFoundException();
		List<ScoreAddHistory> historyList = scoreAddHistoryService.findByMember(member);
		if (source.getScore() < total) {
			consumer.accept(source);
			return source;
		}
		List<ScoreAddHistory> results = extractScoreHistory(historyList, total);
		if (results.size() == 0) {
			return source;
		}
		if (total > 0) {
			int sum = results.stream().map(ScoreAddHistory::getTotal).reduce((i, s) -> i + s).get();
			if (sum > total) {
				if (results.size() == 1) {
					ScoreAddHistory s = results.get(0);
					s.setTotal(s.getTotal() - total);
				} else {
					ScoreAddHistory last = results.get(results.size() - 1);
					last.setTotal(sum - total);
					results.remove(last);
					for (ScoreAddHistory h : results) {
						scoreAddHistoryService.delete(h.getId());
					}
				}
			} else if (sum == total) {
				for (ScoreAddHistory h : results) {
					scoreAddHistoryService.delete(h.getId());
				}
			}
		}
		if (source.getScore() >= total) {
			scoreHistoryService.save(member, -total, reason, type);
		} else {
			scoreHistoryService.save(member, -member.getScore(), reason, type);
		}
		source.setScore(source.getScore() - total);
		return source;
	}

	@Override
	public Member minusScore(Member member, int total, String reason, ScoreHistoryEnum type) {
		return minusScoreBase(member, total, reason, type, n -> {
			throw new NotEnoughScoreException();
		});
	}

	@Override
	public Member minusScoreAll(Member member, int total, String reason, ScoreHistoryEnum type) {
		return minusScoreBase(member, total, reason, type, n -> {
			scoreAddHistoryService.deleteByMember(n);
			scoreHistoryService.save(member, n.getScore(), reason, type);
			n.setScore(0);
		});
	}
	
	@Override
	public Member minusScoreAll(Member member, int total, String reason, ScoreHistoryEnum type, String remark) {
		return minusScoreBase(member, total, reason, type, n -> {
			scoreAddHistoryService.deleteByMember(n);
			scoreHistoryService.save(member, n.getScore(), reason, type, remark);
			n.setScore(0);
		});
	}

	/*
	 * find which scoreAddHistory should deleted from history
	 *
	 * @param allList member all scoreAddedHistory list
	 */
	@Override
	public List<ScoreAddHistory> extractScoreHistory(List<ScoreAddHistory> allList, int total) {
		List<ScoreAddHistory> results = new ArrayList<>();
		int init = 0;
		for (ScoreAddHistory s : allList) {
			if (init < total) {
				init += s.getTotal();
				results.add(s);
			} else {
				break;
			}
		}
		return results;
	}

	@Override
	@Transactional(readOnly = true)
	public Member findOne(Long id) {
		Member member = repository.findOne(id);
		member.getMemberDetail().getAddress();
		return member;
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable("member")
	public Member findOneCache(Long id) {
		Member member = repository.findOne(id);
		if (member == null) {
			throw new NotFoundException();
		}
		member.getMemberDetail().getAddress();
		return member;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<MemberJson> findAll(AdminMemberForm form, Pageable pageable, ValidEnum valid) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> c = cb.createQuery(Member.class);
		Root<Member> root = c.from(Member.class);
		c.select(root);
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Member.class)));
		List<Predicate> criteria = new ArrayList<>();

		if (!StringUtils.isEmpty(form.getMobile())){
			criteria.add(cb.like(root.get("name"), "%"+form.getMobile()+"%"));
		}
		if (form.getCard() != null)
			criteria.add(cb.equal(root.get("card"), new Card(form.getCard())));
		if (form.getBeginTime() != null)
			criteria.add(cb.greaterThanOrEqualTo(root.get("createdTime"), form.getBeginTime()));
		if (form.getEndTime() != null) {
			form.getEndTime().add(Calendar.HOUR, 23);
			form.getEndTime().add(Calendar.MINUTE, 59);
			form.getEndTime().add(Calendar.SECOND, 59);
			criteria.add(cb.lessThanOrEqualTo(root.get("createdTime"), form.getEndTime()));
		}
		if (valid != null)
			criteria.add(cb.equal(root.get("valid"), valid));
		c.where(criteria.toArray(new Predicate[0]));
		c.orderBy(cb.desc(root.get("createdTime")));
		cq.where(criteria.toArray(new Predicate[0]));

		List<Member> lists = em.createQuery(c).setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize()).getResultList();
		Long count = em.createQuery(cq).getSingleResult();

		List<MemberJson> results = lists.stream().map(i -> new MemberJson(i)).collect(Collectors.toList());
		return new PageImpl<>(results, pageable, count);
	}

	@Override
	@Transactional(readOnly = true)
	public Long countByCard(Card card) {
		return repository.countByCard(card);
	}

	@Override
	public Member validateOrInValidate(Member member, String name) {
		Member m = repository.findOne(member.getId());
		m.getMemberDetail().getRealName();
		if (m.getValid().equals(ValidEnum.VALID)) {
			m.setValid(ValidEnum.INVALID);
		} else {
			m.setInValidBy(name);
			m.setInValidTime(Calendar.getInstance());
			m.setValid(ValidEnum.VALID);
		}
		return m;
	}

	@CacheEvict(value = "member")
	public Member refresh(Long id) {
		return repository.findOne(id);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Member update(Member member) {
		Member m = repository.findOne(member.getId());
		m.setScore(member.getScore());
		return m;
	}
}
