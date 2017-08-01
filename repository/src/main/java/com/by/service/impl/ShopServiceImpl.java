package com.by.service.impl;

import java.util.ArrayList;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.by.json.ShopJson;
import com.by.model.Shop;
import com.by.repository.ShopRepository;
import com.by.service.ShopService;

/**
 * Created by yagamai on 15-12-1.
 */
@Service
@Transactional
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopRepository repository;
	@Autowired
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "shop")
	public Shop findByKey(String code) {
		Shop shop = repository.findByShopKey(code);
		shop.getRules().size();
		return shop;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Shop> findAll(String name, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Shop> c = cb.createQuery(Shop.class);
		Root<Shop> root = c.from(Shop.class);
		c.select(root);
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Shop.class)));
		List<Predicate> criteria = new ArrayList<>();
		if (!StringUtils.isEmpty(name))
			criteria.add(cb.like(root.get("name"), "%" + name + "%"));
		c.where(criteria.toArray(new Predicate[0]));
		cq.where(criteria.toArray(new Predicate[0]));

		List<Shop> lists = em.createQuery(c).setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize()).getResultList();
		Long count = em.createQuery(cq).getSingleResult();
		return new PageImpl<>(lists, pageable, count);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ShopJson> findAllJson(String name, Pageable pageable) {
		Page<Shop> pages = findAll(name, pageable);
		List<ShopJson> jsons = pages.getContent().stream().map(i -> i.toJson()).collect(Collectors.toList());
		return new PageImpl<>(jsons, pageable, pages.getTotalElements());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ShopJson> findShopByFloorJson(Shop shop) {
		List<Shop> shops = repository.findByFloorOrderBySortAsc(shop.getFloor());
		List<ShopJson> jsons = shops.stream().map(i -> i.toJson()).collect(Collectors.toList());
		return jsons;
	}

	public Page<Shop> findFirstPage(int size) {
		return repository.findAll(new PageRequest(0, size, Sort.Direction.ASC, "name"));
	}

	@Override
	public Shop save(Shop shop) {
		shop.setCreatedTime(Calendar.getInstance());
		shop.setUpdatedTime(Calendar.getInstance());
		shop.setFirstCategory(shop.getFirstCategory());
		shop.setSecondCategory(shop.getSecondCategory());
		shop.setFloor(shop.getFloor());
		return repository.save(shop);
	}

	@Override
	public Shop save(ShopJson shop) {
		Shop s = new Shop();
		s.setName(shop.getName());
		s.setShopKey(shop.getKey());
		return save(s);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "shop")
	public Shop findOne(int id) {
		return repository.findOne(id);
	}

	@Override
	public Shop update(ShopJson shop) {
		Shop source = repository.findOne(shop.getId());
		source.setName(shop.getName());
		source.setShopKey(shop.getKey());
		return source;
	}

	@Override
	@Cacheable("shop")
	@Transactional(readOnly = true)
	public List<Shop> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	@Override
	@CachePut("shop")
	@Transactional(readOnly = true)
	public List<Shop> refresh(Sort sort) {
		return repository.findAll(sort);
	}

	@Override
	public List<ShopJson> toJson(List<Shop> shops) {
		return shops.stream().map(i -> {
			ShopJson json = new ShopJson();
			json.setId(i.getId());
			json.setName(i.getName());
			json.setImgHref(i.getImgHref());
			json.setKey(i.getShopKey());
			json.setDescpriton(i.getDescpriton());
			json.setShopImg(i.getShopImg());
			json.setShopImg2(i.getShopImg2());
			json.setPhone(i.getPhone());
			json.setBusinessHour(i.getBusinessHour());
			return json;
		}).collect(Collectors.toList());
	}

	@Override
	@CachePut(value = "shop", key = "#shop.id")
	public Shop update(Shop shop) {
		Shop source = findOne(shop.getId());
		source.setName(shop.getName());
		source.setShopKey(shop.getShopKey());
		source.setImgHref(shop.getImgHref());
		source.setUpdatedBy(shop.getUpdatedBy());
		source.setUpdatedTime(Calendar.getInstance());
		source.setFirstCategory(shop.getFirstCategory());
		source.setSecondCategory(shop.getSecondCategory());
		source.setFloor(shop.getFloor());
		source.setAddress(shop.getAddress());
		source.setBusinessHour(shop.getBusinessHour());
		source.setPhone(shop.getPhone());
		source.setDescpriton(shop.getDescpriton());
		source.setShopImg(shop.getShopImg());
		source.setShopImg2(shop.getShopImg2());
		source.setIsBigLogo(shop.getIsBigLogo());
		source.setSort(shop.getSort());
		return repository.save(source);
	}

	@Override
	@Transactional(readOnly = true)
	public Long countByName(String name) {
		return repository.countByName(name);
	}

	@Override
	public Long countByShopKey(String key) {
		return repository.countByShopKey(key);
	}

	@Override
	public Shop findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public Shop findByShopKey(String name) {
		return repository.findByShopKey(name);
	}

}
