package com.by.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.exception.Fail;
import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.AdminMemberForm;
import com.by.json.MemberJson;
import com.by.json.UpdateScoreJson;
import com.by.model.Card;
import com.by.model.Member;
import com.by.model.MemberDetail;
import com.by.model.MemberStatics;
import com.by.model.Menu;
import com.by.model.User;
import com.by.security.UserContext;
import com.by.service.CardService;
import com.by.service.GiftCouponMemberService;
import com.by.service.MemberService;
import com.by.service.MemberStaticsService;
import com.by.service.ParkingCouponService;
import com.by.service.ParkingHistoryService;
import com.by.service.ScoreHistoryService;
import com.by.service.ShopCouponMemberService;
import com.by.service.TradingService;
import com.by.typeEnum.ScoreHistoryEnum;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-9.
 */
@Controller
@RequestMapping("/admin/members")
public class AdminMemberController extends BaseController {
	private final String EDIT = "admin/member/edit";
	private final String CREATE = "admin/member/create";
	private final String REDIRECT = "redirect:/admin/members/";
	private final Menu subMenu = new Menu(2);
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberStaticsService staticsService;
	@Autowired
	private UserContext userContext;
	@Autowired
	private CardService cardService;
	@Autowired
	private TradingService tradingService;
	@Autowired
	private ScoreHistoryService scoreHistoryService;
	@Autowired
	private ParkingHistoryService parkingHistoryService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private GiftCouponMemberService giftCouponMemberService;
	@Autowired
	private ShopCouponMemberService shopCouponMemberService;
	@Autowired
	private ParkingCouponService parkingCouponService;
	@Value("${cache.member}")
	private String url;
	@Value("${reason.admin.score.add}")
	private String ADMIN_MANUAL_ADD_SCORE;
	@Value("${reason.admin.score.minus}")
	private String ADMIN_MANUAL_MINUS_SCORE;
	@Value("${reason.score}")
	private String SCORE_SHOULD_BIGGER_THAN_ZERO;

	@ModelAttribute("cards")
	public List<Card> findValidCard() {
		return cardService.findAllCache().stream()
				.collect(Collectors.toList());
	}

	@ModelAttribute("allCards")
	public List<Card> findAllCard() {
		return cardService.findAllCache().stream().collect(Collectors.toList());
	}

