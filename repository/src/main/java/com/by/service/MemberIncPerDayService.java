package com.by.service;

import java.util.List;

/**
 * Created by yagamai on 16-3-15.
 */
public interface MemberIncPerDayService {
    List<Object[]> findByYearAndMonth(int year, int month);
    
    Integer findMaxNumber();
}
