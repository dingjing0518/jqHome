package com.by.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.form.BaseCouponForm;
import com.by.model.Rule;
import com.by.repository.RuleRepository;
import com.by.service.RuleService;
import com.by.typeEnum.CouponAdminStateEnum;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-11-26.
 */
@Service
@Transactional
public class RuleServiceImpl implements RuleService {
	@Autowired
	private RuleRepository repository;

	@Override
	@CachePut({ "ruleCard" })
	public Rule save(Rule rule) {
		return repository.save(rule);
	}

	@Override
	@CacheEvict({ "ruleCard" })
	public Rule update(Rule rule) {
		Rule source = repository.findOne(rule.getId());
		source.setBeginTime(rule.getBeginTime());
		return source;
	}

	@Override
	public <T extends Rule> List<Predicate> getPredicateList(BaseCouponForm form, Root<T> root, CriteriaBuilder cb) {
		List<Predicate> criteria = new ArrayList<>();
		if (form.getState() != null) {
			Calendar today = Calendar.getInstance();
			if (form.getState().equals(CouponAdminStateEnum.CLOSED)) {
				criteria.add(cb.equal(root.get("valid"), ValidEnum.INVALID));
			} else if (form.getState().equals(CouponAdminStateEnum.NOEXPIRE)) {
				criteria.add(cb.greaterThan(root.get("beginTime"), today));
				criteria.add(cb.and(cb.equal(root.get("valid"), ValidEnum.VALID)));
			} else if (form.getState().equals(CouponAdminStateEnum.USING)) {
				criteria.add(cb.or(
						cb.and(cb.lessThanOrEqualTo(root.get("beginTime"), today),
								cb.greaterThanOrEqualTo(root.get("endTime"), today)),
						cb.and(cb.isNull(root.get("beginTime"))), cb.isNull(root.get("endTime"))));
				criteria.add(cb.and(cb.equal(root.get("valid"), ValidEnum.VALID)));
			} else if (form.getState().equals(CouponAdminStateEnum.EXPIRE)) {
				criteria.add(cb.lessThan(root.get("endTime"), today));
				criteria.add(cb.and(cb.notEqual(root.get("valid"), ValidEnum.INVALID)));
			}
		}
		if (form.getBeginTime() != null) {
			criteria.add(cb.or(cb.greaterThanOrEqualTo(root.get("beginTime"), form.getBeginTime()),
					cb.isNull(root.get("endTime"))));
		}
		if (form.getEndTime() != null) {
			criteria.add(cb.or(cb.lessThanOrEqualTo(root.get("beginTime"), form.getEndTime()),
					cb.isNull(root.get("endTime"))));
		}
		return criteria;
	}

	@Override
	@Transactional(readOnly = true)
	public Long countByName(String name) {
		return repository.countByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Rule findByName(String name) {
		return repository.findByName(name);
	}
}
