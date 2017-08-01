package com.by.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.by.model.MallIntro;

public interface MallIntroRepository extends CrudRepository<MallIntro, Integer> {
	List<MallIntro> findAll();
}
