package com.by.repository;

import java.util.Calendar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.by.model.Coupon;
import com.by.typeEnum.ValidEnum;

public interface CouponRepository extends PagingAndSortingRepository<Coupon, Integer> {
	Page<Coupon> findByValid(ValidEnum valid, Pageable pageable);

	@Query("select c from Coupon c where c.valid=:valid and ((c.beginTime<:today and :today<c.endTime) or (c.beginTime is null and c.endTime is null)) and type(c)!=:type order by c.couponEndTime NULLS last")
	Page<Coupon> findAllByValidAndDateBetween(@Param("valid") ValidEnum VALID, @Param("today") Calendar calendar,
			Pageable pageable,@Param("type")Class clazz);
}
