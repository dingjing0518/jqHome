package com.by.json;

import com.by.model.Advertisement;

public class AdvertisementJson {
	private int id;
	private String href;
	private String coverImg;

	public AdvertisementJson(Advertisement adv) {
		this.id = adv.getId();
		this.href = adv.getHref();
		this.coverImg = adv.getCoverImg();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

}
