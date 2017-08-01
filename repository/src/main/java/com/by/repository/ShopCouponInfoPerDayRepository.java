package com.by.repository;

import com.by.model.ShopCouponInfoPerDay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by yagamai on 16-3-18.
 */
public interface ShopCouponInfoPerDayRepository extends CrudRepository<ShopCouponInfoPerDay, Integer> {
    @Query("select month(s.createdTime),sum(s.exchangeNumber),sum(s.useNumber),concat(year(s.createdTime),month(s.createdTime)) from ShopCouponInfoPerDay s  where year(s.createdTime)=:year and month(s.createdTime)<=:month group by month(s.createdTime) order by month(s.createdTime) asc ")
    public List<Object[]> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
