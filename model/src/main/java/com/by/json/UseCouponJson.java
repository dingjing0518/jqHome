package com.by.json;

/**
 * Created by yagamai on 15-12-11.
 * 停车券使用规则为用户只需要告诉后台需要使用的张数，
 * 后台优先使用即将过期的卡券,
 * 不需要知道用户使用的是哪个模板生成的停车券
 */
public class UseCouponJson {
	// 需要使用的数量
	private int total;
	// 如果用户设置了密码则对应的密码
	private String password;
	// 对应的车牌号
	private String license;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
}
