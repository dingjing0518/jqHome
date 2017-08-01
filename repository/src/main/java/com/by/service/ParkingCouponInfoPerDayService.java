package com.by.service;

import java.util.List;

/**
 * Created by yagamai on 16-3-17.
 */
public interface ParkingCouponInfoPerDayService {
    List<Object[]> findByYearAndMonth(int year, int month);
}
