package com.by.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.by.model.EventDetails;

public interface EventDetailsRepository extends PagingAndSortingRepository<EventDetails, Integer> {
	Optional<EventDetails> findById(int id);

	List<EventDetails> findAll(Sort sort);
}
