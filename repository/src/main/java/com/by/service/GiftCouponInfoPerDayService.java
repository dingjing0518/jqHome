package com.by.service;

import java.util.List;

public interface GiftCouponInfoPerDayService {
	List<Object[]> findByYearAndMonth(int year, int month);
}
