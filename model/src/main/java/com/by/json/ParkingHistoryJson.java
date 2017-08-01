package com.by.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.by.model.ParkingHistory;

public class ParkingHistoryJson {
	private String startTime;
	private String endTime;
	private String license;
	private Float amount;

	public ParkingHistoryJson(ParkingHistory history) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.startTime = format.format(history.getStartTime().getTime());
		this.endTime = format.format(history.getEndTime().getTime());
		this.license = history.getLicense();
		this.amount = history.getAmount();
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}
}
