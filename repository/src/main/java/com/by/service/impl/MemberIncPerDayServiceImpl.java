package com.by.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.repository.MemberIncPerDayRepository;
import com.by.service.MemberIncPerDayService;

/**
 * Created by yagamai on 16-3-15.
 */
@Service
@Transactional
public class MemberIncPerDayServiceImpl implements MemberIncPerDayService {
	@Autowired
	private MemberIncPerDayRepository repository;
	@Autowired
	private EntityManager em;

	@Override
	public List<Object[]> findByYearAndMonth(int year, int month) {
		return repository.findByYearAndMonth(year, month);
	}

	@Override
	public Integer findMaxNumber() {
		Integer max = em.createQuery("select sum() from MemberIncPerDay m group by(month(m.createdTime)) order by m.number desc", Integer.class)
				.setMaxResults(1).getFirstResult();
		return max;
	}
}
