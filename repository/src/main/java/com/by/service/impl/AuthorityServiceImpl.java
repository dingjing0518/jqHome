package com.by.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.by.exception.NotFoundException;
import com.by.json.AuthorityJson;
import com.by.model.Authority;
import com.by.repository.AuthorityRepository;
import com.by.service.AuthorityService;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 16-1-4.
 */
@Component
@Transactional
public class AuthorityServiceImpl implements AuthorityService {
	@Autowired
	private AuthorityRepository repository;
	@Autowired
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public Page<Authority> findAll(String name, Pageable pageable) {
		if (name != null) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Authority> c = cb.createQuery(Authority.class);
			Root<Authority> root = c.from(Authority.class);
			c.select(root);
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			cq.select(cb.count(cq.from(Authority.class)));
			c.where(cb.equal(root.get("name"), name));
			cq.where(cb.equal(root.get("name"), name));
			List<Authority> lists = em.createQuery(c)
					.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
					.setMaxResults(pageable.getPageSize()).getResultList();
			Long count = em.createQuery(cq).getSingleResult();
			return new PageImpl<>(lists, pageable, count);
		}
		return repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Authority findOne(int id) {
		Authority authority = repository.findOne(id);
		authority.getMenus().size();
		return authority;
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "auth")
	public List<Authority> findAll() {
		return repository.findAll(new Sort(Sort.Direction.ASC, "name"));
	}

	@Override
	public Page<AuthorityJson> toJson(Page<Authority> lists, Pageable pageable) {
		List<Authority> authorities = lists.getContent();
		List<AuthorityJson> json = authorities.stream().map(i -> new AuthorityJson(i)).collect(Collectors.toList());
		return new PageImpl<>(json, pageable, lists.getTotalElements());
	}

	@Override
	public Authority save(Authority authority) {
		authority.setValid(ValidEnum.VALID);
		return repository.save(authority);
	}

	@Override
	public Authority update(Authority authority) {
		Authority source = repository.findOne(authority.getId());
		source.setUpdatedBy(authority.getUpdatedBy());
		source.setName(authority.getName());
		source.setMenus(authority.getMenus());
		return source;
	}

	@Override
	public Long countByName(String name) {
		return repository.countByName(name);
	}

	@Override
	public Authority findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public Authority validOrInValid(Authority authority) {
		Authority source = repository.findOne(authority.getId());
		if (source == null)
			throw new NotFoundException();
		if (source.getValid().equals(ValidEnum.VALID))
			source.setValid(ValidEnum.INVALID);
		else
			source.setValid(ValidEnum.VALID);
		return source;
	}

	@Override
	@Transactional(readOnly = true)
	@CachePut(value = "auth")
	public List<Authority> refreshCache() {
		return repository.findAll(new Sort(Sort.Direction.ASC, "name"));
	}
}
