package com.by.service;

import java.util.List;

import com.by.json.MemberIntroJson;
import com.by.model.MemberIntro;

public interface MemberIntroService {
	MemberIntro save(MemberIntro memberIntro);

	MemberIntro update(MemberIntro memberIntro);	
	
	List<MemberIntro> findAll();
	
	List<MemberIntroJson> findAllJson();

	MemberIntro findOne(int id);
}
