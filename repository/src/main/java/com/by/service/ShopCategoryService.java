package com.by.service;

import java.util.List;

import com.by.model.ShopCategory;

/**
 * Created by yagamai on 16-3-24.
 */
public interface ShopCategoryService {
    List<ShopCategory> findAll();

    ShopCategory findOne(int id);

    List<ShopCategory> findAllFirstCategory();

    List<ShopCategory> findCategoryByParent(ShopCategory c);
    
    ShopCategory update(ShopCategory sc);
    
    ShopCategory save(ShopCategory sc);
    
    ShopCategory valid(int id);
}
