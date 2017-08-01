package com.by.controller;

import java.util.List;

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
import com.by.exception.Success;
import com.by.form.ShopCouponForm;
import com.by.json.ShopCouponJson;
import com.by.model.Menu;
import com.by.model.Shop;
import com.by.model.ShopCoupon;
import com.by.service.CouponService;
import com.by.service.ShopCouponService;
import com.by.service.ShopService;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 16-1-4.
 */
@Controller
@RequestMapping(value = "/admin/shopCoupons")
public class AdminShopCouponController extends BaseController {
	private final String CREATE = "admin/shopCoupon/create";
	private final String EDIT = "admin/shopCoupon/edit";
	private final String LIST = "admin/shopCoupon/lists";
	private final String REDIRECT = "redirect:/admin/shopCoupons/";
	private final Menu subMenu = new Menu(11);
	@Autowired
	private ShopCouponService service;
	@Autowired
	private ShopService shopService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private CouponService couponService;
	@Value("${cache.shopCoupon}")
	private String shopCouponUrl;
	private RestTemplate restTemplate = new RestTemplate();

	@ModelAttribute("shops")
	public List<Shop> shops() {
		return shopService.findAll(new Sort(Sort.Direction.ASC, "name"));
	}

	// 列表页
	@RequestMapping(method = RequestMethod.GET)
	public String list(ShopCouponForm form, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE) Pageable pageable) {
		Page<ShopCouponJson> lists = service.findAll(form, pageable);
		uiModel.addAttribute("lists", lists);
		uiModel.addAttribute("last", computeLastPage(lists.getTotalPages()));
		uiModel.addAttribute("form", form);
		return LIST;
	}

	// 获取添加页
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String form(Model uiModel) {
		uiModel.addAttribute("coupon", new ShopCoupon());
		return CREATE;
	}

	// 新增店铺券
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("coupon") ShopCoupon shopCoupon, BindingResult result, Model uiModel,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("coupon", shopCoupon);
			uiModel.addAttribute("message", failMessage(messageSource));
			return CREATE;
		}
		shopCoupon.setCreatedBy(userContext.getCurrentUser().getName());
		ShopCoupon coupon = service.save(shopCoupon);
		service.cachePut(coupon);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + coupon.getId();
	}

	// 详情
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") int id, Model uiModel) {
		ShopCoupon coupon = service.findOne(id);
		if (coupon == null)
			throw new NotFoundException();
		uiModel.addAttribute("coupon", coupon);
		uiModel.addAttribute("statusMessage", getStatusMessage(coupon));
		return EDIT;
	}

	// 更新
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") int id, Model uiModel,
			@Valid @ModelAttribute("coupon") ShopCoupon shopCoupon, BindingResult result,
			RedirectAttributes redirectAttributes) {
		shopCoupon.setId(id);
		if (result.hasErrors()) {
			uiModel.addAttribute("coupon", shopCoupon);
			uiModel.addAttribute("message", failMessage(messageSource));
			shopCoupon.setValid(ValidEnum.VALID);
			uiModel.addAttribute("statusMessage", getStatusMessage(shopCoupon));
			return EDIT;
		}
		refershCache(id);
		shopCoupon.setUpdatedBy(userContext.getCurrentUser().getName());
		ShopCoupon coupon = service.update(shopCoupon);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + coupon.getId();
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Success<Page<ShopCouponJson>> list(ShopCouponForm form,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "beginTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<ShopCouponJson> lists = service.findAll(form, pageable);
		return new Success<>(lists);
	}

	// 名称是否重复
	@RequestMapping(value = "/name/duplicate", method = RequestMethod.GET)
	@ResponseBody
	public boolean nameDuplicate(@RequestParam("name") String name,
			@RequestParam(value = "id", required = false) Integer id) {
		if (id == null) {
			Long count = service.countByName(name);
			return count > 0 ? false : true;
		} else {
			ShopCoupon shopCoupon = service.findByName(name);
			if (shopCoupon != null)
				return shopCoupon.getId() == id ? true : false;
			return true;
		}
	}

	// 有效无效店铺券
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
		restTemplate.put(shopCouponUrl + id, null);
	}
}
