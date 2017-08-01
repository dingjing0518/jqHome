package com.by.json;

import java.util.Arrays;

public class ShopCouponUseJson {
	private String mobile;
	private String[] codes;
	private String shopKey;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String[] getCodes() {
		return codes;
	}

	public void setCodes(String[] codes) {
		this.codes = codes;
	}

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}

	@Override
	public String toString() {
		return "ShopCouponUseJson [mobile=" + mobile + ", codes=" + Arrays.toString(codes) + ", shopKey=" + shopKey
				+ "]";
	}
}