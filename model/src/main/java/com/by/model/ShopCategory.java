package com.by.model;

import com.by.json.CategoryJson;
import com.by.typeEnum.ValidEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yagamai on 16-3-24.
 */
@Entity
@DiscriminatorValue(value = "s")
public class ShopCategory extends Category {
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	private List<ShopCategory> children = new ArrayList<ShopCategory>();

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private ShopCategory parent;

	@Enumerated(EnumType.ORDINAL)
	private ValidEnum valid;

	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Calendar createdTime;

	@Column(name = "updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_time")
	private Calendar updatedTime;

	public ShopCategory() {
	}

	public ShopCategory(int id) {
		this.id = id;
	}

	public List<ShopCategory> getChildren() {
		return children;
	}

	public void setChildren(List<ShopCategory> children) {
		this.children = children;
	}

	public ShopCategory getParent() {
		return parent;
	}

	public void setParent(ShopCategory parent) {
		this.parent = parent;
	}

	public ValidEnum getValid() {
		return valid;
	}

	public void setValid(ValidEnum valid) {
		this.valid = valid;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Calendar getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Calendar updatedTime) {
		this.updatedTime = updatedTime;
	}

	public CategoryJson toJson() {
		CategoryJson json = new CategoryJson();
		json.setId(this.getId());
		json.setName(this.getName());
		return json;
	}

	@PrePersist
	private void prePersist() {
		this.createdTime = Calendar.getInstance();
		if (this.updatedTime == null)
			this.updatedTime = Calendar.getInstance();
		this.valid = ValidEnum.VALID;
	}

	@PreUpdate
	private void preUpdate() {
		this.updatedTime = Calendar.getInstance();
	}
}
