package com.by.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import com.by.model.Activity;

public interface ActivityRepository extends CrudRepository<Activity, Integer>{
	List<Activity> findAll();
	List<Activity> findAll(Sort sort);
}
