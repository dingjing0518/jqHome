package com.by.converter;

import org.springframework.core.convert.converter.Converter;

import com.by.typeEnum.ShowOnIndex;

public class StringToShowOnIndexConverter implements Converter<String, ShowOnIndex> {

	@Override
	public ShowOnIndex convert(String source) {
		return ShowOnIndex.fromString(source);
	}

}
