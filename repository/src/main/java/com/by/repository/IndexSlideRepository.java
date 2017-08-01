package com.by.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.IndexSlide;

public interface IndexSlideRepository extends PagingAndSortingRepository<IndexSlide, Integer> {

	List<IndexSlide> findAll();
}
