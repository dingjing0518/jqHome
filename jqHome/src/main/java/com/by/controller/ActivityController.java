package com.by.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.by.model.Activity;
import com.by.model.ActivitySlide;
import com.by.model.EventDetails;
import com.by.service.ActivityService;
import com.by.service.ActivitySildeService;
import com.by.service.EventDetailsService;

/**
 * @author dingjing
 *
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {
	private final String ACTIVITY = "activity/activity";
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ActivitySildeService activitySildeService;
	@Autowired
	private EventDetailsService eventDetailsService;
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String activtyIndex(Model uiModel){
		List<Activity> activitys=activityService.findAll(new Sort(Sort.Direction.ASC, "sort"));
		List<EventDetails>eventDetails=eventDetailsService.findAll((new Sort(Sort.Direction.DESC, "beginTime")));
		uiModel.addAttribute("activitys", activitys);
		List<ActivitySlide> activitySlides =activitySildeService.findAll();
		List<ActivitySlide> newActivitySlides=new ArrayList<>();
		for(int i=0;i<activitySlides.size();i++){
			if(activitySlides.get(i).getStatus()==0){
				newActivitySlides.add(activitySlides.get(i));
			}
		}
		uiModel.addAttribute("activitySlides", newActivitySlides);
		uiModel.addAttribute("meventDetails",eventDetails.subList(0, 5));
		uiModel.addAttribute("eventDetails",eventDetails );
		return ACTIVITY;
	}
}
