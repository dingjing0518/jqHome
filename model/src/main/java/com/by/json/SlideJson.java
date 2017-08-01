package com.by.json;

import com.by.model.IndexSlide;

public class SlideJson {
	private String coverImg;
	
	public SlideJson(IndexSlide slider) {
		this.coverImg = slider.getCoverImg();
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
}
