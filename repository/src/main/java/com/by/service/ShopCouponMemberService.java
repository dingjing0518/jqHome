package com.by.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.json.ShopCouponMemberJson;
import com.by.model.Member;
import com.by.model.ShopCoupon;
import com.by.model.ShopCouponMember;

/**
 * Created by yagamai on 15-12-8.
 */
public interface ShopCouponMemberService {
    void exchangeCoupon(Member member, ShopCoupon coupon, int total);

    Double useCoupon(String[] code, String mobile,String shopKey);

    ShopCouponMember save(ShopCouponMember coupon);

    ShopCouponMember save(ShopCoupon coupon, Member member);

    Long countByCoupon(ShopCoupon coupon);

    List<ShopCouponMember> findByCouponAndMember(ShopCoupon coupon, Member member);

	Page<ShopCouponMemberJson> findByMember(Member member, Pageable pageable);

    List<ShopCouponMember> findByMemberAndShop(String name, String shopKey);

    ShopCouponMember findByCode(String code);
}
