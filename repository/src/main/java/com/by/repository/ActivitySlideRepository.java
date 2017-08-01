package com.by.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.ActivitySlide;

public interface ActivitySlideRepository extends PagingAndSortingRepository<ActivitySlide, Integer> {
	List<ActivitySlide> findAll();
	List<ActivitySlide> findByTypeOrderByTypeAsc(int type);
}
