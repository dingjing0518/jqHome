package com.by.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.repository.ParkingCouponInfoPerDayRepository;
import com.by.service.ParkingCouponInfoPerDayService;

/**
 * Created by yagamai on 16-3-17.
 */
@Service
@Transactional
public class ParkingCouponInfoPerDayServiceImpl implements ParkingCouponInfoPerDayService {
    @Autowired
    private ParkingCouponInfoPerDayRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findByYearAndMonth(int year, int month) {
        return repository.findByYearAndMonth(year, month);
    }
}
