package com.by.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.by.exception.NotPermittedException;
import com.by.model.Menu;
import com.by.model.MenuCategory;

public class RoleInteceptor extends HandlerInterceptorAdapter {
	private static Logger log = LoggerFactory.getLogger(RoleInteceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String[] menu = request.getRequestURI().substring(request.getContextPath().length()).split("\\/");
		log.debug(request.getRequestURI());
		log.debug(menu[2]);

		Map<MenuCategory, List<Menu>> menus = (Map<MenuCategory, List<Menu>>) request.getSession()
				.getAttribute("menus");
		boolean flag = false;
		if (menu[2].equals("index") || menu[2].equals("upload")||menu[2].equals("trading")) {
			return true;
		}

		for (List<Menu> l : menus.values()) {
			for (Menu m : l) {
				String[] href = m.getHref().split("\\/");
				if (href[2].equals(menu[2])) {
					flag = true;
					break;
				}
			}
		}
		if (flag == false) {
			throw new NotPermittedException();
		}
		return flag;
	}

}
