package com.by.model;

import com.by.json.CouponJson;
import com.by.json.CouponMemberJson;
import com.by.json.GiftCouponMemberJson;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by yagamai on 15-12-3.
 */

@Entity
@Table(name = "by_gift_coupon_member")
public class GiftCouponMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private GiftCoupon coupon;

    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "exchanged_time")
    private Calendar exchangedTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "used_time")
    private Calendar usedTime;

    public GiftCouponMember() {
    }

    public GiftCouponMember(Member member, GiftCoupon coupon) {
        this.member = member;
        this.coupon = coupon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public GiftCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(GiftCoupon coupon) {
        this.coupon = coupon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Calendar getExchangedTime() {
        return exchangedTime;
    }

    public void setExchangedTime(Calendar exchangedTime) {
        this.exchangedTime = exchangedTime;
    }

    public Calendar getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Calendar usedTime) {
        this.usedTime = usedTime;
    }

    public CouponJson toCouponJson() {
        CouponJson json = getCoupon().toCouponJson();
        json.setId(getCoupon().getId());
        json.setCode(getCode());
        return json;
    }

    public CouponMemberJson toJson() {
        return new GiftCouponMemberJson(this);
    }

    @PrePersist
    private void prePersist() {
        this.exchangedTime = Calendar.getInstance();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftCouponMember that = (GiftCouponMember) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
