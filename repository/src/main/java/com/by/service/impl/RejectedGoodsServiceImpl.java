package com.by.service.impl;

import com.by.model.RejectedGoods;
import com.by.repository.RejectedGoodsRepository;
import com.by.service.RejectedGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yagamai on 16-3-9.
 */
@Service
@Transactional
public class RejectedGoodsServiceImpl implements RejectedGoodsService {
    @Autowired
    private RejectedGoodsRepository repository;

    @Override
    public RejectedGoods save(RejectedGoods goods) {
        return repository.save(goods);
    }
}
