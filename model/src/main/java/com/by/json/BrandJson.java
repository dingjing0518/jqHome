package com.by.json;

import com.by.model.Brand;
import com.by.model.ShopCategory;

public class BrandJson {
	private int id;
	private String name;
	private Integer sortAll;
	private Integer sort;
	private String specialImg;
	private String bigImg;
	private String smallImg;
	private String address;
	private ShopCategory brandCategory;
	private Integer isAllBigImg;
	private Integer isBigImg;
	private  Integer status;
	
	public BrandJson() {
		super();
	}

	public BrandJson(Brand brand) {
		this.id = brand.getId();
		this.name = brand.getName();
		this.sort = brand.getSort();
		this.sortAll = brand.getSortAll();
		this.bigImg = brand.getBigImg();
		this.smallImg = brand.getSmallImg();
		this.setAddress(brand.getAddress());
		this.setBrandCategory(brand.getBrandCategory());
		this.isAllBigImg = brand.getIsAllBigImg();
		this.isBigImg = brand.getIsBigImg();
		this.specialImg = brand.getSpecialImg();
		this.setStatus(brand.getStatus());
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

	public Integer getSortAll() {
		return sortAll;
	}

	public void setSortAll(Integer sortAll) {
		this.sortAll = sortAll;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getBigImg() {
		return bigImg;
	}

	public void setBigImg(String bigImg) {
		this.bigImg = bigImg;
	}

	public String getSmallImg() {
		return smallImg;
	}

	public void setSmallImg(String smallImg) {
		this.smallImg = smallImg;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ShopCategory getBrandCategory() {
		return brandCategory;
	}

	public void setBrandCategory(ShopCategory brandCategory) {
		this.brandCategory = brandCategory;
	}

	public Integer getIsBigImg() {
		return isBigImg;
	}

	public void setIsBigImg(Integer isBigImg) {
		this.isBigImg = isBigImg;
	}

	public String getSpecialImg() {
		return specialImg;
	}

	public void setSpecialImg(String specialImg) {
		this.specialImg = specialImg;
	}

	public Integer getIsAllBigImg() {
		return isAllBigImg;
	}

	public void setIsAllBigImg(Integer isAllBigImg) {
		this.isAllBigImg = isAllBigImg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
