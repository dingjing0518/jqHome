package com.by.service;
import java.util.List;

import org.springframework.data.domain.Sort;

import com.by.json.ActivityJson;
import com.by.model.Activity;

public interface ActivityService {
	Activity save(Activity activity);

	Activity update(Activity activity);	
	
	List<Activity> findAll();
	
	List<Activity> findAll(Sort sort);
	
	List<ActivityJson> findAllJson();

	Activity findOne(int id);
}
