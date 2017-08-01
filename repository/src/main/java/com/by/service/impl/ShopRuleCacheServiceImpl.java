package com.by.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.Card;
import com.by.model.Shop;
import com.by.model.ShopRule;
import com.by.repository.ShopRepository;
import com.by.service.ShopRuleCacheService;
import com.by.service.model.Tuple2;

@Service
@Transactional
public class ShopRuleCacheServiceImpl implements ShopRuleCacheService {
	@Autowired
	private ShopRepository shopRepository;

	@Override
	@CachePut(value = "shopRule")
	public Tuple2<Double, Integer> getShopRateAndScore(int id) {
		Shop shop = shopRepository.findOne(id);
		List<ShopRule> shopRules = shop.getRules().stream().filter(i -> i.isValidRule()).collect(Collectors.toList());
		Integer maxScore = 0;
		Double maxRate = 0.0;
		if (shopRules.size() > 0) {
			maxScore = Card.getMaxScore(shopRules);
			maxRate = Card.getMaxRate(shopRules);
		}
		return new Tuple2<>(maxRate, maxScore);
	}
	
	@Override
	public int getShopRuleMaxScore(int shopId, double amount) {
		Shop shop = shopRepository.findOne(shopId);
		List<ShopRule> shopRules = shop.getRules().stream().filter(i -> i.isValidRule()).collect(Collectors.toList());
		
		int  maxScore = 0;
		int currentScore = 0;
		if(shopRules == null  || shopRules.size() == 0) {
			return maxScore;
		} else {
			for (ShopRule shopRule : shopRules) {
				currentScore = Math.addExact(shopRule.getScore(), Math.multiplyExact(new Double(amount).intValue(), new Double(shopRule.getRate()).intValue()));
				if(currentScore > maxScore) {
					maxScore = currentScore;
				}
			}
		}
		return maxScore;
	}

	@Override
	@CacheEvict(value = "shopRule")
	public void refreshCache(int id) {
	}

}
