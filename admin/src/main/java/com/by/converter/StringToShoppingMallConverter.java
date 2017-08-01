package com.by.converter;

import org.springframework.core.convert.converter.Converter;

import com.by.factory.ShoppingMallFactory;
import com.by.model.ShoppingMall;

public class StringToShoppingMallConverter implements Converter<String, ShoppingMall> {
	@Override
	public ShoppingMall convert(String source) {
		return ShoppingMallFactory.MALL.fromString(source);
	}
}
