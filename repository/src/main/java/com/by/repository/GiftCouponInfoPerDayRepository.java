package com.by.repository;

import com.by.model.GiftCouponInfoPerDay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by yagamai on 16-3-18.
 */
public interface GiftCouponInfoPerDayRepository extends CrudRepository<GiftCouponInfoPerDay, Integer> {
    @Query("select month(g.createdTime),sum(g.exchangeNumber),sum(g.useNumber),concat(year(g.createdTime),month(g.createdTime)) from GiftCouponInfoPerDay g  where year(g.createdTime)=:year and month(g.createdTime)<=:month group by month(g.createdTime) order by month(g.createdTime) asc ")
    public List<Object[]> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
