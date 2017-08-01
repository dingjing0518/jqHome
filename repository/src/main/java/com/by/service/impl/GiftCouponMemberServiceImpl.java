package com.by.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.exception.AlreadyExchangeException;
import com.by.exception.AlreadyUsedException;
import com.by.exception.CouponNotFoundException;
import com.by.exception.NotFoundException;
import com.by.exception.NotValidException;
import com.by.exception.OutOfStorageException;
import com.by.json.CouponMemberJson;
import com.by.model.GiftCoupon;
import com.by.model.GiftCouponMember;
import com.by.model.Member;
import com.by.model.Sequence;
import com.by.repository.GiftCouponMemberRepository;
import com.by.service.GiftCouponMemberService;
import com.by.service.GiftCouponService;
import com.by.service.MemberService;
import com.by.service.SequenceService;
import com.by.typeEnum.ScoreHistoryEnum;
import com.by.typeEnum.ValidEnum;

/**
 * Created by yagamai on 15-12-3.
 */
@Service
@Transactional
public class GiftCouponMemberServiceImpl implements GiftCouponMemberService {
	private static final DateFormat format = new SimpleDateFormat("yyyyMMddss");
	private final String reason = "礼品兑换";
	@Autowired
	private GiftCouponMemberRepository repository;
	@Autowired
	private GiftCouponService giftCouponService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private SequenceService sequenceService;

	public GiftCouponMember useCoupon(String code, String name) {
		Member member = memberService.findByName(name);
		return useCoupon(code, member);
	}

	@Override
	public GiftCouponMember useCoupon(String code, Member member) {
		GiftCouponMember pcm = repository.findByCodeAndMember(code, member);
		if (pcm == null)
			throw new CouponNotFoundException();
		if (pcm.getUsedTime() != null)
			throw new AlreadyUsedException();
		pcm.setUsedTime(Calendar.getInstance());
		return pcm;
	}

	@Override
	public void exchangeCoupon(GiftCoupon coupon, Member member, int total) {
		int count = total;
		Member m = memberService.findOne(member.getId());
		GiftCoupon sourceCoupon = giftCouponService.findOne(coupon.getId());
		List<GiftCouponMember> giftCouponMember = findByCouponAndMember(sourceCoupon, member);
		if (sourceCoupon == null)
			throw new NotFoundException();
		if (sourceCoupon.isValid() && sourceCoupon.isEffective(Calendar.getInstance())) {
			// 如果不能重复
			if (!sourceCoupon.isDuplicateCoupon()) {
				if (giftCouponMember.size() > 0)
					throw new AlreadyExchangeException();
				count = 1;
			}
			// 判断是否有库存
			if (!sourceCoupon.noStorageLimited()) {
				if (outOfStorage(sourceCoupon, count))
					throw new OutOfStorageException();
			}
			memberService.minusScore(m, sourceCoupon.getScore() * count, reason, ScoreHistoryEnum.COUPONEXCHANGE);
			for (int i = 1; i <= count; i++) {
				save(sourceCoupon, m);
			}
		} else {
			throw new NotValidException();
		}
	}

	private boolean outOfStorage(GiftCoupon coupon, int count) {
		Long total = sumTotalGroupByCoupon(coupon);
		if (total == null)
			total = new Long(0);
		return total.intValue() + count > coupon.getTotal();
	}

	public List<GiftCouponMember> findByCouponAndMember(GiftCoupon coupon, Member member) {
		return repository.findByCouponAndMember(coupon, member);
	}

	@Override
	public Long sumTotalGroupByCoupon(GiftCoupon coupon) {
		return repository.countByCoupon(coupon);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CouponMemberJson> findByMember(Member member, Pageable pageable) {
		Page<GiftCouponMember> lists = repository.findByMember(member, pageable);
		return new PageImpl<>(lists.getContent().stream().map(i -> i.toJson()).collect(Collectors.toList()), pageable,
				lists.getTotalElements());
	}

	@Override
	public Page<CouponMemberJson> findByMember(String mobile, Pageable pageable) {
		Member member = memberService.findByName(mobile);
		Page<GiftCouponMember> lists = repository.findByMemberAndValid(member, ValidEnum.VALID, Calendar.getInstance(),
				pageable);
		return new PageImpl<>(lists.getContent().stream().map(i -> i.toJson()).collect(Collectors.toList()), pageable,
				lists.getTotalElements());
	}

	public GiftCouponMember save(GiftCouponMember pcm) {
		return repository.save(pcm);
	}

	public GiftCouponMember save(GiftCoupon coupon, Member member) {
		Sequence sequence = sequenceService.save(new Sequence());
		GiftCouponMember pcm = new GiftCouponMember(member, coupon);
		pcm.setCode(format.format(Calendar.getInstance().getTime()) + sequence.getId());
		return save(pcm);
	}
}
