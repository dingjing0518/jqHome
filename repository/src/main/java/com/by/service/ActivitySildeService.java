package com.by.service;

import java.util.List;

import com.by.form.ActivityForm;
import com.by.model.ActivitySlide;

public interface ActivitySildeService {
	List<ActivitySlide> findAll(ActivityForm form);
	
	List<ActivitySlide> findAll();
	
	ActivitySlide findOne(int id);

	ActivitySlide save(ActivitySlide act);

	ActivitySlide update(ActivitySlide act);

	List<ActivitySlide> findAllByType(int type);
}
