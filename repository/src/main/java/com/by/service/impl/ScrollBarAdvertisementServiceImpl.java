package com.by.service.impl;

import com.by.model.ScrollBarAdvertisement;
import com.by.model.ShoppingMall;
import com.by.repository.ScrollBarAdvertisementRepository;
import com.by.service.ScrollBarAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yagamai on 16-3-29.
 */
@Service
@Transactional
public class ScrollBarAdvertisementServiceImpl implements ScrollBarAdvertisementService {
    @Autowired
    private ScrollBarAdvertisementRepository repository;

    @Override
    public ScrollBarAdvertisement save(ScrollBarAdvertisement sba) {
        return repository.save(sba);
    }

    @Override
    public ScrollBarAdvertisement update(ScrollBarAdvertisement sba) {
        ScrollBarAdvertisement s = repository.findOne(sba.getId());
        s.setCoverImg(sba.getCoverImg());
        return s;
    }

    @Override
    public List<ScrollBarAdvertisement> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ScrollBarAdvertisement> findByMall(ShoppingMall mall) {
        return repository.findByMall(mall);
    }


    @Override
    @Transactional(readOnly = true)
    public ScrollBarAdvertisement findOne(int id) {
        return repository.findOne(id);
    }

}
