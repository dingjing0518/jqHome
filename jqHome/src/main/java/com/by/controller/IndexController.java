package com.by.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.by.model.IndexSlide;
import com.by.service.IndexSlideService;
@Controller
public class IndexController {
	private final String INDEX = "index";
	
	@Autowired
	private IndexSlideService slideService;
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model uiModel) {

		List<IndexSlide> slides = slideService.findAll();
		List<IndexSlide>newslides=new ArrayList<>();
		for(int i=0;i<slides.size();i++){
			if(slides.get(i).getStatus()==0){
				newslides.add(slides.get(i));
			}
		}		
		uiModel.addAttribute("slides", newslides);
		return INDEX;
		
	}
}
