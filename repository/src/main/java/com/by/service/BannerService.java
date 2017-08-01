package com.by.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.by.json.BannerJson;
import com.by.model.Banner;

public interface BannerService {
	Banner save(Banner banner);

	Banner update(Banner banner);

	List<Banner> findAll();
	
	List<Banner> findAll(Sort sort);
	
	List<BannerJson> findAllJson();

	Banner findOne(int id);
}
