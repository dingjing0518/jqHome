package com.by.converter;

import com.by.model.ShopCategory;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by yagamai on 16-3-24.
 */
public class StringToShopCategory implements Converter<String, ShopCategory> {

    @Override
    public ShopCategory convert(String source) {
        ShopCategory sc = new ShopCategory();
        int id = Integer.parseInt(source);
        sc.setId(id);
        return sc;
    }
}
