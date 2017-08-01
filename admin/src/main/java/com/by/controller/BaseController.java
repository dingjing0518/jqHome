package com.by.controller;

import java.util.Calendar;
import java.util.Locale;

import com.by.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.by.security.UserContext;
import com.by.service.MenuCategoryService;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-16.
 */
@Component
public abstract class BaseController implements UtilContoller {
	protected final int INIT_PAGE = 0;
	protected final int PAGE_SIZE = 10;
	private final int maxSize = 7;
	@Autowired
	protected MenuCategoryService menuCategoryService;
	@Autowired
	protected UserContext userContext;

	private enum BarStatus {
		USING("using-bar"), INVALID("close-bar"), OUTOFDATE("cancel-bar"), WAITING("edit-bar");
		private final String name;

		BarStatus(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	@ModelAttribute("subMenu")
	public Menu subMenu() {
		return getSubMenu();
	}

	protected int computeLastPage(int totalPages) {
		if (maxSize > totalPages)
			return totalPages == 0 ? 1 : totalPages;
		else
			return maxSize;
	}

	protected StatusMessage getStatusMessage(TimeRange coupon) {
		ValidEnum valid = coupon.getValid();
		Calendar beginTime = coupon.getBeginTime();
		Calendar endTime = coupon.getEndTime();
		return buildMessage(beginTime, endTime, valid);
	}

	protected StatusMessage buildMessage(Calendar beginTime, Calendar endTime, ValidEnum valid) {
		Calendar today = Calendar.getInstance();
		if (valid.equals(ValidEnum.INVALID)) {
			if (beginTime == null && endTime == null) {
				return getMessage(false, true, "已关闭", BarStatus.INVALID);
			} else if (beginTime != null && endTime != null && beginTime.after(today) && endTime.after(today)) {
				return getMessage(true, true, "已关闭", BarStatus.INVALID);
			} else if (beginTime != null && endTime != null && beginTime.before(today) && endTime.after(today)) {
				return getMessage(false, true, "已关闭", BarStatus.INVALID);
			} else {
				return getMessage(false, false, "已过期", BarStatus.INVALID);
			}
		} else {
			if (beginTime != null && endTime != null && beginTime.after(today) && endTime.after(today)) {
				return getMessage(true, true, "未生效", BarStatus.WAITING);
			} else if (beginTime != null && endTime != null && beginTime.before(today) && endTime.before(today)) {
				return getMessage(false, false, "已过期", BarStatus.OUTOFDATE);
			} else {
				return getMessage(false, true, "使用中", BarStatus.USING);
			}
		}
	}

	private StatusMessage getMessage(boolean canUpdate, boolean canclosed, String state, BarStatus bar) {
		StatusMessage message = new StatusMessage();
		message.setStatus(bar.getName());
		message.setCanValid(canclosed);
		message.setCanUpdate(canUpdate);
		message.setState(state);
		return message;
	}

	public abstract Menu getSubMenu();

	protected Message successMessage(MessageSource messageSource) {
		return new Message("bg-success", messageSource.getMessage("save.success", new Object[] {}, Locale.CHINESE));
	}

	protected Message failMessage(MessageSource messageSource) {
		return new Message("bg-danger", messageSource.getMessage("save.fail", new Object[] {}, Locale.CHINESE));
	}
}
