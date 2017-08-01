package com.by.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.json.MallIntroJson;
import com.by.model.MallIntro;
import com.by.repository.MallIntroRepository;
import com.by.service.MallIntroService;

@Service
@Transactional
public class MallIntroServiceImpl implements MallIntroService {
	@Autowired
	private MallIntroRepository repository;

	@Override
	@Transactional(readOnly = true)
	public MallIntro findOne(int id) {
		return repository.findOne(id);
	}

	@Override
	public MallIntro save(MallIntro mallIntro) {
		return repository.save(mallIntro);
	}

	@Override
	public MallIntro update(MallIntro newMallIntro) {
		MallIntro mallIntro = repository.findOne(newMallIntro.getId());
		
		mallIntro.setSection1Img1(newMallIntro.getSection1Img1());
		mallIntro.setSection1Img2(newMallIntro.getSection1Img2());
		mallIntro.setSection1Img3(newMallIntro.getSection1Img3());
		mallIntro.setSection1Title(newMallIntro.getSection1Title());
		mallIntro.setSection1Content(newMallIntro.getSection1Content());
		mallIntro.setSection2Img(newMallIntro.getSection2Img());
		mallIntro.setSection2Title(newMallIntro.getSection2Title());
		mallIntro.setSection2Content(newMallIntro.getSection2Content());
		mallIntro.setSection3Img1(newMallIntro.getSection3Img1());
		mallIntro.setSection3Title1(newMallIntro.getSection3Title1());
		mallIntro.setSection3Content1(newMallIntro.getSection3Content1());
		mallIntro.setSection3Img2(newMallIntro.getSection3Img2());
		mallIntro.setSection3Title2(newMallIntro.getSection3Title2());
		mallIntro.setSection3Content2(newMallIntro.getSection3Content2());
		mallIntro.setSection3Img3(newMallIntro.getSection3Img3());
		mallIntro.setSection3Title3(newMallIntro.getSection3Title3());
		mallIntro.setSection3Content3(newMallIntro.getSection3Content3());
		mallIntro.setSection4Img(newMallIntro.getSection4Img());
		mallIntro.setSection4Title(newMallIntro.getSection4Title());
		mallIntro.setSection4Content(newMallIntro.getSection4Content());

		return repository.save(mallIntro);
	}

	@Override
	public List<MallIntroJson> findAllJson() {
		List<MallIntro> mallIntro = findAll();
		List<MallIntroJson> jsons = mallIntro.stream().map(i -> i.toJson()).collect(Collectors.toList());
		return jsons;
	}

	@Override
	public List<MallIntro> findAll() {
		return repository.findAll();
	}
	

}
