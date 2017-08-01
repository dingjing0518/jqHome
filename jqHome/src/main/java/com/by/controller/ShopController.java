package com.by.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.json.ShopJson;
import com.by.model.Floor;
import com.by.model.Shop;
import com.by.service.ShopService;

@Controller
@RequestMapping("/shops")
public class ShopController {
	private final String LIST = "shop/index";
	@Autowired
	private ShopService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String list(Model uiModel,@PageableDefault(page = 0, size = 15) Pageable pageable) {
		
		Shop shop = new Shop();
		Floor fl = new Floor();
		fl.setId(1);
		shop.setFloor(fl);
		List<ShopJson> shopList = service.findShopByFloorJson(shop);
		
		uiModel.addAttribute("shops", shopList);
		
		return LIST;
	}
	
	@RequestMapping(value = "/json/{floor}", method = RequestMethod.GET)
	@ResponseBody
	public List<ShopJson> listValid(@PathVariable("floor") int floor) {
		Shop shop = new Shop();
		Floor fl = new Floor();
		fl.setId(floor);
		shop.setFloor(fl);
		List<ShopJson> shopList = service.findShopByFloorJson(shop);
		
		return shopList;
	}
}
