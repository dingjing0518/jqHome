package com.by.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.exception.AdminNotFoundException;
import com.by.model.ShopCategory;
import com.by.repository.ShopCategoryRepository;
import com.by.service.ShopCategoryService;
import com.by.typeEnum.ValidEnum;
import com.google.common.collect.Lists;

/**
 * Created by yagamai on 16-3-24.
 */
@Service
@Transactional
public class ShopCategoryServiceImpl implements ShopCategoryService {
	@Autowired
	private ShopCategoryRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<ShopCategory> findAll() {
		return Lists.newArrayList(repository.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public ShopCategory findOne(int id) {
		return repository.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ShopCategory> findAllFirstCategory() {
		return repository.findAllFirstCategory();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ShopCategory> findCategoryByParent(ShopCategory c) {
		Sort sort = new Sort("name");
		return repository.findCategoryByParent(c, sort);
	}

	@Override
	public ShopCategory update(ShopCategory sc) {
		ShopCategory shopCategory = repository.findOne(sc.getId());
		shopCategory.setName(sc.getName());
		return shopCategory;
	}

	@Override
	public ShopCategory save(ShopCategory sc) {
		return repository.save(sc);
	}

	@Override
	public ShopCategory valid(int id) {
		ShopCategory sc = repository.findOne(id);
		if (sc == null)
			throw new AdminNotFoundException();
		if (sc.getValid().equals(ValidEnum.INVALID)) {
			sc.setValid(ValidEnum.VALID);
			if (sc.getParent() == null) {
				sc.getChildren().forEach(i -> i.setValid(ValidEnum.VALID));
			}
		} else {
			sc.setValid(ValidEnum.INVALID);
			if (sc.getParent() == null) {
				sc.getChildren().forEach(i -> i.setValid(ValidEnum.INVALID));
			}
		}
		return sc;
	}
}
