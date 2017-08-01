package com.by.controller;

import com.by.factory.ShoppingMallFactory;
import com.by.model.Menu;
import com.by.model.ScrollBarAdvertisement;
import com.by.service.ScrollBarAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by yagamai on 16-3-29.
 */
@Controller
@RequestMapping("/admin/{mall}/scrollBar")
public class AdminScrollBarAdvController extends BaseController {
    private final Menu subMenu = new Menu(25);
    private final String LIST = "admin/scrollBar/lists";
    private final String EDIT = "admin/scrollBar/edit";
    private final String REDIRECT = "redirect:/admin/jq/scrollBar/";
    @Autowired
    private ScrollBarAdvertisementService service;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel, @PathVariable("mall") String m) {
        List<ScrollBarAdvertisement> lists = service.findByMall(ShoppingMallFactory.MALL.fromString(m));
        uiModel.addAttribute("lists", lists);
        return LIST;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") int id, Model uiModel) {
        ScrollBarAdvertisement adv = service.findOne(id);
        uiModel.addAttribute("adv", adv);
        return EDIT;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable("id") int id, Model uiModel, RedirectAttributes redirectAttributes,
                         @Valid @ModelAttribute("adv") ScrollBarAdvertisement adv, BindingResult result) {
        adv.setId(id);
        if (result.hasErrors()) {
            uiModel.addAttribute("adv", adv);
            uiModel.addAttribute("message", failMessage(messageSource));
            return EDIT;
        }
        ScrollBarAdvertisement sba = service.update(adv);
        redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
        return REDIRECT + sba.getId();
    }

    @Override
    public Menu getSubMenu() {
        return subMenu;
    }
}
