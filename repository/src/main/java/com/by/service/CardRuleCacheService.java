package com.by.service;

import com.by.service.model.Tuple2;

public interface CardRuleCacheService {
	Tuple2<Double, Integer> getCardRateAndScore(int id);
	
	int getCardRuleMaxScore(int id, double amount);
	
	void refreshCache(int id);
}
