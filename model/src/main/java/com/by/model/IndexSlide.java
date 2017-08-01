/**

 * @Title: Slide.java

 * @Package com.by.model

 * @Description: TODO

 * Copyright: Copyright (c) 2016 

 * Company:Joymap

 * 

 * @author Carl

 * @date 2016-5-17 5:30:40

 * @version V1.0

 */

package com.by.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.by.json.SlideJson;

@Entity
@Table(name = "by_index_slide")
public class IndexSlide {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "cover_img")
	private String coverImg;

	@Column(name = "mobile_img")
	private String mobileImg;
	
	private int sort;
	private Integer status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	protected Calendar createdTime;

	@Column(name = "created_by")
	protected String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_time")
	protected Calendar updatedTime;

	@Column(name = "updated_by")
	protected String updatedBy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getMobileImg() {
		return mobileImg;
	}

	public void setMobileImg(String mobileImg) {
		this.mobileImg = mobileImg;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
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

	public SlideJson toJson() {
		return new SlideJson(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		IndexSlide that = (IndexSlide) o;

		return id == that.id;

	}

	@Override
	public int hashCode() {
		return id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
