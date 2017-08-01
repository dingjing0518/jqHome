package com.by.service;

import com.by.form.BaseCouponForm;
import com.by.json.CouponTemplateJson;
import com.by.json.ParkingCouponHistoryJson;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ParkingCouponService {
	ParkingCoupon save(ParkingCoupon coupon);

	ParkingCoupon cachePut(ParkingCoupon coupon);

	Optional<ParkingCoupon> update(ParkingCoupon coupon);

	List<ParkingCoupon> findAll();

	Optional<ParkingCoupon> findById(int id);

	ParkingCoupon findOne(int id);

	ParkingCoupon findOneCache(int id);

	Page<ParkingCoupon> findByValid(ValidEnum valid, Pageable pageable);

	Page<CouponTemplateJson> findAll(BaseCouponForm from, Pageable pageable);

	Page<ParkingCoupon> findAll(Pageable pageable);

	Page<ParkingCoupon> findFirstPage(int size);

	ParkingCoupon validOrInValid(ParkingCoupon coupon);

	void exchangeCoupon(Member member, ParkingCoupon coupon, int total);

	void useCoupon(Member member, int total, String license);

	ParkingCoupon findActivate();

	Page<ParkingCouponHistoryJson> findByMemberHistory(Member member, Pageable pageable);

	Long countByName(String name);

	ParkingCoupon findByName(String name);

	void delete(ParkingCoupon coupon);

	void deleteResult(int total, List<ParkingCouponMember> pcm);

	ParkingCoupon refresh();
}
