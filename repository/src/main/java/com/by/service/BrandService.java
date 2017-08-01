package com.by.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.by.json.BrandJson;
import com.by.model.Brand;
import com.by.model.ShopCategory;
public interface BrandService {
	Page<Brand> findAll(String name, Pageable pageable);
	Page<BrandJson> findAllJson(String name,Pageable pageable);
	List<Brand> findAll(Sort sort);
	List<BrandJson> toJson(List<Brand> brands);
	Brand save(Brand brand);
	Brand save(BrandJson brand);
	List<Brand> refresh(Sort sort);
	Long countByName(String name);
	Brand findByName(String name);
	Brand findOne(int id);
	Brand update(BrandJson shop);
	Brand update(Brand shop);

	List<Brand> findRecommandByBrandCategory(ShopCategory brandCategory, Sort sort);
	
	List<Brand> findOtherByBrandCategory(ShopCategory brandCategory, Sort sort);
}
