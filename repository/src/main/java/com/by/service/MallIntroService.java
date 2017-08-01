package com.by.service;

import java.util.List;

import com.by.json.MallIntroJson;
import com.by.model.MallIntro;

public interface MallIntroService {
	MallIntro save(MallIntro memberIntro);

	MallIntro update(MallIntro memberIntro);	
	
	MallIntro findOne(int id);
	
	List<MallIntroJson> findAllJson();
	
	List<MallIntro> findAll();
}
