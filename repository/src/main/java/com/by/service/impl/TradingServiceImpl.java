package com.by.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.by.exception.MemberNotFoundException;
import com.by.exception.NotFoundException;
import com.by.exception.ScoreAlreadyAddedException;
import com.by.exception.ShopNotExistException;
import com.by.form.ManualForm;
import com.by.json.TradingJson;
import com.by.json.TradingRequestJson;
import com.by.model.Member;
import com.by.model.Shop;
import com.by.model.ShopCouponMember;
import com.by.model.Trading;
import com.by.repository.TradingRepository;
import com.by.service.CardRuleCacheService;
import com.by.service.MemberService;
import com.by.service.ShopCouponMemberService;
import com.by.service.ShopRuleCacheService;
import com.by.service.ShopService;
import com.by.service.TradingService;
import com.by.service.model.Tuple2;
import com.by.typeEnum.ScoreHistoryEnum;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-11-27.
 */
@Service
@Transactional
public class TradingServiceImpl implements TradingService {
	private final String reason = "补积分";
	@Value("${reason.trading}")
	private String TRADING_SCORE;
	@Autowired
	private TradingRepository repository;
	@Autowired
	private MemberService memberService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCouponMemberService shopCouponMemberService;
	@Autowired
	private ShopRuleCacheService shopRuleCacheService;
	@Autowired
	private CardRuleCacheService cardRuleCacheService;

	@Override
	@Transactional(readOnly = true)
	public Page<Trading> findByShopAndCreatedTimeBetween(Shop shop, Calendar startTime, Calendar endTime,
			Pageable pageable) {
		return repository.findByShopAndCreatedTimeBetween(shop, startTime, endTime, pageable);
	}

	@Override
	public Trading manualAddScore(ManualForm form) {
		Member member = memberService.findByName(form.getMobile());
		if (member == null)
			throw new MemberNotFoundException();
		Shop shop = shopService.findByName(form.getShopName());
		if (shop == null)
			throw new ShopNotExistException();
		//人工积分Code代表小票号，慧银Code没有值
		Trading t = repository.findByCode(form.getFlowCode());
		//没有人工积分
		if (t == null) {
			Trading trading = new Trading();
			trading.setAmount(form.getAmount());
			trading.setCode(form.getFlowCode());
			int score = tradeToScore(member, shop, form.getAmount());
			trading.setScore(score);
			trading.setShop(shop);
			trading.setMember(member);
			memberService.addScore(member, score, reason, ScoreHistoryEnum.TRADE, form.getRemark());
			return repository.save(trading);
		} else {
			// 该笔交易未进行积分
			if (t.getScore() == null) {
				int score = tradeToScore(member, shop, form.getAmount());
				t.setScore(score);
				memberService.addScore(member, score, reason, ScoreHistoryEnum.TRADE, form.getRemark());
			} else {
				throw new ScoreAlreadyAddedException();
			}
		}
		return t;
	}

	@Override
	public Trading fromPoster(TradingRequestJson json) {
		Shop shop = shopService.findByShopKey(json.getShopKey());
		if (shop == null)
			throw new ShopNotExistException();

		if (json.getMobile() != null) {
			// 如果传过来的消息中包含会员信息，则增加积分
			Member member = memberService.findByName(json.getMobile());
			int score = tradeToScore(member, shop, json.getAmount());
			json.setScore(score);
			memberService.addScore(member, score, TRADING_SCORE, ScoreHistoryEnum.TRADE);
		}
		return save(json);
	}

	@Override
	public Trading findByCodeAndShop(String code, String shopName) {
		Shop shop = shopService.findByName(shopName);
		return repository.findByCodeAndShop(code, shop);
	}

	@Override
	public Trading findByCode(String code) {
		return repository.findByCode(code);
	}

	@Override
	public Trading save(TradingRequestJson trading) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Trading t = new Trading();
		t.setScore(trading.getScore());
		if (!StringUtils.isEmpty(trading.getMobile())) {
			Member member = memberService.findByName(trading.getMobile());
			if (member == null)
				throw new MemberNotFoundException();
			t.setMember(member);
		}
		if (!StringUtils.isEmpty(trading.getShopKey())) {
			Shop shop = shopService.findByKey(trading.getShopKey());
			if (shop == null)
				throw new NotFoundException();
			t.setShop(shop);
		}
		t.setAmount(trading.getAmount());
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(format.parse(trading.getCreatedTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		t.setCreatedTime(cal);
		String[] codes = trading.getCode();
		t = repository.save(t);
		// 将该笔交易同卡券进行关联
		if (codes != null && codes.length > 0) {
			for (String c : codes) {
				ShopCouponMember scm = shopCouponMemberService.findByCode(c);
				scm.setTrading(t);
			}
		}
		return t;
	}

	@Override
	@Transactional(readOnly = true)
	public int tradeToScore(Member member, Shop shop, double amount) {
		Member m = memberService.findOne(member.getId());
		if (m == null)
			throw new MemberNotFoundException();
		if (m.getValid().equals(ValidEnum.INVALID))
			return 0;
		double maxRate = 0, maxScore = 0;
		Tuple2<Double, Integer> shopRuleRateAndScore = shopRuleCacheService.getShopRateAndScore(shop.getId());
		Tuple2<Double, Integer> cardRuleRateAndScore = cardRuleCacheService
				.getCardRateAndScore(member.getCard().getId());
		maxRate = Math.max(shopRuleRateAndScore.get_1(), cardRuleRateAndScore.get_1());
		//店铺和会员卡倍率取最高，店铺/会员卡最大额外积分相加
		return Long.valueOf(Math.round(amount * maxRate +  shopRuleRateAndScore.get_2() + cardRuleRateAndScore.get_2())).intValue();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TradingJson> findByMember(Member member, Pageable pageable) {
		Page<Trading> results = repository.findByMember(member, pageable);
		List<TradingJson> json = results.getContent().stream().map(i -> new TradingJson(i))
				.collect(Collectors.toList());
		return new PageImpl<>(json, pageable, results.getTotalElements());
	}

	@Override
	@Transactional(readOnly = true)
	public Long sumAmountByMember(Member m) {
		return repository.sumAmountByMember(m);
	}

	@Override
	@Transactional(readOnly = true)
	public Long countByMember(Member m) {
		return repository.countByMember(m);
	}

}
