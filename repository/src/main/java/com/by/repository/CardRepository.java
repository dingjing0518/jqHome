package com.by.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Card;
import com.by.typeEnum.ValidEnum;

public interface CardRepository extends PagingAndSortingRepository<Card, Integer> {
	Optional<Card> findById(int id);

	Card findByName(String name);
	
	Long countByName(String name);

	Card findByIdAndValid(int id, ValidEnum valid);

	Page<Card> findByValid(ValidEnum valid, Pageable pageable);
	
	List<Card> findByValid(ValidEnum valid);
}
