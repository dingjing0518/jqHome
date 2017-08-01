package com.by.factory;

import com.by.model.ShoppingMall;

/**
 * Created by yagamai on 16-3-29.
 */
public enum ShoppingMallFactory {
	MALL;

	public ShoppingMall nanXiang() {
		return new ShoppingMall(1);
	}

	public ShoppingMall jinQiao() {
		return new ShoppingMall(2);
	}

	public ShoppingMall fromString(String mall) {
		if (mall.equals("nx")) {
			return nanXiang();
		} else if (mall.equals("jq")) {
			return jinQiao();
		} else
			return null;
	}
}
