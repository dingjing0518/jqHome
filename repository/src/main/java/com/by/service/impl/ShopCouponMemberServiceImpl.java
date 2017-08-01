package com.by.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.exception.AlreadyExchangeException;
import com.by.exception.AlreadyUsedException;
import com.by.exception.CouponNotBelongToMemberException;
import com.by.exception.CouponNotFoundException;
import com.by.exception.NotFoundException;
import com.by.exception.NotValidException;
import com.by.exception.OutOfStorageException;
import com.by.exception.ShopKeyNotMatchException;
import com.by.json.ShopCouponMemberJson;
import com.by.model.Member;
import com.by.model.Sequence;
import com.by.model.ShopCoupon;
import com.by.model.ShopCouponMember;
import com.by.repository.ShopCouponMemberRepository;
import com.by.service.MemberService;
import com.by.service.SequenceService;
import com.by.service.ShopCouponMemberService;
import com.by.service.ShopCouponService;
import com.by.typeEnum.ScoreHistoryEnum;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-8.
 */
@Service
@Transactional
public class ShopCouponMemberServiceImpl implements ShopCouponMemberService {
	@Value("${reason.coupon}")
	private String reason ;
	private final DateFormat format = new SimpleDateFormat("yyyyMMddS");
	@Autowired
	private ShopCouponMemberRepository repository;
	@Autowired
	private MemberService memberService;
	@Autowired
	private ShopCouponService shopCouponService;
	@Autowired
	private SequenceService sequenceService;

	@Override
	public void exchangeCoupon(Member member, ShopCoupon coupon, int total) {
		int count = total;
		Member m = memberService.findOne(member.getId());
		ShopCoupon shopCoupon = shopCouponService.findOne(coupon.getId());
		List<ShopCouponMember> lists = findByCouponAndMember(shopCoupon, m);
		if (shopCoupon == null)
			throw new NotFoundException();
		if (shopCoupon.isValid() && shopCoupon.isEffective(Calendar.getInstance())) {
			// 如果不能重复
			if (!shopCoupon.isDuplicateCoupon()) {
				if (lists.size() > 0)
					throw new AlreadyExchangeException();
				count = 1;
			}
			// 判断是否有库存
			if (!shopCoupon.noStorageLimited()) {
				if (outOfStorage(shopCoupon, count))
					throw new OutOfStorageException();
			}
			memberService.minusScore(m, shopCoupon.getScore() * count, reason, ScoreHistoryEnum.COUPONEXCHANGE);
			for (int i = 1; i <= count; i++) {
				save(shopCoupon, m);
			}
		} else {
			throw new NotValidException();
		}
	}

	private boolean outOfStorage(ShopCoupon coupon, int count) {
		Long total = countByCoupon(coupon);
		if (total == null)
			total = new Long(0);
		return total.intValue() == coupon.getTotal() || total.intValue() + count > coupon.getTotal();
	}

	@Override
	public Double useCoupon(String[] code, String mobile, String shopKey) {
		List<ShopCoupon> coupons = new ArrayList<>();
		Arrays.stream(code).forEach(i -> {
			ShopCouponMember shopCouponMember = repository.findByCode(i);
			if (shopCouponMember == null)
				throw new CouponNotFoundException();
			if (shopCouponMember.getUsedTime() != null)
				throw new AlreadyUsedException();
			if (!shopCouponMember.getMember().getName().equals(mobile)) {
				throw new CouponNotBelongToMemberException();
			}
			if (!shopCouponMember.getCoupon().getShop().getShopKey().equals(shopKey)) {
				throw new ShopKeyNotMatchException();
			}
			coupons.add(shopCouponMember.getCoupon());
			shopCouponMember.setUsedTime(Calendar.getInstance());
		});
		return coupons.stream().mapToDouble(i -> i.getAmount()).sum();
	}

	@Override
	public ShopCouponMember save(ShopCouponMember coupon) {
		return repository.save(coupon);
	}

	@Override
	public ShopCouponMember save(ShopCoupon coupon, Member member) {
		Sequence sequence = sequenceService.save(new Sequence());
		ShopCouponMember sm = new ShopCouponMember(member, coupon);
		sm.setCode(format.format(Calendar.getInstance().getTime()) + sequence.getId());
		return save(sm);
	}

	@Override
	public Long countByCoupon(ShopCoupon coupon) {
		return repository.countByCoupon(coupon);
	}

	@Override
	public List<ShopCouponMember> findByCouponAndMember(ShopCoupon coupon, Member member) {
		return repository.findByCouponAndMember(coupon, member);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ShopCouponMemberJson> findByMember(Member member, Pageable pageable) {
		Page<ShopCouponMember> lists = repository.findByMember(member, pageable);
		return new PageImpl<>(toJson(lists), pageable, lists.getTotalElements());
	}

	private List<ShopCouponMemberJson> toJson(Page<ShopCouponMember> lists) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return lists.getContent().stream().map(i -> {
			ShopCouponMemberJson c = new ShopCouponMemberJson();
			c.setId(i.getCoupon().getId());
			c.setCode(i.getCode());
			c.setExchangedTime(format.format(i.getExchangedTime().getTime()));
			c.setName(i.getCoupon().getName());
			c.setShopName(i.getCoupon().getShop().getName());
			return c;
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<ShopCouponMember> findByMemberAndShop(String name, String shopKey) {
		return repository.findByMemberAndShop(name, shopKey, ValidEnum.VALID, Calendar.getInstance());
	}

	@Override
	public ShopCouponMember findByCode(String code) {
		return repository.findByCode(code);
	}
}
