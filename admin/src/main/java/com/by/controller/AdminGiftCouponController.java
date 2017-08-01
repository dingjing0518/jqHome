package com.by.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.BaseCouponForm;
import com.by.form.CouponQueryForm;
import com.by.json.GiftCouponJson;
import com.by.model.GiftCoupon;
import com.by.model.Menu;
import com.by.service.CouponService;
import com.by.service.GiftCouponService;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-22.
 */
@Controller
@RequestMapping("/admin/giftCoupons")
public class AdminGiftCouponController extends BaseController {
	private final String CREATE = "admin/giftCoupons/create";
	private final String EDIT = "admin/giftCoupons/edit";
	private final String REDIRECT = "redirect:/admin/giftCoupons/";
	private final String LIST = "admin/giftCoupons/lists";
	private final Menu subMenu = new Menu(10);
	@Autowired
	private GiftCouponService service;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private CouponService couponService;
	@Value("${cache.giftCoupon}")
	private String giftCouponRefreshUrl;
	private RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(method = RequestMethod.GET)
	public String list(BaseCouponForm form, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE) Pageable pageable) {
		Page<GiftCouponJson> lists = service.findAll(form, pageable);
		uiModel.addAttribute("lists", lists);
		uiModel.addAttribute("last", computeLastPage(lists.getTotalPages()));
		uiModel.addAttribute("form", form);
		return LIST;
	}

	// 新增页
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String form(Model uiModel) {
		GiftCoupon coupon = new GiftCoupon();
		uiModel.addAttribute("coupon", coupon);
		return CREATE;
	}

	// 处理新增逻辑
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("coupon") GiftCoupon coupon, BindingResult result, Model uiModel,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("coupon", coupon);
			uiModel.addAttribute("message", failMessage(messageSource));
			return CREATE;
		}
		coupon.setCreatedBy(userContext.getCurrentUser().getName());
		GiftCoupon source = service.save(coupon);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + source.getId();
	}

	// 获取一条记录
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") int id, Model uiModel) {
		GiftCoupon coupon = service.findOne(id);
		if (coupon == null)
			throw new NotFoundException();
		uiModel.addAttribute("coupon", coupon);
		uiModel.addAttribute("statusMessage", getStatusMessage(coupon));
		return EDIT;
	}

	// 更新数据
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@Valid @ModelAttribute("coupon") GiftCoupon coupon, BindingResult result,
			@PathVariable("id") int id, Model uiModel, RedirectAttributes redirectAttributes) {
		coupon.setId(id);
		if (result.hasErrors()) {
			uiModel.addAttribute("coupon", coupon);
			uiModel.addAttribute("message", failMessage(messageSource));
			coupon.setValid(ValidEnum.VALID);
			uiModel.addAttribute("statusMessage", getStatusMessage(coupon));
			return EDIT;
		}
		refershCache(id);
		coupon.setUpdatedBy(userContext.getCurrentUser().getName());
		GiftCoupon source = service.update(coupon);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + source.getId();
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Status list(CouponQueryForm form,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		return new Success<>(service.findAll(form, pageable));
	}

	@RequestMapping(value = "/name/duplicate", method = RequestMethod.GET)
	@ResponseBody
	public boolean nameDuplicate(@RequestParam("name") String name,
			@RequestParam(value = "id", required = false) Integer id) {
		if (id == null) {
			Long count = service.countByName(name);
			return count > 0 ? false : true;
		} else {
			GiftCoupon coupon = service.findByName(name);
			if (coupon != null)
				return id == coupon.getId() ? true : false;
			return true;
		}
	}

	@RequestMapping(value = "/{id}/valid", method = RequestMethod.PUT)
	public String valid(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		couponService.valid(id);
		refershCache(id);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + id;
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}

	private void refershCache(int id) {
		restTemplate.put(giftCouponRefreshUrl + id, null);
	}
}
