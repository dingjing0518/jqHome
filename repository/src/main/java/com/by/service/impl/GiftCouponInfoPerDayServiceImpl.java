package com.by.service.impl;

import com.by.repository.GiftCouponInfoPerDayRepository;
import com.by.service.GiftCouponInfoPerDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GiftCouponInfoPerDayServiceImpl implements GiftCouponInfoPerDayService {
    @Autowired
    private GiftCouponInfoPerDayRepository repository;

    @Override
    public List<Object[]> findByYearAndMonth(int year, int month) {
        return repository.findByYearAndMonth(year, month);
    }

}
