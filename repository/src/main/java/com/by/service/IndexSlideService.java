package com.by.service;

import java.util.List;

import com.by.json.SlideJson;
import com.by.model.IndexSlide;

public interface IndexSlideService {
	IndexSlide save(IndexSlide slide);

	IndexSlide update(IndexSlide slide);
	
	List<IndexSlide> findAll();
	
	List<SlideJson> findAllJson();

	IndexSlide findOne(int id);
}
