package com.by.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.by.model.Brand;
import com.by.model.ShopCategory;
import com.by.service.BrandService;

@Controller
@RequestMapping("/brand")
public class BrandController {
	private final String LIST = "brand/index";
	private final String FOOD_LIST = "brand/food/index";
	private final String RETAIL_LIST = "brand/retail/index";
	private final String FUN_LIST = "brand/fun/index";
	private final String CHILD_LIST = "brand/child/index";
	
	@Autowired
	private BrandService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String list(Model uiModel) {	
		List<Brand> brands = service.findAll(new Sort(Sort.Direction.ASC, "sortAll"));
		List<Brand> newBrands = selectStatus(brands);
		uiModel.addAttribute("brands", newBrands);
		return LIST;
	}
	
	@RequestMapping(value = "/food/", method = RequestMethod.GET)
	public String foodList(Model uiModel) {	
		List<Brand> recommandBrands = service.findRecommandByBrandCategory(new ShopCategory(32),new Sort(Sort.Direction.ASC, "sort"));
		List<Brand> newrecommandBrands=selectStatus(recommandBrands);
		List<Brand> otherBrands = service.findOtherByBrandCategory(new ShopCategory(32),new Sort(Sort.Direction.ASC, "sort"));
		List<Brand> newotherBrands=selectStatus(otherBrands);
		uiModel.addAttribute("recommandBrands", newrecommandBrands.subList(0, 2));
		uiModel.addAttribute("otherBrands", newrecommandBrands.subList(2, 6));
		uiModel.addAttribute("brands", newotherBrands);
		return FOOD_LIST;
	}
	
	@RequestMapping(value = "/retail/", method = RequestMethod.GET)
	public String retailList(Model uiModel) {	
		List<Brand> recommandBrands = service.findRecommandByBrandCategory(new ShopCategory(35),new Sort(Sort.Direction.ASC, "sort"));
		List<Brand> newrecommandBrands=selectStatus(recommandBrands);
		List<Brand> otherBrands = service.findOtherByBrandCategory(new ShopCategory(35),new Sort(Sort.Direction.ASC, "sort"));
		List<Brand> newotherBrands=selectStatus(otherBrands);
		uiModel.addAttribute("recommandBrands", newrecommandBrands.subList(0, 2));
		uiModel.addAttribute("otherBrands", newrecommandBrands.subList(2, 6));
		uiModel.addAttribute("brands", newotherBrands);
		return RETAIL_LIST;
	}
	
	@RequestMapping(value = "/fun/", method = RequestMethod.GET)
	public String funList(Model uiModel) {	
		List<Brand> recommandBrands = service.findRecommandByBrandCategory(new ShopCategory(34),new Sort(Sort.Direction.ASC, "sort"));
		List<Brand> newrecommandBrands=selectStatus(recommandBrands);
		List<Brand> otherBrands = service.findOtherByBrandCategory(new ShopCategory(34),new Sort(Sort.Direction.ASC, "sort"));
		List<Brand> newotherBrands=selectStatus(otherBrands);
		uiModel.addAttribute("recommandBrands", newrecommandBrands.subList(0, 3));
		uiModel.addAttribute("otherBrands", newrecommandBrands.subList(3, 7));
		uiModel.addAttribute("brands", newotherBrands);
		return FUN_LIST;
	}
	
	@RequestMapping(value = "/child/", method = RequestMethod.GET)
	public String childList(Model uiModel) {	
		List<Brand> recommandBrands = service.findRecommandByBrandCategory(new ShopCategory(36),new Sort(Sort.Direction.ASC, "sort"));
		List<Brand> newrecommandBrands=selectStatus(recommandBrands);
		List<Brand> otherBrands = service.findOtherByBrandCategory(new ShopCategory(36),new Sort(Sort.Direction.ASC, "sort"));
		List<Brand> newotherBrands=selectStatus(otherBrands);
		uiModel.addAttribute("recommandBrands", newrecommandBrands.subList(0, 2));
		uiModel.addAttribute("otherBrands", newrecommandBrands.subList(2, 6));
		uiModel.addAttribute("brands", newotherBrands);
		return CHILD_LIST;
	}
	private List<Brand> selectStatus(List<Brand> brands) {
		List<Brand> newBrands=new ArrayList<>();
      for(int i=0;i<brands.size();i++){
    	  Brand brand=brands.get(i);
    	  if(brand.getStatus()==0){
    		  newBrands.add(brand);
    	  }
      }
		return newBrands;
	}
}
