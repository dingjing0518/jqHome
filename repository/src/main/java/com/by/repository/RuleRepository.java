package com.by.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.by.model.Rule;

public interface RuleRepository extends PagingAndSortingRepository<Rule, Integer> {
	Long countByName(String name);

	Rule findByName(String name);
}
