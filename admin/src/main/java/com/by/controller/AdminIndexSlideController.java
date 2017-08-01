package com.by.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.SlideJson;
import com.by.model.IndexSlide;
import com.by.model.Menu;
import com.by.model.User;
import com.by.service.IndexSlideService;
import com.by.service.MenuService;

/**
 * Created by yagamai on 15-12-9.
 */
@Controller
@RequestMapping("/admin/slides/index")
public class AdminIndexSlideController extends BaseController {
	private static final String LIST="admin/slide/index/list";
	private static final String CREATE="admin/slide/index/create";
	private static final String EDIT="admin/slide/index/edit";
	private static final String REDIRECT = "redirect:/admin/slides/index/";
	
    private final Menu subMenu = new Menu(31);
    @Autowired
    private IndexSlideService service;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("menus")
    public List<Menu> menus() {
        return menuService.findAll();
    }

    //Slide列表
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
    	List<IndexSlide> slides = service.findAll();
        uiModel.addAttribute("lists", slides);
        return LIST;
    }

    // 新增Slide页面
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String form(Model uiModel) {
        IndexSlide slide = new IndexSlide();
        uiModel.addAttribute("slide", slide);
        return CREATE;
    }

    // 新增Slide
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String add(@ModelAttribute("slide") IndexSlide slide, BindingResult result, Model uiModel,
                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            uiModel.addAttribute("slide", slide);
            uiModel.addAttribute("message", failMessage(messageSource));
            return CREATE;
        }
        User user = userContext.getCurrentUser();
        slide.setCreatedBy(user.getName());
        slide.setCreatedTime(Calendar.getInstance());
        slide.setStatus(0);
        IndexSlide ban = service.save(slide);
        redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
        return REDIRECT + ban.getId();
    }

    // 查看Slide详情
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(Model uiModel, @PathVariable("id") int id) {
        IndexSlide slide = service.findOne(id);
        if (slide == null)
            throw new NotFoundException();
        uiModel.addAttribute("slide", slide);
        return EDIT;
    }

    // Slide列表json
    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Status list() {
        List<SlideJson> slides = service.findAllJson();
        return new Success<>(slides);
    }

    // 修改Slide信息
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String edit(@ModelAttribute("slide") IndexSlide slide, BindingResult result, Model uiModel,
                       @PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            uiModel.addAttribute("slide", slide);
            uiModel.addAttribute("message", failMessage(messageSource));
            return EDIT;
        }
        slide.setId(id);
        slide.setUpdatedBy(userContext.getCurrentUser().getName());
        slide.setUpdatedTime(Calendar.getInstance());
        service.update(slide);
        redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
        return REDIRECT+ id;
    }
    //修改Slide状态
    @RequestMapping(value="/status", method = RequestMethod.POST)
	@ResponseBody
	public String updataIndexStatus(@RequestParam("data") String data,@RequestParam("id") String id){
    	 IndexSlide slide = service.findOne(new Integer(id));
    	 slide.setStatus(new Integer(data));
    	 slide.setUpdatedBy(userContext.getCurrentUser().getName());
    	 service.update(slide);
		return "success";
		}
    @Override
    public Menu getSubMenu() {
        return subMenu;
    }
}
