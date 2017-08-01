package com.by.service;

import java.util.List;

import com.by.model.ScrollBarAdvertisement;
import com.by.model.ShoppingMall;

/**
 * Created by yagamai on 16-3-29.
 */
public interface ScrollBarAdvertisementService {
	ScrollBarAdvertisement save(ScrollBarAdvertisement sba);

	ScrollBarAdvertisement update(ScrollBarAdvertisement sba);

	List<ScrollBarAdvertisement> findAll();

	List<ScrollBarAdvertisement> findByMall(ShoppingMall mall);

	ScrollBarAdvertisement findOne(int id);
}
