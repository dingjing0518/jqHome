package com.by.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.Fail;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.CouponJson;
import com.by.json.ShopCouponUseJson;
import com.by.json.TradingRequestJson;
import com.by.service.ShopCouponMemberService;
import com.by.service.TradingService;

/**
 * Created by yagamai on 16-2-25.
 */
@Controller
@RequestMapping(value = "/poster")
public class HuiYinController {
	@Value("${key.poster}")
	private String posterKey;
	@Autowired
	private ShopCouponMemberService shopCouponMemberService;
	@Autowired
	private TradingService tradingService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Status trading(@RequestHeader(value = "posterKey", required = true) String key,
			@RequestBody TradingRequestJson json) {
		if (!key.equals(posterKey))
			return new Fail("need key");
		tradingService.fromPoster(json);
		return new Success<>("success");
	}

	// 优惠券核销
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Status useCoupon(@RequestHeader(value = "posterKey", required = true) String key,
			@RequestBody ShopCouponUseJson useCouponInfo) {
		if (!key.equals(posterKey))
			return new Fail("need key");
		String name = useCouponInfo.getMobile();
		String shopKey = useCouponInfo.getShopKey();
		Double profit = shopCouponMemberService.useCoupon(useCouponInfo.getCodes(), name, shopKey);
		return new Success<>(profit);
	}

	@RequestMapping(value = "/member", method = RequestMethod.GET)
	@ResponseBody
	public Status find(@RequestHeader(value = "posterKey", required = true) String key,
			@RequestParam("mobile") String name, @RequestParam("shopKey") String shopKey) {
		if (!key.equals(posterKey))
			return new Fail("need key");
		List<CouponJson> list = shopCouponMemberService.findByMemberAndShop(name, shopKey).stream()
				.map(i -> i.toCouponJson()).collect(Collectors.toList());
		return new Success<>(new MemberCoupon(name, list));
	}

	private class MemberCoupon {
		private String name;
		private List<CouponJson> lists;

		public MemberCoupon(String name, List<CouponJson> lists) {
			this.name = name;
			this.lists = lists;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<CouponJson> getLists() {
			return lists;
		}

		public void setLists(List<CouponJson> lists) {
			this.lists = lists;
		}
	}
}
