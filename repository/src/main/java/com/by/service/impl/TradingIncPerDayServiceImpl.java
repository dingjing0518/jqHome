package com.by.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.repository.TradingIncPerDayRepository;
import com.by.service.TradingIncPerDayService;

@Service
public class TradingIncPerDayServiceImpl implements TradingIncPerDayService {
	@Autowired
	private TradingIncPerDayRepository repository;

	@Override
	public List<Object[]> findByYearAndMonth(int year, int month) {
		return repository.findByYearAndMonth(year, month);
	}

}
