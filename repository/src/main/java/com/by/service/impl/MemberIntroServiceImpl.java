package com.by.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.json.MemberIntroJson;
import com.by.model.MemberIntro;
import com.by.repository.MemberIntroRepository;
import com.by.service.MemberIntroService;

@Service
@Transactional
public class MemberIntroServiceImpl implements MemberIntroService {
	@Autowired
	private MemberIntroRepository repository;

	@Override
	@Transactional(readOnly = true)
	public MemberIntro findOne(int id) {
		return repository.findOne(id);
	}

	@Override
	public MemberIntro save(MemberIntro memberIntro) {
		return repository.save(memberIntro);
	}

	@Override
	public MemberIntro update(MemberIntro newMemberIntro) {
		MemberIntro memberIntro = repository.findOne(newMemberIntro.getId());
		memberIntro.setCoverImg(newMemberIntro.getCoverImg());
		memberIntro.setIntroduction(newMemberIntro.getIntroduction());
		memberIntro.setMemberRightsImg(newMemberIntro.getMemberRightsImg());
		memberIntro.setMemberRightsContent(newMemberIntro.getMemberRightsContent());
		memberIntro.setUpdatedBy(newMemberIntro.getUpdatedBy());
		memberIntro.setUpdatedTime(newMemberIntro.getUpdatedTime());
		memberIntro.setUseImg(newMemberIntro.getUseImg());
		memberIntro.setRegImg(newMemberIntro.getRegImg());
		return repository.save(memberIntro);
	}

	@Override
	public List<MemberIntroJson> findAllJson() {
		List<MemberIntro> metroIntro = findAll();
		List<MemberIntroJson> jsons = metroIntro.stream().map(i -> i.toJson()).collect(Collectors.toList());
		return jsons;
	}

	@Override
	public List<MemberIntro> findAll() {
		return repository.findAll();
	}

}
