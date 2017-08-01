package com.by.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * Created by yagamai on 15-12-22.
 */
@Entity
@DiscriminatorValue(value = "m")
public class MenuCategory extends Category {
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<Menu> menus;

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

}
