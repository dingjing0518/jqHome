package com.by.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

@Entity
@Table(name = "by_activity_detail")
public class ActivityDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "cover_img")
	private String coverImg;

	@Column(name = "left_img")
	private String leftImg;

	private String content;

	@Column(name = "sidelights_1")
	private String sidelights1;

	@Column(name = "sidelights_2")
	private String sidelights2;

	@Column(name = "sidelights_3")
	private String sidelights3;

	@Column(name = "sidelights_4")
	private String sidelights4;

	@Column(name = "sidelights_5")
	private String sidelights5;

	@Column(name = "sidelights_6")
	private String sidelights6;

	public ActivityDetail() {
	}

	public ActivityDetail(int id) {
		this.id = id;
	}

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

	public String getLeftImg() {
		return leftImg;
	}

	public void setLeftImg(String leftImg) {
		this.leftImg = leftImg;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSidelights1() {
		return sidelights1;
	}

	public void setSidelights1(String sidelights1) {
		this.sidelights1 = sidelights1;
	}

	public String getSidelights2() {
		return sidelights2;
	}

	public void setSidelights2(String sidelights2) {
		this.sidelights2 = sidelights2;
	}

	public String getSidelights3() {
		return sidelights3;
	}

	public void setSidelights3(String sidelights3) {
		this.sidelights3 = sidelights3;
	}

	public String getSidelights4() {
		return sidelights4;
	}

	public void setSidelights4(String sidelights4) {
		this.sidelights4 = sidelights4;
	}

	public String getSidelights5() {
		return sidelights5;
	}

	public void setSidelights5(String sidelights5) {
		this.sidelights5 = sidelights5;
	}

	public String getSidelights6() {
		return sidelights6;
	}

	public void setSidelights6(String sidelights6) {
		this.sidelights6 = sidelights6;
	}
	
	public List<String> getSidelights() {
		List<String> sidelightsList = new ArrayList<String>();
		if(StringUtils.hasLength(sidelights1)) {
			sidelightsList.add(sidelights1);
		}
		if(StringUtils.hasLength(sidelights2)) {
			sidelightsList.add(sidelights2);
		}
		if(StringUtils.hasLength(sidelights3)) {
			sidelightsList.add(sidelights3);
		}
		if(StringUtils.hasLength(sidelights4)) {
			sidelightsList.add(sidelights4);
		}
		if(StringUtils.hasLength(sidelights5)) {
			sidelightsList.add(sidelights5);
		}
		if(StringUtils.hasLength(sidelights6)) {
			sidelightsList.add(sidelights6);
		}
		return sidelightsList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ActivityDetail activity = (ActivityDetail) o;

		return id == activity.id;

	}

	@Override
	public int hashCode() {
		return id;
	}
}
