package com.by.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Brand;
import com.by.model.ShopCategory;


public interface BrandRepository extends PagingAndSortingRepository<Brand, Integer>{
	List<Brand> findAll(Sort sort);
	Long countByName(String name);
	Brand findByName(String name);
	List<Brand> findByBrandCategoryAndSpecialImgNotNull(ShopCategory brandCategory, Sort sort);
	List<Brand> findByBrandCategoryAndSpecialImg(ShopCategory brandCategory, String specialImg, Sort sort);
}
