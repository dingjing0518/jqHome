package com.by.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.Menu;
import com.by.model.MenuCategory;
import com.by.model.User;
import com.by.service.MenuCategoryService;
import com.by.service.UserService;

/**
 * Created by yagamai on 15-12-22.
 */
@Service
@Transactional
public class MenuCategoryServiceImpl implements MenuCategoryService {
	@Autowired
	private UserService userService;

	@Override
	public Map<MenuCategory, List<Menu>> getCategoryAndMenu(User user) {
		Set<Menu> sets = userService.getMenus(user);
		Map<MenuCategory, List<Menu>> results = new HashMap<>();
		sets.forEach(i -> {
			if (!results.containsKey(i.getCategory())) {
				List<Menu> l = new ArrayList<>();
				l.add(i);
				results.put(i.getCategory(), l);
			} else {
				results.get(i.getCategory()).add(i);
			}
		});
		return results;
	}

	@CacheEvict(value = "userMenu",allEntries=true)
	public void refreshCache() {

	}
}
