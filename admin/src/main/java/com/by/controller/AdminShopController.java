package com.by.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
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
import com.by.json.CategoryJson;
import com.by.json.ShopJson;
import com.by.model.Floor;
import com.by.model.Menu;
import com.by.model.Shop;
import com.by.model.ShopCategory;
import com.by.model.User;
import com.by.service.FloorService;
import com.by.service.MenuService;
import com.by.service.ShopCategoryService;
import com.by.service.ShopRuleCacheService;
import com.by.service.ShopService;

/**
 * Created by yagamai on 15-12-9.
 */
@Controller
@RequestMapping("/admin/shops")
public class AdminShopController extends BaseController {
    private final Menu subMenu = new Menu(12);
    @Autowired
    private ShopService service;
    @Autowired
    private MenuService menuService;
    @Autowired
    @Qualifier("shopKeyUniqueValidator")
    private Validator shopKeyUniqueValidator;
    @Autowired
    @Qualifier("shopKeyValidator")
    private Validator shopKeyValidator;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private FloorService floorService;
    @Autowired
    private ShopRuleCacheService shopRuleCacheService;

    @ModelAttribute("categories")
    public List<ShopCategory> categories() {
        return shopCategoryService.findAllFirstCategory();
    }
    
    @ModelAttribute("floors")
    public List<Floor> floors() {
        return floorService.findAll();
    }

    @ModelAttribute("menus")
    public List<Menu> menus() {
        return menuService.findAll();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Status all() {
        return new Success<>(service.toJson(service.findAll(new Sort(Sort.Direction.ASC, "name"))));
    }

    // 店铺列表
    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(required = false, value = "name") String name, Model uiModel,
                       @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE) Pageable pageable) {
        Page<Shop> pages = service.findAll(name, pageable);
        uiModel.addAttribute("lists", pages);
        uiModel.addAttribute("last", computeLastPage(pages.getTotalPages()));
        uiModel.addAttribute("name", name);
        return "admin/shop/list";
    }

    // 新增店铺页面
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String form(Model uiModel) {
        Shop shop = new Shop();
        uiModel.addAttribute("shop", shop);
        return "admin/shop/create";
    }

    // 增加一个店铺
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("shop") Shop shop, BindingResult result, Model uiModel,
                      RedirectAttributes redirectAttributes) {
        shopKeyUniqueValidator.validate(shop, result);
        if (result.hasErrors()) {
            uiModel.addAttribute("shop", shop);
            uiModel.addAttribute("message", failMessage(messageSource));
            return "admin/shop/create";
        }
        User user = userContext.getCurrentUser();
        shop.setCreatedBy(user.getName());
        shop.setUpdatedBy(user.getName());
        Shop s = service.save(shop);
        service.refresh(new Sort(Sort.Direction.ASC, "name"));
        redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
        return "redirect:/admin/shops/" + s.getId();
    }

    // 查看店铺详情
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(Model uiModel, @PathVariable("id") int id) {
        Shop shop = service.findOne(id);
        if (shop == null)
            throw new NotFoundException();
        uiModel.addAttribute("shop", shop);
        List<ShopCategory> secondCategorires = shopCategoryService.findCategoryByParent(shop.getFirstCategory());
        uiModel.addAttribute("secondCategories", secondCategorires);
        uiModel.addAttribute("tuple",shopRuleCacheService.getShopRateAndScore(id));
        return "admin/shop/edit";
    }

    // 店铺列表json
    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Status list(@RequestParam(required = false, value = "name") String name,
                       @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ShopJson> pages = service.findAllJson(name, pageable);
        return new Success<>(pages);
    }

    // 修改店铺信息
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String edit(@Valid @ModelAttribute("shop") Shop shop, BindingResult result, Model uiModel,
                       @PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        shopKeyValidator.validate(shop, result);
        if (result.hasErrors()) {
            uiModel.addAttribute("shop", shop);
            uiModel.addAttribute("message", failMessage(messageSource));
            return "admin/shop/edit";
        }
        shop.setId(id);
        shop.setUpdatedBy(userContext.getCurrentUser().getName());
        service.update(shop);
        redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
        return "redirect:/admin/shops/" + id;
    }

    @RequestMapping(value = "/key/duplicate", method = RequestMethod.GET)
    @ResponseBody
    public boolean duplicate(@RequestParam("shopKey") String shopKey,
                             @RequestParam(value = "id", required = false) Integer id) {
        long count;
        if (id == null) {
            count = service.countByShopKey(shopKey);
            return count > 0 ? false : true;
        } else {
            Shop shop = service.findByShopKey(shopKey);
            if (shop != null)
                return !id.equals(shop.getId()) ? false : true;
            return true;
        }
    }

    @RequestMapping(value = "/secondCategories/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<CategoryJson> findByFirstCategory(@PathVariable("id") int id) {
        return shopCategoryService.findCategoryByParent(new ShopCategory(id)).stream().map(i -> i.toJson())
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/name/duplicate", method = RequestMethod.GET)
    @ResponseBody
    public boolean nameDuplicate(@RequestParam("name") String name,
                                 @RequestParam(value = "id", required = false) Integer id) {
        Long count;
        if (id == null) {
            count = service.countByName(name);
            return count > 0 ? false : true;
        } else {
            Shop shop = service.findByName(name);
            if (shop != null)
                return !id.equals(shop.getId()) ? false : true;
            return true;
        }
    }

    @Override
    public Menu getSubMenu() {
        return subMenu;
    }
}
