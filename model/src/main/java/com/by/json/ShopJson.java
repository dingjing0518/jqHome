package com.by.json;

import com.by.model.Shop;

/**
 * Created by yagamai on 15-12-9.
 */
public class ShopJson {
	private int id;
	private String name;
	private String imgHref;
	private String key;
	private String descpriton;
	private String shopImg;
	private String shopImg2;
	private String phone;
	private String businessHour;
	private int isBigLogo;
	private int sort;

	public ShopJson() {
	}

	public ShopJson(Shop shop) {
		this.id = shop.getId();
		this.name = shop.getName();
		this.imgHref = shop.getImgHref();
		this.key = shop.getShopKey();
		this.descpriton = shop.getDescpriton();
		this.shopImg = shop.getShopImg();
		this.shopImg2 = shop.getShopImg2();
		this.phone = shop.getPhone();
		this.businessHour = shop.getBusinessHour();
		this.isBigLogo = shop.getIsBigLogo();
		this.sort = shop.getSort();
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

	public String getImgHref() {
		return imgHref;
	}

	public void setImgHref(String imgHref) {
		this.imgHref = imgHref;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public int getIsBigLogo() {
		return isBigLogo;
	}

	public void setIsBigLogo(int isBigLogo) {
		this.isBigLogo = isBigLogo;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getShopImg2() {
		return shopImg2;
	}

	public void setShopImg2(String shopImg2) {
		this.shopImg2 = shopImg2;
	}

}
