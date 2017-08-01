package com.by.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.by.model.MemberIntro;

public interface MemberIntroRepository extends CrudRepository<MemberIntro, Integer> {
	List<MemberIntro> findAll();
}
