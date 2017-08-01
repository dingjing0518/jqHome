package com.by.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class CouponInfoPerDay {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_time")
	protected Calendar createdTime;

	@Column(name = "use_number")
	protected int useNumber;

	@Column(name = "exchange_number")
	protected int exchangeNumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar time) {
		this.createdTime = time;
	}

	public int getUseNumber() {
		return useNumber;
	}

	public void setUseNumber(int useNumber) {
		this.useNumber = useNumber;
	}

	public int getExchangeNumber() {
		return exchangeNumber;
	}

	public void setExchangeNumber(int exchangeNumber) {
		this.exchangeNumber = exchangeNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CouponInfoPerDay other = (CouponInfoPerDay) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
