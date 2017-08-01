package com.by.controller;

import org.springframework.stereotype.Component;

/**
 * Created by yagamai on 15-12-16.
 */
@Component
public abstract class BaseController{
	protected final int INIT_PAGE = 0;
	protected final int PAGE_SIZE = 3;
	private final int maxSize = 7;

	protected int computeLastPage(int totalPages) {
		if (maxSize > totalPages)
			return totalPages == 0 ? 1 : totalPages;
		else
			return maxSize;
	}

}
