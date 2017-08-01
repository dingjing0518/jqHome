package com.by.controller;

import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.BannerJson;
import com.by.model.Banner;
import com.by.model.Menu;
import com.by.model.User;
import com.by.service.BannerService;
import com.by.service.MenuService;

/**
 * Created by yagamai on 15-12-9.
 */
@Controller
@RequestMapping("/admin/banners")
public class AdminBannerController extends BaseController {
	private static final String LIST="admin/banner/list";
	private static final String CREATE="admin/banner/create";
	private static final String EDIT="admin/banner/edit";
	private static final String REDIRECT = "redirect:/admin/banners/";
	
    private final Menu subMenu = new Menu(25);
    @Autowired
    private BannerService service;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("menus")
    public List<Menu> menus() {
        return menuService.findAll();
    }

    //Banner列表
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
    	List<Banner> banners = service.findAll(new Sort(Sort.Direction.ASC, "sort"));
        uiModel.addAttribute("lists", banners);
        return LIST;
    }

    // 新增Banner页面
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String form(Model uiModel) {
        Banner banner = new Banner();
        uiModel.addAttribute("banner", banner);
        return CREATE;
    }

    // 新增Banner
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("banner") Banner banner, BindingResult result, Model uiModel,
                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            uiModel.addAttribute("banner", banner);
            uiModel.addAttribute("message", failMessage(messageSource));
            return CREATE;
        }
        User user = userContext.getCurrentUser();
        banner.setCreatedBy(user.getName());
        banner.setCreatedTime(Calendar.getInstance());
        Banner ban = service.save(banner);
        redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
        return REDIRECT + ban.getId();
    }

    // 查看Banner详情
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(Model uiModel, @PathVariable("id") int id) {
        Banner banner = service.findOne(id);
        if (banner == null)
            throw new NotFoundException();
        uiModel.addAttribute("banner", banner);
        return EDIT;
    }

    // Banner列表json
    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Status list() {
        List<BannerJson> banners = service.findAllJson();
        return new Success<>(banners);
    }

    // 修改Banner信息
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String edit(@Valid @ModelAttribute("banner") Banner banner, BindingResult result, Model uiModel,
                       @PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            uiModel.addAttribute("banner", banner);
            uiModel.addAttribute("message", failMessage(messageSource));
            return EDIT;
        }
        banner.setId(id);
        banner.setUpdatedBy(userContext.getCurrentUser().getName());
        banner.setUpdatedTime(Calendar.getInstance());
        service.update(banner);
        redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
        return REDIRECT+ id;
    }

    @Override
    public Menu getSubMenu() {
        return subMenu;
    }
}
