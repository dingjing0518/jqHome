package com.by.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.by.model.User;
import com.by.service.MenuCategoryService;

public class AuthenticationSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired
	private MenuCategoryService menuCategoryService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		User user = (User) authentication.getPrincipal();
		request.getSession().setAttribute("menus", menuCategoryService.getCategoryAndMenu(user));
		request.getSession().setAttribute("userName", user.getName());
		setDefaultTargetUrl("/admin/index");
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
