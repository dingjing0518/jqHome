package com.by.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.by.form.ActivityForm;
import com.by.model.ActivitySlide;
import com.by.repository.ActivitySlideRepository;
import com.by.service.ActivitySildeService;
@Service
@Transactional
public class ActivitySlideServiceImpl implements ActivitySildeService {
	@Autowired
	private ActivitySlideRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<ActivitySlide> findAll(ActivityForm form) {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public ActivitySlide findOne(int id) {
		return repository.findOne(id);
	}
	@Override
	public ActivitySlide save(ActivitySlide act) {
		return repository.save(act);
	}

	@Override
	public ActivitySlide update(ActivitySlide act) {
		ActivitySlide activitySlide = repository.findOne(act.getId());
		activitySlide.setCoverImg(act.getCoverImg());
		activitySlide.setSort(act.getSort());
		activitySlide.setType(act.getType());
		activitySlide.setUpdatedBy(act.getUpdatedBy());
		activitySlide.setUpdatedTime(act.getUpdatedTime());
		activitySlide.setVedioName(act.getVedioName());
		activitySlide.setVedioUrl(act.getVedioUrl());
		return repository.save(activitySlide);
	}

	@Override
	public List<ActivitySlide> findAll() {
		return repository.findAll();
	}

	@Override
	public List<ActivitySlide> findAllByType(int type) {
		return repository.findByTypeOrderByTypeAsc(type);
	}
}
