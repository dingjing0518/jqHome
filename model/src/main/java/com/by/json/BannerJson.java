package com.by.json;

import com.by.model.Banner;

public class BannerJson {
	private int id;
	private String title;
	private String name;
	private String jumpTo;
	private String coverImg;

	public BannerJson(Banner banner) {
		this.id = banner.getId();
		this.title = banner.getTitle();
		this.name = banner.getName();
		this.jumpTo = banner.getJumpTo();
		this.coverImg = banner.getCoverImg();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJumpTo() {
		return jumpTo;
	}

	public void setJumpTo(String jumpTo) {
		this.jumpTo = jumpTo;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

}
