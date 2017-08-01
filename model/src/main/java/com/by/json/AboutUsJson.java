package com.by.json;

import com.by.model.AboutUs;

public class AboutUsJson {
	private int id;
	private String topBackgroundImg;
	private String email;
	private String phone;
	private String address;
	private String iconLogoImg;
	private String invested;
	public AboutUsJson(){
		
	}
	public AboutUsJson(AboutUs aboutus){
		this.id=aboutus.getId();
		this.topBackgroundImg=aboutus.getTopBackgroundImg();
		this.email=aboutus.getEmail();
		this.address=aboutus.getAddress();
		this.phone=aboutus.getPhone();
		this.iconLogoImg=aboutus.getIconLogoImg();
		this.invested=aboutus.getInvested();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTopBackgroundImg() {
		return topBackgroundImg;
	}
	public void setTopBackgroundImg(String topBackgroundImg) {
		this.topBackgroundImg = topBackgroundImg;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIconLogoImg() {
		return iconLogoImg;
	}
	public void setIconLogoImg(String iconLogoImg) {
		this.iconLogoImg = iconLogoImg;
	}
	public String getInvested() {
		return invested;
	}
	public void setInvested(String invested) {
		this.invested = invested;
	}
}
