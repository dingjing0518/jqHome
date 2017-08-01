package com.by.repository;

import com.by.model.TradingIncPerDay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by yagamai on 16-3-15.
 */
public interface TradingIncPerDayRepository extends CrudRepository<TradingIncPerDay, Integer> {
    @Query("select month(t.createdTime),sum(t.number),sum(t.amount),concat(year(t.createdTime),month(t.createdTime)) from TradingIncPerDay t where year(t.createdTime)=:year and month(t.createdTime)<=:month group by month(t.createdTime) order by month(t.createdTime) asc ")
    List<Object[]> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
