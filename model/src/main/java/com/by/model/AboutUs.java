package com.by.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.by.json.AboutUsJson;

@Entity
@Table(name="by_aboutus")
public class AboutUs {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	//顶部背景图（PC端）
	@Column(name="top_background_img")
	private String topBackgroundImg;
	//顶部背景图（移动端）
	@Column(name="moblie_top_background_img")
	private String moblieTopBackgroundImg;
	//邮箱
	private String email;
	//电话
	private String phone;
	//地址
	private String address;
	//下方logo图
	@Column(name="icon_logo_img")
	private String iconLogoImg;
	//投资方
	private String invested;
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
	public String getMoblieTopBackgroundImg() {
		return moblieTopBackgroundImg;
	}
	public void setMoblieTopBackgroundImg(String moblieTopBackgroundImg) {
		this.moblieTopBackgroundImg = moblieTopBackgroundImg;
	}
	public AboutUsJson toJson(){
		return new AboutUsJson(this);
	}
}
