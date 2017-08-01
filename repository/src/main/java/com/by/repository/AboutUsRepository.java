package com.by.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.by.model.AboutUs;

public interface AboutUsRepository extends CrudRepository<AboutUs, Integer>{
	List<AboutUs> findAll();
}
