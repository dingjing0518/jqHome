package com.by.form;

import java.util.Calendar;

import com.by.typeEnum.ShowOnIndex;

/**
 * Created by yagamai on 15-12-31.
 */
public class ActivityForm {
	private ShowOnIndex isShowOnIndex;
	private String name;
	private Calendar startTime;
	private Calendar endTime;

	public ShowOnIndex getIsShowOnIndex() {
		return isShowOnIndex;
	}

	public void setIsShowOnIndex(ShowOnIndex isShowOnIndex) {
		this.isShowOnIndex = isShowOnIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
}
