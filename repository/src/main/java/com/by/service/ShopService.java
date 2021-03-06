package com.by.service;

import com.by.form.ShopBindUserForm;
import com.by.json.ShopJson;
import com.by.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by yagamai on 15-12-1.
 */
public interface ShopService {
	Shop findByKey(String code);

	Page<Shop> findAll(String name, Pageable pageable);

	Page<ShopJson> findAllJson(String name,Pageable pageable);
	
	List<ShopJson> findShopByFloorJson(Shop shop);

	Page<Shop> findFirstPage(int size);

	Shop save(Shop shop);

	Shop save(ShopJson shop);

	Shop findOne(int id);

	Shop update(ShopJson shop);

	Shop update(Shop shop);

	List<Shop> findAll(Sort sort);

	Long countByName(String name);

	Shop findByName(String name);

	Long countByShopKey(String key);

	Shop findByShopKey(String name);

	List<ShopJson> toJson(List<Shop> shops);

    List<Shop> refresh(Sort sort);
}
