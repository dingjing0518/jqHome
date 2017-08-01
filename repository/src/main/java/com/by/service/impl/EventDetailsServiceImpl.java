package com.by.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.by.form.EventDetailsForm;
import com.by.json.EventDetailsJson;
import com.by.model.EventDetails;
import com.by.repository.EventDetailsRepository;
import com.by.service.EventDetailsService;
@Service
public class EventDetailsServiceImpl implements EventDetailsService {
	@Autowired
	private EntityManager em;
	@Autowired
	private EventDetailsRepository repository;
	@Override
	public Page<EventDetailsJson> findAll(EventDetailsForm form, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<EventDetails> c = cb.createQuery(EventDetails.class);
		Root<EventDetails> root = c.from(EventDetails.class);
		c.select(root);
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(EventDetails.class)));
		List<Predicate> criteria = new ArrayList<>();
		
		if (form.getBeginTime() != null)
			criteria.add(cb.greaterThanOrEqualTo(root.get("beginTime"), form.getBeginTime()));
		if (form.getEndTime() != null) {
			form.getEndTime().add(Calendar.HOUR, 23);
			form.getEndTime().add(Calendar.MINUTE, 59);
			form.getEndTime().add(Calendar.SECOND, 59);
			criteria.add(cb.lessThanOrEqualTo(root.get("beginTime"), form.getEndTime()));
		}
		c.where(criteria.toArray(new Predicate[0]));
		c.orderBy(cb.desc(root.get("beginTime")));
		cq.where(criteria.toArray(new Predicate[0]));

		List<EventDetails> lists = em.createQuery(c).setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize()).getResultList();
		Long count = em.createQuery(cq).getSingleResult();

		List<EventDetailsJson> results = lists.stream().map(i -> new EventDetailsJson(i)).collect(Collectors.toList());
		return new PageImpl<>(results, pageable, count);
	}
	@Override
	public EventDetails save(EventDetails eventDetails) {
		return repository.save(eventDetails);
	}
	@Override
	public EventDetails findOne(int id) {
		return repository.findOne(id);
	}

	@Override
	public Optional<EventDetails> findById(int id) {
		return repository.findById(id);
	}
	@Override
	public EventDetails update(EventDetails eventDetails) {
		EventDetails source = findOne(eventDetails.getId());
		source.setTitle(eventDetails.getTitle());
		source.setContent(eventDetails.getContent());
		source.setCoverImg(eventDetails.getCoverImg());
		source.setUrl(eventDetails.getUrl());
		source.setBeginTime(eventDetails.getBeginTime());
		source.setEndTime(eventDetails.getEndTime());
		source.setIsNewActivity(eventDetails.getIsNewActivity());
		return repository.save(source);
	}
	@Override
	public List<EventDetails> findAll(Sort sort) {
		return repository.findAll(sort);
	}
}
