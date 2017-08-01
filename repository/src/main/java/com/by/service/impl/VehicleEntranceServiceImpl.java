package com.by.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.VehicleEntrance;
import com.by.repository.VehicleEntranceRepository;
import com.by.service.VehicleEntranceService;

@Service
@Transactional
public class VehicleEntranceServiceImpl implements VehicleEntranceService {
	@Autowired
	private VehicleEntranceRepository repository;

	@Override
	public VehicleEntrance save(VehicleEntrance v) {
		return repository.save(v);
	}

}
