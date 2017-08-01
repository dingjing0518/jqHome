package com.by.service;

import java.util.Calendar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.by.form.ManualForm;
import com.by.json.TradingJson;
import com.by.json.TradingRequestJson;
import com.by.model.Member;
import com.by.model.Shop;
import com.by.model.Trading;

/**
 * Created by yagamai on 15-11-27.
 */
public interface TradingService {
    Page<Trading> findByShopAndCreatedTimeBetween(Shop shop, Calendar startTime, Calendar endTime, Pageable pageable);

    Trading manualAddScore(ManualForm form);

	Trading fromPoster(TradingRequestJson json);

    Trading save(TradingRequestJson trading);

    int tradeToScore(Member member, Shop shop, double amount);

    Page<TradingJson> findByMember(Member member, Pageable pageable);

    Long sumAmountByMember(Member m);

    Long countByMember(Member m);

    Trading findByCodeAndShop(String code, String shopKey);

    Trading findByCode(String code);
}
