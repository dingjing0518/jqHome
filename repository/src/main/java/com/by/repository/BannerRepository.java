package com.by.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Banner;

public interface BannerRepository extends PagingAndSortingRepository<Banner, Integer> {
	List<Banner> findAll(Sort sort);
}
