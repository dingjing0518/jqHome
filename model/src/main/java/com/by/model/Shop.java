package com.by.model;

import com.by.json.ShopJson;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yagamai on 15-11-23.
 */
@Entity
@Table(name = "by_shop")
public class Shop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@NotEmpty(message = "{NotEmpty.shop.name}")
	private String name;

	@NotNull
	@Column(name = "shop_key")
	private String shopKey;

	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
	private List<ShopCoupon> coupons;

	@ManyToMany(mappedBy = "shops", fetch = FetchType.LAZY)
	private List<ShopRule> rules;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Calendar createdTime;

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_time")
	private Calendar updatedTime;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "img_href")
	private String imgHref;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "first_category_id")
	private ShopCategory firstCategory;

	@OneToOne
	@JoinColumn(name = "second_category_id")
	private ShopCategory secondCategory;

	@ManyToOne
	@JoinColumn(name = "floor_id")
	private Floor floor;

	private String descpriton;

	@Column(name = "shop_img")
	private String shopImg;

	@Column(name = "shop_img2")
	private String shopImg2;

	private String address;

	private String phone;

	@Column(name = "business_hour")
	private String businessHour;

	@Column(name = "is_big_logo")
	private Integer isBigLogo;

	private Integer sort;

	public Shop() {
	}

	public Shop(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}

	public List<ShopCoupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<ShopCoupon> coupons) {
		this.coupons = coupons;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Calendar getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Calendar updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<ShopRule> getRules() {
		return rules;
	}

	public void setRules(List<ShopRule> rules) {
		this.rules = rules;
	}

	public String getImgHref() {
		return imgHref;
	}

	public void setImgHref(String imgHref) {
		this.imgHref = imgHref;
	}

	public ShopJson toJson() {
		return new ShopJson(this);
	}

	public ShopCategory getFirstCategory() {
		return firstCategory;
	}

	public void setFirstCategory(ShopCategory firstCategory) {
		this.firstCategory = firstCategory;
	}

	public ShopCategory getSecondCategory() {
		return secondCategory;
	}

	public void setSecondCategory(ShopCategory secondCategory) {
		this.secondCategory = secondCategory;
	}

	public Floor getFloor() {
		return floor;
	}

	public void setFloor(Floor floor) {
		this.floor = floor;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBusinessHour() {
		return businessHour;
	}

	public void setBusinessHour(String businessHour) {
		this.businessHour = businessHour;
	}

	public String getDescpriton() {
		return descpriton;
	}

	public void setDescpriton(String descpriton) {
		this.descpriton = descpriton;
	}

	public String getShopImg() {
		return shopImg;
	}

	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}

	public String getShopImg2() {
		return shopImg2;
	}

	public void setShopImg2(String shopImg2) {
		this.shopImg2 = shopImg2;
	}

	@PrePersist
	private void prePersist() {
		this.createdTime = Calendar.getInstance();
		if (this.updatedTime == null)
			this.updatedTime = Calendar.getInstance();
	}

	@PreUpdate
	private void preUpdate() {
		this.updatedTime = Calendar.getInstance();
	}

	public Integer getIsBigLogo() {
		return isBigLogo;
	}

	public void setIsBigLogo(Integer isBigLogo) {
		this.isBigLogo = isBigLogo;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Shop shop = (Shop) o;

		return id == shop.id;

	}

	@Override
	public int hashCode() {
		return id;
	}
}
