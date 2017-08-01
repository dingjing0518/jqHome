package com.by.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.exception.Success;
import com.by.factory.ShoppingMallFactory;
import com.by.json.AdvertisementJson;
import com.by.model.FilmAdvertisement;
import com.by.model.Menu;
import com.by.model.ShoppingMall;
import com.by.service.FilmAdvertisementService;

/**
 * Created by yagamai on 16-3-30.
 */
@Controller
@RequestMapping("/admin/{mall}/films")
public class AdminFilmAdvController extends BaseController {
	private final String LIST = "admin/film/lists";
	private final String EDIT = "admin/film/edit";
	private final String CREATE = "admin/film/create";
	private final String REDIRECT = "redirect:/admin/";
	private final Menu subMenu = new Menu(29);
	@Autowired
	private FilmAdvertisementService service;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@PathVariable("mall") String mall, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE) Pageable pageable) {
		ShoppingMall m = ShoppingMallFactory.MALL.fromString(mall);
		Page<FilmAdvertisement> advs = service.findByMall(m, pageable);
		uiModel.addAttribute("lists", advs);
		uiModel.addAttribute("last", computeLastPage(advs.getTotalPages()));
		return LIST;
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Success<Page<AdvertisementJson>> listJson(@PathVariable("mall") String mall, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE) Pageable pageable) {
		ShoppingMall m = ShoppingMallFactory.MALL.fromString(mall);
		Page<FilmAdvertisement> advs = service.findByMall(m, pageable);
		List<AdvertisementJson> results = advs.getContent().stream().map(i -> i.toJson()).collect(Collectors.toList());
		return new Success<>(new PageImpl<AdvertisementJson>(results, pageable, advs.getTotalElements()));
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String save(Model uiModel) {
		uiModel.addAttribute("adv", new FilmAdvertisement());
		return CREATE;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") int id, Model uiModel, @PathVariable("mall") String mall) {
		FilmAdvertisement adv = service.findOne(id);
		uiModel.addAttribute("adv", adv);
		uiModel.addAttribute("mall", mall);
		return EDIT;
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(Model uiModel, @PathVariable("mall") String mall, @ModelAttribute("adv") FilmAdvertisement film,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("adv", film);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		film.setMall(ShoppingMallFactory.MALL.fromString(mall));
		FilmAdvertisement adv = service.save(film);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + mall + "/films/" + adv.getId();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") int id, Model uiModel, @PathVariable("mall") String mall,
			@ModelAttribute("adv") FilmAdvertisement film, BindingResult result,
			RedirectAttributes redirectAttributes) {
		film.setId(id);
		if (result.hasErrors()) {
			uiModel.addAttribute("adv", film);
			uiModel.addAttribute("message", failMessage(messageSource));
			return EDIT;
		}
		service.update(film);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + mall + "/films/" + id;
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
