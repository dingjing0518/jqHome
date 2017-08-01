package com.by.service;

import com.by.service.model.Tuple2;

public interface ShopRuleCacheService {
	Tuple2<Double, Integer> getShopRateAndScore(int id);
	
	void refreshCache(int id);
	
	int getShopRuleMaxScore(int shopId, double amount ) ;
}
