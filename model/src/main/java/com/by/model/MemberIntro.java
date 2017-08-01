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

import com.by.json.MemberIntroJson;

/**
 * @author dingjing
 * 会员实体
 */
@Entity
@Table(name = "by_member_intro")
public class MemberIntro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	//顶部背景图(PC端)
	@Column(name = "cover_img")
	private String coverImg;
	
	@Column(name = "member_intro")
	private String introduction;

	@Column(name = "member_rights_img")
	private String memberRightsImg;

	@Column(name = "member_rights_content")
	private String memberRightsContent;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Calendar createdTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_time")
	private Calendar updatedTime;
	
	@Column(name = "how_to_use_img")
	private String useImg;
	
	@Column(name = "reg_flow_img")
	private String regImg;
	
	public String getUseImg() {
		return useImg;
	}
	
	public void setUseImg(String useImg) {
		this.useImg = useImg;
	}

	public String getRegImg() {
		return regImg;
	}

	public void setRegImg(String regImg) {
		this.regImg = regImg;
	}

	public MemberIntro() {
	}

	public MemberIntro(int id) {
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

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getMemberRightsImg() {
		return memberRightsImg;
	}

	public void setMemberRightsImg(String memberRightsImg) {
		this.memberRightsImg = memberRightsImg;
	}

	public String getMemberRightsContent() {
		return memberRightsContent;
	}

	public void setMemberRightsContent(String memberRightsContent) {
		this.memberRightsContent = memberRightsContent;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	public Calendar getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Calendar updatedTime) {
		this.updatedTime = updatedTime;
	}

	public MemberIntroJson toJson() {
		return new MemberIntroJson(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MemberIntro activity = (MemberIntro) o;

		return id == activity.id;

	}

	@Override
	public int hashCode() {
		return id;
	}
}
