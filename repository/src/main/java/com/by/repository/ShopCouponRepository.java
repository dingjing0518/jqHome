package com.by.repository;

import java.util.Calendar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.by.model.ShopCoupon;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-8.
 */
public interface ShopCouponRepository extends CrudRepository<ShopCoupon, Integer> {
    Page<ShopCoupon> findByValid(ValidEnum valid, Pageable pageable);

    @Query("select g from ShopCoupon g where g.valid=:valid and ((g.beginTime<:today and :today<g.endTime) or (g.beginTime is null and g.endTime is null))")
    Page<ShopCoupon> findAllByValidAndDateBetween(@Param("valid") ValidEnum valid, @Param("today") Calendar today,
                                                  Pageable pageable);

    @Query("select g from ShopCoupon g where g.valid=:valid and ((g.beginTime<:today and :today<g.endTime) or(g.beginTime is null and g.endTime is null)) and g.name like CONCAT('%',:name,'%')")
    Page<ShopCoupon> findAllByValidAndDateBetweenAndNameLike(@Param("valid") ValidEnum valid, @Param("name") String name, @Param("today") Calendar today,
                                                             Pageable pageable);

    Long countByName(String name);

    ShopCoupon findByName(String name);

}
