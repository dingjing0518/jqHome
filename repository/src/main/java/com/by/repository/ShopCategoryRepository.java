package com.by.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.by.model.ShopCategory;

/**
 * Created by yagamai on 16-3-24.
 */
public interface ShopCategoryRepository extends CrudRepository<ShopCategory, Integer> {
    @Query("select sc from ShopCategory sc where sc.parent is null order by sc.name")
    List<ShopCategory> findAllFirstCategory();

    List<ShopCategory> findCategoryByParent(ShopCategory c,Sort sort);
}
