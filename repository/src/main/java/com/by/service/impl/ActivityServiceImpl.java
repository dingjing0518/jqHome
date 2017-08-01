package com.by.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.by.json.ActivityJson;
import com.by.model.Activity;
import com.by.repository.ActivityRepository;
import com.by.service.ActivityService;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {
	@Autowired
	private ActivityRepository repository;

	@Override
	public Activity save(Activity activity) {
		return repository.save(activity);
	}

	@Override
	public Activity update(Activity activity) {
		Activity a = repository.findOne(activity.getId());
		a.setActivityImg(activity.getActivityImg());
		a.setName(activity.getName());
		a.setSort(activity.getSort());
		a.setUpdatedTime(activity.getUpdatedTime());
		a.setUpdatedBy(activity.getUpdatedBy());
		return repository.save(a);
	}

	@Override
	public List<Activity> findAll() {
		return repository.findAll();
	}

	@Override
	public List<ActivityJson> findAllJson() {
		List<Activity> activity = findAll();
		List<ActivityJson> jsons = activity.stream().map(i -> i.toJson()).collect(Collectors.toList());
		return jsons;
	}

	@Override
	public Activity findOne(int id) {
		return repository.findOne(id);
	}

	@Override
	public List<Activity> findAll(Sort sort) {
		
		return repository.findAll(sort);
	}
	
}
