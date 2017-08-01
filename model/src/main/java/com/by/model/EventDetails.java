package com.by.model;


import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.by.json.EventDetailsJson;

@Entity
@Table(name = "by_event_details")
public class EventDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String content;
	//活动开始时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "begin_time")
	private Calendar beginTime;
	//活动结束时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	private Calendar endTime;
	private String url;
	@Column(name = "cover_img")
	private String coverImg;
	//是否是最新活动
	@Column(name="is_new_activity")
	private Integer isNewActivity;
	public String getCoverImg() {
		return coverImg;
	}
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public Calendar getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Calendar beginTime) {
		this.beginTime = beginTime;
	}
	public Integer getIsNewActivity() {
		return isNewActivity;
	}
	public void setIsNewActivity(Integer isNewActivity) {
		this.isNewActivity = isNewActivity;
	}
	public Calendar getEndTime() {
		return endTime;
	}
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
	public EventDetailsJson toJson() {
		return new EventDetailsJson(this);
	}
}
