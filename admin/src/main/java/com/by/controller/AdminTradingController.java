package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.json.TradingJson;
import com.by.model.Member;
import com.by.model.Trading;
import com.by.service.TradingService;

/**
 * Created by yagamai on 15-12-9.
 */
@Controller
@RequestMapping("/admin/trading")
public class AdminTradingController {
	@Autowired
	private TradingService service;

	@RequestMapping(value = "/member/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Page<TradingJson> list(@PathVariable Long id,
			@PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		return service.findByMember(new Member(id), pageable);
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String form() {
		return "admin/manual/create";
	}

	@RequestMapping(value = "/exist", method = RequestMethod.GET)
	@ResponseBody
	public boolean exist(@RequestParam(value = "code") String code, @RequestParam(value = "shopName") String shopName) {
		if (StringUtils.isEmpty(shopName))
			return false;
		Trading t = service.findByCodeAndShop(code, shopName);
		if (t == null)
			return true;
		if (t != null && t.getScore() == null)
			return true;
		return false;
	}
}
