package com.by.service;

import java.util.List;

import com.by.model.Clause;

public interface ClauseService {
	Clause save(Clause clause);

	Clause update(Clause clause);	
	
	List<Clause> findAll();

	Clause findOne(int id);
}
