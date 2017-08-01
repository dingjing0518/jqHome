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

import com.by.json.MallIntroJson;

@Entity
@Table(name = "by_mall_intro")
public class MallIntro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "section1_img1")
	private String section1Img1;

	@Column(name = "section1_img2")
	private String section1Img2;

	@Column(name = "section1_img3")
	private String section1Img3;

	@Column(name = "section1_title")
	private String section1Title;

	@Column(name = "section1_content")
	private String section1Content;

	@Column(name = "section2_img")
	private String section2Img;

	@Column(name = "section2_title")
	private String section2Title;

	@Column(name = "section2_content")
	private String section2Content;

	@Column(name = "section3_img1")
	private String section3Img1;

	@Column(name = "section3_title1")
	private String section3Title1;

	@Column(name = "section3_content1")
	private String section3Content1;

	@Column(name = "section3_img2")
	private String section3Img2;

	@Column(name = "section3_title2")
	private String section3Title2;

	@Column(name = "section3_content2")
	private String section3Content2;

	@Column(name = "section3_img3")
	private String section3Img3;

	@Column(name = "section3_title3")
	private String section3Title3;

	@Column(name = "section3_content3")
	private String section3Content3;

	@Column(name = "section4_img")
	private String section4Img;

	@Column(name = "section4_title")
	private String section4Title;

	@Column(name = "section4_content")
	private String section4Content;

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

	public String getMapUrl() {
		return "http://api.map.baidu.com/api?v=2.0&ak=45D0Env5U7WMZ3j9P0kTa8gy&callback";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSection1Img1() {
		return section1Img1;
	}

	public void setSection1Img1(String section1Img1) {
		this.section1Img1 = section1Img1;
	}

	public String getSection1Img2() {
		return section1Img2;
	}

	public void setSection1Img2(String section1Img2) {
		this.section1Img2 = section1Img2;
	}

	public String getSection1Img3() {
		return section1Img3;
	}

	public void setSection1Img3(String section1Img3) {
		this.section1Img3 = section1Img3;
	}

	public String getSection1Content() {
		return section1Content;
	}

	public void setSection1Content(String section1Content) {
		this.section1Content = section1Content;
	}

	public String getSection2Img() {
		return section2Img;
	}

	public void setSection2Img(String section2Img) {
		this.section2Img = section2Img;
	}

	public String getSection2Content() {
		return section2Content;
	}

	public void setSection2Content(String section2Content) {
		this.section2Content = section2Content;
	}

	public String getSection3Img1() {
		return section3Img1;
	}

	public void setSection3Img1(String section3Img1) {
		this.section3Img1 = section3Img1;
	}

	public String getSection3Title1() {
		return section3Title1;
	}

	public void setSection3Title1(String section3Title1) {
		this.section3Title1 = section3Title1;
	}

	public String getSection3Content1() {
		return section3Content1;
	}

	public void setSection3Content1(String section3Content1) {
		this.section3Content1 = section3Content1;
	}

	public String getSection3Img2() {
		return section3Img2;
	}

	public void setSection3Img2(String section3Img2) {
		this.section3Img2 = section3Img2;
	}

	public String getSection3Title2() {
		return section3Title2;
	}

	public void setSection3Title2(String section3Title2) {
		this.section3Title2 = section3Title2;
	}

	public String getSection3Content2() {
		return section3Content2;
	}

	public void setSection3Content2(String section3Content2) {
		this.section3Content2 = section3Content2;
	}

	public String getSection3Img3() {
		return section3Img3;
	}

	public void setSection3Img3(String section3Img3) {
		this.section3Img3 = section3Img3;
	}

	public String getSection3Title3() {
		return section3Title3;
	}

	public void setSection3Title3(String section3Title3) {
		this.section3Title3 = section3Title3;
	}

	public String getSection3Content3() {
		return section3Content3;
	}

	public void setSection3Content3(String section3Content3) {
		this.section3Content3 = section3Content3;
	}

	public String getSection4Img() {
		return section4Img;
	}

	public void setSection4Img(String section4Img) {
		this.section4Img = section4Img;
	}

	public String getSection4Title() {
		return section4Title;
	}

	public void setSection4Title(String section4Title) {
		this.section4Title = section4Title;
	}

	public String getSection4Content() {
		return section4Content;
	}

	public void setSection4Content(String section4Content) {
		this.section4Content = section4Content;
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

	public MallIntroJson toJson() {
		return new MallIntroJson(this);
	}

	public String getSection1Title() {
		return section1Title;
	}

	public void setSection1Title(String section1Title) {
		this.section1Title = section1Title;
	}

	public String getSection2Title() {
		return section2Title;
	}

	public void setSection2Title(String section2Title) {
		this.section2Title = section2Title;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MallIntro activity = (MallIntro) o;

		return id == activity.id;

	}

	@Override
	public int hashCode() {
		return id;
	}
}
