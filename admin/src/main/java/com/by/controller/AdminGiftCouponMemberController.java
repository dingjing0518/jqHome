package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.CouponMemberJson;
import com.by.model.Menu;
import com.by.service.GiftCouponMemberService;

/**
 * Created by yagamai on 16-3-3.
 */
@Controller
@RequestMapping(value = "/admin/giftCouponMember")
public class AdminGiftCouponMemberController extends BaseController {
	private final String LIST = "admin/giftCouponMember/lists";
	private final Menu subMenu = new Menu(7);
	@Autowired
	private GiftCouponMemberService service;

	// 卡券列表
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(required = false, value = "mobile") String mobile, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "exchangedTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<CouponMemberJson> lists = service.findByMember(mobile, pageable);
		uiModel.addAttribute("lists", lists);
		uiModel.addAttribute("last", computeLastPage(lists.getTotalPages()));
		uiModel.addAttribute("mobile", mobile);
		return LIST;
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Status list(@RequestParam(required = false, value = "mobile") String mobile,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "exchangedTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<CouponMemberJson> lists = service.findByMember(mobile, pageable);
		return new Success<>(lists);
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Success<String> verification(@RequestBody Verification info) {
		service.useCoupon(info.getCode(), info.getMobile());
		return new Success<>("success");
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}

	public static class Verification {
		private String mobile;
		private String code;

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
	}
}
