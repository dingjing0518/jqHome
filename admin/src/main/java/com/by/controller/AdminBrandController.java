package com.by.controller;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import com.by.json.BrandJson;
import com.by.model.Brand;
import com.by.model.Menu;
import com.by.model.ShopCategory;
import com.by.model.User;
import com.by.service.BrandService;
import com.by.service.MenuService;
import com.by.service.ShopCategoryService;

@Controller
@RequestMapping("/admin/brand")
public class AdminBrandController extends BaseController {
	private final Menu subMenu = new Menu(13);
	@Autowired
	private BrandService brandService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private MessageSource messageSource;

	@ModelAttribute("categories")
	public List<ShopCategory> categories() {
		return shopCategoryService.findAllFirstCategory();
	}

	@ModelAttribute("menus")
	public List<Menu> menus() {
		return menuService.findAll();
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Status all() {
		return new Success<>(brandService.toJson(brandService.findAll(new Sort(Sort.Direction.ASC, "name"))));
	}

	// 品牌列表
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(required = false, value = "name") String name, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE) Pageable pageable) {
		Page<Brand> pages = brandService.findAll(name, pageable);
		uiModel.addAttribute("lists", pages);
		uiModel.addAttribute("last", computeLastPage(pages.getTotalPages()));
		uiModel.addAttribute("name", name);
		return "admin/brand/list";
	}

	// 品牌列表json
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Status list(@RequestParam(required = false, value = "name") String name,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<BrandJson> pages = brandService.findAllJson(name, pageable);
		return new Success<>(pages);
	}

	// 新增一个品牌页面
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String form(Model uiModel) {
		Brand brand = new Brand();
		uiModel.addAttribute("brand", brand);
		return "admin/brand/create";
	}

	// 增加一个品牌
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("brand") Brand brand, BindingResult result, Model uiModel,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("brand", brand);
			uiModel.addAttribute("message", failMessage(messageSource));
			return "admin/brand/create";
		}
		User user = userContext.getCurrentUser();
		brand.setCreated_by(user.getName());
		brand.setUpdated_by(user.getName());
		brand.setStatus(0);
		Brand s = brandService.save(brand);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return "redirect:/admin/brand/" + s.getId();
	}

	// 检测品牌重复性
	@RequestMapping(value = "/name/duplicate", method = RequestMethod.GET)
	@ResponseBody
	public boolean nameDuplicate(@RequestParam("name") String name,
			@RequestParam(value = "id", required = false) Integer id) {
		Long count;
		if (id == null) {
			count = brandService.countByName(name);
			return count > 0 ? false : true;
		} else {
			Brand brand = brandService.findByName(name);
			if (brand != null)
				return !id.equals(brand.getId()) ? false : true;
			return true;
		}
	}

	// 查看品牌详情
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String view(Model uiModel, @PathVariable("id") int id) {
		Brand brand = brandService.findOne(id);
		if (brand == null)
			throw new NotFoundException();
		uiModel.addAttribute("brand", brand);
		return "admin/brand/edit";
	}

	// 修改品牌信息
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String edit(@Valid @ModelAttribute("brand") Brand brand, BindingResult result, Model uiModel,
			@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("brand", brand);
			uiModel.addAttribute("message", failMessage(messageSource));
			return "admin/brand/edit";
		}
		brand.setId(id);
		brand.setUpdated_by(userContext.getCurrentUser().getName());
		brandService.update(brand);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return "redirect:/admin/brand/" + id;
	}
	//修改品牌的状态值
		@RequestMapping(value="/status", method = RequestMethod.POST)
		@ResponseBody
		public String updataBrandStatus(@RequestParam("data") String data,@RequestParam("name") String name){
			Brand brand = brandService.findByName(name);
			brand.setStatus(new Integer(data));
			brand.setUpdated_by(userContext.getCurrentUser().getName());
			brandService.update(brand);
			return "success";
			}
	@Override
	public Menu getSubMenu() {
		return subMenu;
	}

}
