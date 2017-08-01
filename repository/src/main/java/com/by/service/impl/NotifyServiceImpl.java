package com.by.service.impl;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.by.json.NotifyJson;
import com.by.model.Brand;
import com.by.model.Notify;
import com.by.repository.NotifyRepository;
import com.by.service.NotifyService;
@Service
public class NotifyServiceImpl implements NotifyService {
	@Autowired
	private NotifyRepository repository;
	@Autowired
	private EntityManager em;
	public List<Notify> findAll() {
		return repository.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public List<Notify> findAll(Sort sort) {
		return repository.findAll(sort);
	}
	@Override
	@Transactional(readOnly = true)
	public Page<Notify> findAll(String name, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Notify> c = cb.createQuery(Notify.class);
		Root<Notify> root = c.from(Notify.class);
		c.select(root);
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Brand.class)));
		List<Predicate> criteria = new ArrayList<>();
		if (!StringUtils.isEmpty(name))
			criteria.add(cb.like(root.get("name"), "%" + name + "%"));
		c.where(criteria.toArray(new Predicate[0]));
		cq.where(criteria.toArray(new Predicate[0]));
		List<Notify> lists = em.createQuery(c).setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize()).getResultList();
		Long count = em.createQuery(cq).getSingleResult();
		return new PageImpl<>(lists, pageable, count);
	}
	@Override
	@Transactional(readOnly = true)
	public Page<NotifyJson> findAllJson(String name, Pageable pageable) {
		Page<Notify> pages = findAll(name, pageable);
		List<NotifyJson> jsons = pages.getContent().stream().map(i -> i.toJson()).collect(Collectors.toList());
		return new PageImpl<>(jsons, pageable, pages.getTotalElements());
	}
	@Override
	public List<NotifyJson> toJson(List<Notify> notifys) {
		return notifys.stream().map(i -> {
			NotifyJson json = new NotifyJson();
			json.setId(i.getId());
			json.setTitle(i.getTitle());
			json.setContent(i.getContent());
			return json;
		}).collect(Collectors.toList());
	}
	@Override
	public Notify save(Notify notify) {
		return repository.save(notify);
	}
	@Override
	public Notify save(NotifyJson notify) {
		Notify b = new Notify();
		b.setId(notify.getId());
		b.setTitle(notify.getTitle());
		b.setSort(notify.getSort());
		return save(b);
	}
	@Override
	public List<Notify> refresh(Sort sort) {
		return repository.findAll(sort);
	}
	@Override
	@Transactional(readOnly = true)
	public Notify findOne(int id) {
		return repository.findOne(id);
	}
	@Override
	public Notify update(NotifyJson notify) {
		Notify source = repository.findOne(notify.getId());
		source.setTitle(notify.getTitle());
		source.setContent(notify.getContent());
		source.setSort(notify.getSort());
		return source;
	}
	@Override
	public Notify update(Notify notify) {
		Notify source = findOne(notify.getId());
		source.setTitle(notify.getTitle());
		source.setContent(notify.getContent());
		source.setSort(notify.getSort());
		return repository.save(source);
	}
}
