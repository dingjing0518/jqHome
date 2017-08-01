package com.by.repository;

import com.by.model.MemberIncPerDay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by yagamai on 16-3-15.
 */
public interface MemberIncPerDayRepository extends CrudRepository<MemberIncPerDay, Integer> {
	@Query("select month(m.createdTime),sum(m.number),concat(year(m.createdTime),month(m.createdTime)) from MemberIncPerDay m  where year(m.createdTime)=:year and month(m.createdTime)<=:month group by month(m.createdTime) order by month(m.createdTime) asc ")
	List<Object[]> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
