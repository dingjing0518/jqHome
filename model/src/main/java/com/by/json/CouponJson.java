package com.by.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.by.model.Coupon;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-7.
 */
public class CouponJson {
    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private Integer id;

    private String name;

    private String couponEndTime;

    private Calendar couponTime;

    private ValidEnum valid;

    private String type;

    private int total;

    private String code;

    private String coverImg;

    private String contentImg;

    private int score;

    private String logo;
    
    private Double amount;
    
    public CouponJson() {
    }

    public CouponJson(Coupon coupon) {
        this.id = coupon.getId();
        this.name = coupon.getName();
        this.valid = coupon.getValid();
        if (coupon.getCouponEndTime() != null) {
            this.couponTime = coupon.getCouponEndTime();
            this.couponEndTime = format.format(this.couponTime.getTime());
        }
        if (coupon.getTotal() != null)
            this.total = coupon.getTotal();
        this.coverImg = coupon.getCoverImg();
        this.contentImg = coupon.getContentImg();
        this.score = coupon.getScore();
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(String couponEndTime) {
        this.couponEndTime = couponEndTime;
    }

    public ValidEnum getValid() {
        return valid;
    }

    public void setValid(ValidEnum valid) {
        this.valid = valid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Calendar getCouponTime() {
        return couponTime;
    }

    public void setCouponTime(Calendar couponTime) {
        this.couponTime = couponTime;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "CouponJson [name=" + name + "]";
	}
}
