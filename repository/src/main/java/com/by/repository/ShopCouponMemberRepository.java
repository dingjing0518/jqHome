package com.by.repository;

import com.by.model.Member;
import com.by.model.ShopCoupon;
import com.by.model.ShopCouponMember;
import com.by.typeEnum.ValidEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;
import java.util.List;

/**
 * Created by yagamai on 15-12-8.
 */
public interface ShopCouponMemberRepository extends PagingAndSortingRepository<ShopCouponMember, Long> {
    Long countByCoupon(ShopCoupon coupon);

    List<ShopCouponMember> findByCouponAndMember(ShopCoupon coupon, Member member);

    @Query("select sc from ShopCouponMember sc where sc.usedTime is null and sc.member=:member and sc.coupon.valid=:valid and ((:today<sc.coupon.couponEndTime) or (sc.coupon.couponEndTime is null)) order by sc.coupon.couponEndTime")
    List<ShopCouponMember> findByMemberAndValid(@Param("member") Member member, @Param("valid") ValidEnum valid, @Param("today") Calendar today);

    Page<ShopCouponMember> findByMember(@Param("member") Member member, Pageable pageable);

    ShopCouponMember findByCode(String code);

    @Query("select s from ShopCouponMember s where s.member.name=:name and s.coupon.shop.shopKey=:shopKey and s.coupon.valid=:valid and ((:today<s.coupon.couponEndTime) or (s.coupon.beginTime is null and s.coupon.endTime is null)) and s.usedTime is null")
    List<ShopCouponMember> findByMemberAndShop(@Param("name") String name, @Param("shopKey") String shopKey, @Param("valid") ValidEnum valid, @Param("today") Calendar today);
}
