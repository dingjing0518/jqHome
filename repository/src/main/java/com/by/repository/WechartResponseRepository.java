package com.by.repository;

import org.springframework.data.repository.CrudRepository;

import com.by.model.WechartResponse;

public interface WechartResponseRepository extends CrudRepository<WechartResponse, Integer> {
	WechartResponse findByOncestr(String oncestr);
}
