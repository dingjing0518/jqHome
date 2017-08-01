package com.by.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.json.AboutUsJson;
import com.by.model.AboutUs;
import com.by.repository.AboutUsRepository;
import com.by.service.AboutUsService;
@Service
@Transactional
public class AboutUsServiceImpl implements AboutUsService {
	@Autowired
	private AboutUsRepository repository;
	@Override
	public AboutUs save(AboutUs aboutUs) {
		return repository.save(aboutUs);
	}

	@Override
	public AboutUs update(AboutUs newAboutUs) {
		AboutUs aboutUs = repository.findOne(newAboutUs.getId());
		aboutUs.setAddress(newAboutUs.getAddress());
		aboutUs.setEmail(newAboutUs.getEmail());
		aboutUs.setTopBackgroundImg(newAboutUs.getTopBackgroundImg());
		aboutUs.setIconLogoImg(newAboutUs.getIconLogoImg());
		aboutUs.setInvested(newAboutUs.getInvested());
		aboutUs.setPhone(newAboutUs.getPhone());
		aboutUs.setMoblieTopBackgroundImg(newAboutUs.getMoblieTopBackgroundImg());
		return repository.save(aboutUs);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AboutUs> findAll() {
		return repository.findAll();
	}

	@Override
	public List<AboutUsJson> findAllJson() {
		List<AboutUs> aboutUs = findAll();
		List<AboutUsJson> jsons = aboutUs.stream().map(i -> i.toJson()).collect(Collectors.toList());
		return jsons;
	}

	@Override
	public AboutUs findOne(int id) {
		return repository.findOne(id);
	}

}
