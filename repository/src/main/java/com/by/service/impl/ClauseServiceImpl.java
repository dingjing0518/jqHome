package com.by.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.Clause;
import com.by.repository.ClauseRepository;
import com.by.service.ClauseService;

@Service
@Transactional
public class ClauseServiceImpl implements ClauseService {
	@Autowired
	private ClauseRepository repository;

	@Override
	@Transactional(readOnly = true)
	public Clause findOne(int id) {
		return repository.findOne(id);
	}

	@Override
	public Clause save(Clause mallIntro) {
		return repository.save(mallIntro);
	}

	@Override
	public Clause update(Clause newClause) {
		Clause clause = repository.findOne(newClause.getId());
		clause.setCoverImg(newClause.getCoverImg());
		clause.setClauseImg(newClause.getClauseImg());
		clause.setClauseContent(newClause.getClauseContent());
		clause.setPartnerImg(newClause.getPartnerImg());
		clause.setPartnerImg2(newClause.getPartnerImg2());
		clause.setPartnerContent(newClause.getPartnerContent());
		clause.setContactImg(newClause.getContactImg());
		clause.setPhone(newClause.getPhone());
		clause.setAddress(newClause.getAddress());
		clause.setEmail(newClause.getEmail());
		clause.setUpdatedBy(newClause.getUpdatedBy());
		clause.setUpdatedTime(newClause.getUpdatedTime());
		return repository.save(clause);
	}

	@Override
	public List<Clause> findAll() {
		return repository.findAll();
	}
	

}
