package com.by.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.by.model.Notify;

public interface NotifyRepository extends PagingAndSortingRepository<Notify, Integer>{
	List<Notify> findAll();
	List<Notify> findAll(Sort sort);
}
