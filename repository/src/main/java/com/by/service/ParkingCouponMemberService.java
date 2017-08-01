package com.by.service;

import com.by.json.ParkingCouponMemberJson;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ParkingCouponMemberService {
    Page<ParkingCouponMember> findByMember(Member member, Pageable pageable);

    ParkingCouponMember save(ParkingCouponMember pcm);

    Page<ParkingCouponMemberJson> toJson(Page<ParkingCouponMember> page, Pageable pageable);

    ParkingCouponMember save(Member member, ParkingCoupon pc, int total);

    ParkingCouponMember findByMemberAndCoupon(Member member, ParkingCoupon coupon);

    List<ParkingCouponMember> findByMember(Member member, Sort sort);

    List<ParkingCouponMember> findByMember(Member member);

    void delete(ParkingCouponMember pcm);

    void delete(List<ParkingCouponMember> pcm);

    Long sumTotalByMember(Member member);
}
