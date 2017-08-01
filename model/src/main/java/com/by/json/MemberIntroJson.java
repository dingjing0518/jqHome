package com.by.json;

import com.by.model.MemberIntro;

public class MemberIntroJson {
	private int id;
	private String coverImg;
	private String memberIntro;
	private String memberRightsImg;
	private String memberRightsContent;
	private String regFlowImg;
	private String regFlowContent;
	private String howToUseImg;
	private String howToUseContent;
	
	public MemberIntroJson() {
		
	}

	public MemberIntroJson(MemberIntro memberIntro) {
		this.setId(memberIntro.getId());
		this.setCoverImg(memberIntro.getCoverImg());
		this.setMemberIntro(memberIntro.getIntroduction());
		this.setMemberRightsImg(memberIntro.getMemberRightsImg());
		this.setMemberRightsContent(memberIntro.getMemberRightsContent());
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

	public String getMemberIntro() {
		return memberIntro;
	}

	public void setMemberIntro(String memberIntro) {
		this.memberIntro = memberIntro;
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

	public String getRegFlowImg() {
		return regFlowImg;
	}

	public void setRegFlowImg(String regFlowImg) {
		this.regFlowImg = regFlowImg;
	}

	public String getRegFlowContent() {
		return regFlowContent;
	}

	public void setRegFlowContent(String regFlowContent) {
		this.regFlowContent = regFlowContent;
	}

	public String getHowToUseImg() {
		return howToUseImg;
	}

	public void setHowToUseImg(String howToUseImg) {
		this.howToUseImg = howToUseImg;
	}

	public String getHowToUseContent() {
		return howToUseContent;
	}

	public void setHowToUseContent(String howToUseContent) {
		this.howToUseContent = howToUseContent;
	}

}
