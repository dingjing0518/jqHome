package com.by.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.Card;
import com.by.model.CardRule;
import com.by.repository.CardRepository;
import com.by.service.CardRuleCacheService;
import com.by.service.model.Tuple2;

@Service
@Transactional
public class CardRuleCacheServiceImpl implements CardRuleCacheService {
	@Autowired
	private CardRepository cardRepository;
	
	@Override
	@CachePut(value = "cardRule")
	public Tuple2<Double, Integer> getCardRateAndScore(int id) {
		Card card = cardRepository.findOne(id);
		List<CardRule> cardRules = card.getRules().stream().filter(i -> i.isValidTradingRule()).collect(Collectors.toList());
		Integer maxScore = 0;
		Double maxRate = 0.0;
		if (cardRules.size() > 0) {
			maxScore = Card.getMaxScore(cardRules);
			maxRate = Card.getMaxRate(cardRules);
		}
		return new Tuple2<>(maxRate, maxScore);
	}
	
	@Override
	public int getCardRuleMaxScore(int id, double amount) {
		Card card = cardRepository.findOne(id);
		List<CardRule> cardRules = card.getRules().stream().filter(i -> i.isValidRule()).collect(Collectors.toList());
		
		int  maxScore = 0;
		int currentScore = 0;
		if(cardRules == null  || cardRules.size() == 0) {
			return maxScore;
		} else {
			for (CardRule cardRule : cardRules) {
				currentScore = Math.addExact(cardRule.getScore(), Math.multiplyExact(new Double(amount).intValue(), new Double(cardRule.getRate()).intValue()));
				if(currentScore > maxScore) {
					maxScore = currentScore;
				}
			}
		}
		return maxScore;
	}



	@Override
	@CacheEvict(value = "cardRule")
	public void refreshCache(int id) {
	}

}
