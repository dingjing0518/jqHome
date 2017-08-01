package com.by.json;

import java.text.SimpleDateFormat;

import com.by.model.EventDetails;

public class EventDetailsJson {
	private int id;
	private String title;
	private String content;
	private String beginTime;
	private String endTime;
	private String url;
	private String coverImg;
	private int isNewActivity;
	public EventDetailsJson(EventDetails eventDetails){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		this.id=eventDetails.getId();
		this.title=eventDetails.getTitle();
		this.content=eventDetails.getContent();
		this.beginTime=format.format(eventDetails.getBeginTime().getTime());
		this.endTime=format.format(eventDetails.getEndTime().getTime());
		this.url=eventDetails.getUrl();
		this.coverImg=eventDetails.getCoverImg();
		this.isNewActivity=eventDetails.getIsNewActivity();
	}
	
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public int getIsNewActivity() {
		return isNewActivity;
	}

	public void setIsNewActivity(int isNewActivity) {
		this.isNewActivity = isNewActivity;
	}
	
}
