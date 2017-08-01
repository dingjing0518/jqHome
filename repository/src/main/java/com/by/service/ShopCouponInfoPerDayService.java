package com.by.service;

import java.util.List;

/**
 * Created by yagamai on 16-3-18.
 */
public interface ShopCouponInfoPerDayService {
    List<Object[]> findByYearAndMonth(int year, int month);
}
