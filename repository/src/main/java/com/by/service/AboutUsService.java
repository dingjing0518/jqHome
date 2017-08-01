package com.by.service;

import java.util.List;

import com.by.json.AboutUsJson;
import com.by.model.AboutUs;
public interface AboutUsService {
	AboutUs save(AboutUs aboutUs);

	AboutUs update(AboutUs aboutUs);	
	
	List<AboutUs> findAll();
	
	List<AboutUsJson> findAllJson();

	AboutUs findOne(int id);
}
