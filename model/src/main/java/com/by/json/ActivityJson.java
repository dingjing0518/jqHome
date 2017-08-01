package com.by.json;

import com.by.model.Activity;

public class ActivityJson {
	private int id;
	private String activityImg;
	private Integer sort;
	private String name;
	public ActivityJson(){
		
	}
	public ActivityJson(Activity activity){
		this.id=activity.getId();
		this.activityImg=activity.getActivityImg();
		this.sort=activity.getSort();
		this.name=activity.getName();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getActivityImg() {
		return activityImg;
	}
	public void setActivityImg(String activityImg) {
		this.activityImg = activityImg;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
