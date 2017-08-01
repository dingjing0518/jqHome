package com.by.service.impl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.by.json.SlideJson;
import com.by.model.IndexSlide;
import com.by.repository.IndexSlideRepository;
import com.by.service.IndexSlideService;

@Service
@Transactional
public class IndexSlideServiceImpl implements IndexSlideService {
	@Autowired
	private IndexSlideRepository repository;

	@Override
	public IndexSlide save(IndexSlide slide) {
		return repository.save(slide);
	}

	@Override
	public IndexSlide update(IndexSlide newSlide) {
		IndexSlide slide = repository.findOne(newSlide.getId());
		slide.setCoverImg(newSlide.getCoverImg());
		slide.setMobileImg(newSlide.getMobileImg());
		slide.setSort(newSlide.getSort());
		slide.setUpdatedBy(newSlide.getUpdatedBy());
		slide.setUpdatedTime(newSlide.getUpdatedTime());
		return repository.save(slide);
	}

	@Override
	public List<IndexSlide> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public IndexSlide findOne(int id) {
		return repository.findOne(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<SlideJson> findAllJson() {
		List<IndexSlide> Slides = findAll();
		List<SlideJson> jsons = Slides.stream().map(i -> i.toJson()).collect(Collectors.toList());
		return jsons;
	}


}