	// 列表界面
	@RequestMapping(method = RequestMethod.GET)
	public String firstPage(@ModelAttribute("form") AdminMemberForm form, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE) Pageable pageable) {
		Page<MemberJson> members = memberService.findAll(form, pageable, ValidEnum.VALID);
		uiModel.addAttribute("lists", members);
		uiModel.addAttribute("last", computeLastPage(members.getTotalPages()));
		uiModel.addAttribute("form", form);
		return "admin/member/lists";
	}

	@RequestMapping(value = "/{card}/member", params = "form", method = RequestMethod.GET)
	public String cardForm(@PathVariable("card") int cardId, Model uiModel) {
		Member member = new Member();
		member.setCard(new Card(cardId));
		member.setMemberDetail(new MemberDetail());
		uiModel.addAttribute("member", member);
		return CREATE;
	}

	@RequestMapping(value = "/{card}/member", params = "form", method = RequestMethod.POST)
	public String create(@PathVariable("card") int card, @Valid @ModelAttribute("member") Member member,
			BindingResult result, Model uiModel, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("card", card);
			uiModel.addAttribute("message", failMessage(messageSource));
		}
		member.setCard(new Card(card));
		member.setCreatedBy(userContext.getCurrentUser().getName());
		member = memberService.save(member);
		cardService.addSignUpScore(member);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return "redirect:/admin/members/" + member.getId() + "?edit";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Status list(AdminMemberForm form,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<MemberJson> results = memberService.findAll(form, pageable, ValidEnum.VALID);
		return new Success<>(results);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Long id, Model uiModel) {
		Member member = memberService.findOne(id);
		if (member == null)
			throw new NotFoundException();
		findAllCard();
		uiModel.addAttribute("member", member);
		return EDIT;
	}

	@RequestMapping(value = "/score/add", method = RequestMethod.PUT)
	@ResponseBody
	public Status scoreAdd(@RequestBody UpdateScoreJson json) {
		if (json.getScore() < 0) {
			return new Fail(SCORE_SHOULD_BIGGER_THAN_ZERO);
		}
		Member member = memberService.addScore(new Member(json.getId()), json.getScore(), ADMIN_MANUAL_ADD_SCORE,
				ScoreHistoryEnum.ADMIN);
		return new Success<>(member.getScore());
	}

	@RequestMapping(value = "/score/minus", method = RequestMethod.PUT)
	@ResponseBody
	public Status scoreMinus(@RequestBody UpdateScoreJson json) {
		if (json.getScore() < 0) {
			return new Fail("需大于零");
		}
		Member member = memberService.minusScore(new Member(json.getId()), json.getScore(), ADMIN_MANUAL_MINUS_SCORE,
				ScoreHistoryEnum.ADMIN);
		return new Success<>(member.getScore());
	}

	@RequestMapping(value = "/{id}/validate", method = RequestMethod.PUT)
	public String validate(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		User user = userContext.getCurrentUser();
		memberService.validateOrInValidate(new Member(id), user.getName());
		new RestTemplate().put(url + id, null);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + id;
	}

	@RequestMapping(value = "/{id}/use", method = RequestMethod.GET)
	@ResponseBody
	public Success<MemberStatics> consume(@PathVariable("id") Long id) {
		Member member = new Member(id);
		return new Success<>(staticsService.findOne(member));
	}

	@RequestMapping(value = "/{id}/trading", method = RequestMethod.GET)
	@ResponseBody
	public Status trading(@PathVariable("id") Long id,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		return new Success<>(tradingService.findByMember(new Member(id), pageable));
	}

	@RequestMapping(value = "/{id}/score", method = RequestMethod.GET)
	@ResponseBody
	public Status score(@PathVariable("id") Long id,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		return new Success<>(scoreHistoryService.findByMemberJson(new Member(id), pageable));
	}

	@RequestMapping(value = "/{id}/parking", method = RequestMethod.GET)
	@ResponseBody
	public Status parking(@PathVariable("id") Long id,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "startTime", direction = Sort.Direction.DESC) Pageable pageable) {
		return new Success<>(
				parkingHistoryService.toJson(parkingHistoryService.findByMember(new Member(id), pageable), pageable));
	}

	@RequestMapping(value = "/{id}/giftCoupons", method = RequestMethod.GET)
	@ResponseBody
	public Status giftCoupons(@PathVariable("id") Long id,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "exchangedTime", direction = Sort.Direction.DESC) Pageable pageable) {
		return new Success<>(giftCouponMemberService.findByMember(new Member(id), pageable));
	}

	@RequestMapping(value = "/{id}/shopCoupons", method = RequestMethod.GET)
	@ResponseBody
	public Status shopCoupons(@PathVariable("id") Long id,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "exchangedTime", direction = Sort.Direction.DESC) Pageable pageable) {
		return new Success<>(shopCouponMemberService.findByMember(new Member(id), pageable));
	}

	@RequestMapping(value = "/{id}/parkingCoupons", method = RequestMethod.GET)
	@ResponseBody
	public Status parkingCoupons(@PathVariable("id") Long id,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE) Pageable pageable) {
		return new Success<>(parkingCouponService.findByMemberHistory(new Member(id), pageable));
	}

	@RequestMapping(value = "/duplicate", method = RequestMethod.GET)
	@ResponseBody
	public String duplicate(@RequestParam("name") String name) {
		Long count = memberService.countByName(name);
		if (count > 0)
			return "false";
		return "true";
	}

	@RequestMapping(value = "/exist", method = RequestMethod.GET)
	@ResponseBody
	public String exist(@RequestParam("name") String name) {
		if (memberService.countByName(name) > 0)
			return "true";
		return "false";
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
