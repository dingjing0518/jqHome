package com.by.model;

import com.by.json.CouponJson;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by yagamai on 15-12-3.
 */
@Entity
@DiscriminatorValue("g")
public class GiftCoupon extends Coupon {
    @OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY)
    private List<GiftCouponMember> members;

    private String logo;

    public GiftCoupon() {
        super();
    }

    public GiftCoupon(int id) {
        setId(id);
    }

    public List<GiftCouponMember> getMembers() {
        return members;
    }

    public void setMembers(List<GiftCouponMember> members) {
        this.members = members;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public CouponJson toCouponJson() {
        CouponJson json = super.toCouponJson();
        json.setType("g");
        return json;
    }
}
