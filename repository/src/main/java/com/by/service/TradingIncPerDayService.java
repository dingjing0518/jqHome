package com.by.service;

import java.util.List;

public interface TradingIncPerDayService {
    List<Object[]> findByYearAndMonth(int year, int month);
}
