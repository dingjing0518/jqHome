package com.by.repository;

import java.util.List;

import com.by.model.ShoppingMall;
import org.springframework.data.repository.CrudRepository;

import com.by.model.ScrollBarAdvertisement;

/**
 * Created by yagamai on 16-3-29.
 */
public interface ScrollBarAdvertisementRepository extends CrudRepository<ScrollBarAdvertisement, Integer> {
    List<ScrollBarAdvertisement> findAll();

    List<ScrollBarAdvertisement> findByMall(ShoppingMall mall);
}
