package com.by.repository;

import com.by.model.ParkingCoupon;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface ParkingCouponRepository extends CrudRepository<ParkingCoupon, Integer> {
    Optional<ParkingCoupon> findById(int id);

    Page<ParkingCoupon> findByValid(ValidEnum valid, Pageable pageable);

    Page<ParkingCoupon> findAll(Pageable pageable);

    List<ParkingCoupon> findAll();

    Long countByName(String name);

    ParkingCoupon findByName(String name);

    @Query("select p from ParkingCoupon p where p.valid=:valid and ((p.beginTime<:today and :today<p.endTime) or(p.beginTime is null and p.endTime is null))")
    List<ParkingCoupon> findActivate(@Param("valid") ValidEnum valid, @Param("today") Calendar today);
}
