package com.by.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.by.model.ParkingCouponInfoPerDay;

public interface ParkingCouponInfoPerDayRepository extends CrudRepository<ParkingCouponInfoPerDay, Integer> {
	@Query("select month(p.createdTime),sum(p.exchangeNumber),sum(p.useNumber),concat(year(p.createdTime),month(p.createdTime)) from ParkingCouponInfoPerDay p  where year(p.createdTime)=:year and month(p.createdTime)<=:month group by month(p.createdTime) order by month(p.createdTime) asc ")
	public List<Object[]> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
