package com.by.service.impl;

import com.by.repository.ShopCouponInfoPerDayRepository;
import com.by.service.ShopCouponInfoPerDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yagamai on 16-3-18.
 */
@Service
@Transactional(readOnly = true)
public class ShopCouponInfoPerDayServiceImpl implements ShopCouponInfoPerDayService {
    @Autowired
    private ShopCouponInfoPerDayRepository repository;

    @Override
    public List<Object[]> findByYearAndMonth(int year, int month) {
        return repository.findByYearAndMonth(year, month);
    }
}
