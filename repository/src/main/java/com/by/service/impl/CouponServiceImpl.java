package com.by.service.impl;

import com.by.exception.AdminNotFoundException;
import com.by.form.BaseCouponForm;
import com.by.json.CouponJson;
import com.by.model.*;
import com.by.repository.CouponRepository;
import com.by.repository.GiftCouponMemberRepository;
import com.by.repository.ShopCouponMemberRepository;
import com.by.service.*;
import com.by.typeEnum.CouponAdminStateEnum;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {
	@Autowired
	private GiftCouponMemberRepository giftCouponMemberRepository;
	@Autowired
	private ShopCouponMemberRepository shopCouponMemberRepository;
	@Autowired
	private ParkingCouponService parkingCouponService;
	@Autowired
	private GiftCouponService giftCouponService;
	@Autowired
	private ShopCouponService shopCouponService;
	@Autowired
	private ParkingCouponMemberService parkingCouponMemberService;
	@Autowired
	private CouponRepository repository;

	@Override
	public <T extends Coupon> List<Predicate> getPredicateList(BaseCouponForm form, Root<T> root, CriteriaBuilder cb) {
		List<Predicate> criteria = new ArrayList<>();
		if (form.getState() != null) {
			Calendar today = Calendar.getInstance();
			if (form.getState().equals(CouponAdminStateEnum.CLOSED)) {
				criteria.add(cb.equal(root.get("valid"), ValidEnum.INVALID));
			} else if (form.getState().equals(CouponAdminStateEnum.NOEXPIRE)) {
				criteria.add(cb.greaterThan(root.get("beginTime"), today));
				criteria.add(cb.and(cb.equal(root.get("valid"), ValidEnum.VALID)));
			} else if (form.getState().equals(CouponAdminStateEnum.USING)) {
				criteria.add(cb.or(
						cb.and(cb.lessThanOrEqualTo(root.get("beginTime"), today),
								cb.greaterThanOrEqualTo(root.get("endTime"), today)),
						cb.and(cb.isNull(root.get("beginTime"))), cb.isNull(root.get("endTime"))));
				criteria.add(cb.equal(root.get("valid"), ValidEnum.VALID));
			} else if (form.getState().equals(CouponAdminStateEnum.EXPIRE)) {
				criteria.add(cb.lessThan(root.get("endTime"), today));
				criteria.add(cb.and(cb.notEqual(root.get("valid"), ValidEnum.INVALID)));
			}
		}
		if (form.getBeginTime() != null) {
			if (form.getEndTime() != null)
				criteria.add(cb.or(
						cb.and(cb.lessThanOrEqualTo(root.get("beginTime"), form.getEndTime()),
								cb.greaterThanOrEqualTo(root.get("beginTime"), form.getBeginTime())),
						cb.and(cb.isNull(root.get("beginTime")), cb.isNull(root.get("endTime")))));
			else
				criteria.add(cb.or(
						cb.greaterThanOrEqualTo(root.get("beginTime"), form.getBeginTime()),
						cb.and(cb.isNull(root.get("beginTime")), cb.isNull(root.get("endTime")))));
		}
		return criteria;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CouponJson> findAll(Pageable pageable) {
		Calendar today = Calendar.getInstance();
		Page<Coupon> coupons = repository.findAllByValidAndDateBetween(ValidEnum.VALID, today, pageable,
				ParkingCoupon.class);
		List<CouponJson> couponJson = coupons.getContent().stream().map(i -> i.toCouponJson())
				.collect(Collectors.toList());
		Long total = coupons.getTotalElements();
		if (pageable.getPageNumber() == 0) {
			ParkingCoupon parkingCoupon = parkingCouponService.findActivate();
			CouponJson json = new CouponJson(parkingCoupon);
			json.setType("p");
			couponJson.add(0, json);
			total++;
		}
		return new PageImpl<>(couponJson, pageable, total);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CouponJson> findByMember(Member member, Pageable pageable) {
		Calendar today = Calendar.getInstance();
		List<GiftCouponMember> giftCouponList = giftCouponMemberRepository.findByMemberAndValid(member, ValidEnum.VALID,
				today);
		List<ShopCouponMember> shopCouponMemberList = shopCouponMemberRepository.findByMemberAndValid(member,
				ValidEnum.VALID, today);
		List<CouponJson> giftJson = giftCouponList.stream().map(i -> i.toCouponJson()).collect(Collectors.toList());
		List<CouponJson> shopJson = shopCouponMemberList.stream().map(i -> i.toCouponJson())
				.collect(Collectors.toList());
		giftJson.addAll(shopJson);
		giftJson.sort((o1, o2) -> {
			if (o1.getCouponTime() == null)
				return o2.getCouponTime() == null ? 0 : -1;
			if (o2.getCouponTime() == null)
				return 1;
			return o1.getCouponTime().before(o2.getCouponTime()) ? -1 : 1;
		});
		Integer max = giftJson.size();
		if (pageable.getPageNumber() == 0) {
			addParkingCoupon(giftJson, member);
			max++;
		}
		return new PageImpl<>(giftJson.stream().skip(pageable.getPageNumber() * pageable.getPageSize())
				.limit(pageable.getPageSize()).collect(Collectors.toList()), pageable, max);
	}

	private void addParkingCoupon(List<CouponJson> jsons, Member member) {
		List<ParkingCouponMember> lists = parkingCouponMemberService.findByMember(member,
				new Sort(Direction.DESC, "total"));
		List<CouponJson> json = lists.stream().map(i -> i.toCouponJson()).collect(Collectors.toList());
		for (int i = 0; i < json.size(); i++) {
			jsons.add(0, json.get(i));
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CouponJson> findByNameLike(String name, Pageable pageable) {
		Page<GiftCoupon> giftCoupons = giftCouponService.findAllByValidAndDateBetweenAndNameLike(ValidEnum.VALID, name,
				Calendar.getInstance(), pageable);
		Page<ShopCoupon> shopCoupons = shopCouponService.findAllByValidAndDateBetweenAndNameLike(ValidEnum.VALID,
				Calendar.getInstance(), name, pageable);
		List<CouponJson> results = new ArrayList<>();
		List<CouponJson> giftJson = giftCoupons.getContent().stream().map(i -> i.toCouponJson())
				.collect(Collectors.toList());
		List<CouponJson> shopJson = shopCoupons.getContent().stream().map(i -> i.toCouponJson())
				.collect(Collectors.toList());
		results.addAll(giftJson);
		results.addAll(shopJson);
		results.sort((o1, o2) -> {
			if (o1.getCouponTime().before(o2.getCouponTime()))
				return 1;
			return -1;
		});
		ParkingCoupon parkingCoupon = parkingCouponService.findActivate();
		int max = results.size();
		if (pageable.getPageNumber() == 0) {
			if (parkingCoupon.getName().contains(name)) {
				results.add(new CouponJson(parkingCoupon));
				max++;
			}
		}
		return new PageImpl<>(results.stream().limit(pageable.getPageSize()).collect(Collectors.toList()), pageable,
				max);
	}

	// 如果要将一个优惠券置有效,只有未生效的优惠券才可以从无效状态变为有效状态
	@Override
	@Transactional
	public Coupon valid(int id) {
		Coupon coupon = repository.findOne(id);
		if (coupon == null)
			throw new AdminNotFoundException();
		if (coupon.getValid().equals(ValidEnum.INVALID)) {
			Calendar today = Calendar.getInstance();
			if (coupon.notEffective(today))
				coupon.setValid(ValidEnum.VALID);
		} else
			coupon.setValid(ValidEnum.INVALID);
		return coupon;
	}
}
