package com.by.json;

import com.by.model.Notify;

public class NotifyJson {
	private int id;
	private String title;
	private String content;
	private Integer sort;
	public NotifyJson(){
		
	}
	public NotifyJson(Notify notify){
		this.id=notify.getId();
		this.title=notify.getTitle();
		this.content=notify.getContent();
		this.sort=notify.getSort();
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
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
