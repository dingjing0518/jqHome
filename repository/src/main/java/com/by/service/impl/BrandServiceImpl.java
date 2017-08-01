package com.by.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.by.json.BrandJson;
import com.by.model.Brand;
import com.by.model.ShopCategory;
import com.by.repository.BrandRepository;
import com.by.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {
	@Resource
	private BrandRepository brandRepository;
	@Resource
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public Page<Brand> findAll(String name, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Brand> c = cb.createQuery(Brand.class);
		Root<Brand> root = c.from(Brand.class);
		c.select(root);
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Brand.class)));
		List<Predicate> criteria = new ArrayList<>();
		if (!StringUtils.isEmpty(name))
			criteria.add(cb.like(root.get("name"), "%" + name + "%"));
		c.where(criteria.toArray(new Predicate[0]));
		cq.where(criteria.toArray(new Predicate[0]));

		List<Brand> lists = em.createQuery(c).setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize()).getResultList();
		Long count = em.createQuery(cq).getSingleResult();
		return new PageImpl<>(lists, pageable, count);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BrandJson> findAllJson(String name, Pageable pageable) {
		Page<Brand> pages = findAll(name, pageable);
		List<BrandJson> jsons = pages.getContent().stream().map(i -> i.toJson()).collect(Collectors.toList());
		return new PageImpl<>(jsons, pageable, pages.getTotalElements());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Brand> findAll(Sort sort) {
		return brandRepository.findAll(sort);
	}

	@Override
	public List<BrandJson> toJson(List<Brand> brands) {
		return brands.stream().map(i -> {
			BrandJson json = new BrandJson();
			json.setId(i.getId());
			json.setName(i.getName());
			json.setBrandCategory(i.getBrandCategory());
			json.setSpecialImg(i.getSpecialImg());
			json.setBigImg(i.getBigImg());
			json.setSmallImg(i.getSmallImg());
			json.setAddress(i.getAddress());
			json.setIsAllBigImg(i.getIsAllBigImg());
			json.setIsBigImg(i.getIsBigImg());
			json.setSort(i.getSort());
			json.setSortAll(i.getSortAll());
			return json;
		}).collect(Collectors.toList());
	}

	@Override
	public Brand save(Brand brand) {
		brand.setCreated_time(Calendar.getInstance());
		brand.setUpdated_time(Calendar.getInstance());
		brand.setBrandCategory(brand.getBrandCategory());
		return brandRepository.save(brand);
	}

	@Override
	public Brand save(BrandJson brand) {
		Brand b = new Brand();
		b.setName(brand.getName());
		b.setSort(brand.getSort());
		return save(b);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Brand> refresh(Sort sort) {
		return brandRepository.findAll(sort);
	}

	@Override
	@Transactional(readOnly = true)
	public Long countByName(String name) {
		return brandRepository.countByName(name);
	}

	@Override
	public Brand findByName(String name) {
		return brandRepository.findByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Brand findOne(int id) {
		return brandRepository.findOne(id);
	}

	@Override
	public Brand update(BrandJson brand) {
		Brand source = brandRepository.findOne(brand.getId());
		source.setName(brand.getName());
		source.setSort(brand.getSort());
		return source;
	}

	@Override
	public Brand update(Brand brand) {
		Brand source = findOne(brand.getId());
		source.setName(brand.getName());
		source.setIsBigImg(brand.getIsBigImg());
		source.setSort(brand.getSort());
		source.setUpdated_by(brand.getUpdated_by());
		source.setUpdated_time(brand.getUpdated_time());
		source.setSpecialImg(brand.getSpecialImg());
		source.setBrandCategory(brand.getBrandCategory());
		source.setBigImg(brand.getBigImg());
		source.setIsAllBigImg(brand.getIsAllBigImg());
		source.setSmallImg(brand.getSmallImg());
		source.setSortAll(brand.getSortAll());
		source.setAddress(brand.getAddress());
		return brandRepository.save(source);
	}

	@Override
	public List<Brand> findRecommandByBrandCategory(ShopCategory brandCategory, Sort sort) {
		return brandRepository.findByBrandCategoryAndSpecialImgNotNull(brandCategory, sort);
	}
	
	@Override
	public List<Brand> findOtherByBrandCategory(ShopCategory brandCategory, Sort sort) {
		return brandRepository.findByBrandCategoryAndSpecialImg(brandCategory,"", sort);
	}

}
