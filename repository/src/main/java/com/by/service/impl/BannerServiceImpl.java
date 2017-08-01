package com.by.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.json.BannerJson;
import com.by.model.Banner;
import com.by.repository.BannerRepository;
import com.by.service.BannerService;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerRepository repository;

    @Override
    public Banner save(Banner banner) {
        return repository.save(banner);
    }

    @Override
    public Banner update(Banner newBanner) {
    	Banner banner = repository.findOne(newBanner.getId());
    	banner.setTitle(newBanner.getTitle());
    	banner.setName(newBanner.getName());
    	banner.setCoverImg(newBanner.getCoverImg());
    	banner.setJumpTo(newBanner.getJumpTo());
    	banner.setSort(newBanner.getSort());
    	banner.setUpdatedBy(newBanner.getUpdatedBy());
    	banner.setUpdatedTime(newBanner.getUpdatedTime());
    	return repository.save(banner);
    }

    @Override
    public List<Banner> findAll() {
        return repository.findAll(new Sort(Sort.Direction.ASC,"sort"));
    }

    @Override
    @Transactional(readOnly = true)
    public Banner findOne(int id) {
        return repository.findOne(id);
    }

	@Override
	public List<Banner> findAll(Sort sort) {
		return repository.findAll(sort);
	}
    
	@Override
	@Transactional(readOnly = true)
	public List<BannerJson> findAllJson() {
		List<Banner> banners = findAll();
		List<BannerJson> jsons = banners.stream().map(i -> i.toJson()).collect(Collectors.toList());
		return jsons;
	}
    

}
