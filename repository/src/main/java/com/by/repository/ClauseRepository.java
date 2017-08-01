package com.by.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.by.model.Clause;

public interface ClauseRepository extends CrudRepository<Clause, Integer> {
	List<Clause> findAll();
}
