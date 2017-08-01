package com.by.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.model.WechartResponse;
import com.by.repository.WechartResponseRepository;
import com.by.service.WechartResponseService;

@Service
public class WechartResponseServiceImpl implements WechartResponseService {
	@Autowired
	private WechartResponseRepository repository;

	@Override
	public WechartResponse save(WechartResponse response) {
		return repository.save(response);
	}

	@Override
	public WechartResponse findByOncestr(String oncestr) {
		return repository.findByOncestr(oncestr);
	}

}
