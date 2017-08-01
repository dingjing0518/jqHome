package com.by.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.exception.NotEnoughScoreException;
import com.by.exception.NotFoundException;
import com.by.exception.NotValidException;
import com.by.exception.OutOfStorageException;
import com.by.form.BaseCouponForm;
import com.by.json.CouponTemplateJson;
import com.by.json.ParkingCouponHistoryJson;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponExchangeHistory;
import com.by.model.ParkingCouponMember;
import com.by.model.ParkingCouponUseHistory;
import com.by.repository.ParkingCouponRepository;
import com.by.service.CouponService;
import com.by.service.LicenseService;
import com.by.service.MemberService;
import com.by.service.ParkingCouponExchangeHistoryService;
import com.by.service.ParkingCouponMemberService;
import com.by.service.ParkingCouponService;
import com.by.service.ParkingCouponUseHistoryService;
import com.by.typeEnum.ScoreHistoryEnum;
import com.by.typeEnum.ValidEnum;
import com.google.common.collect.Lists;

@Service
@Transactional
public class ParkingCouponServiceImpl implements ParkingCouponService {
	@Autowired
	private ParkingCouponRepository repository;
	@Autowired
	private CouponService couponService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private LicenseService licenseService;
	@Autowired
	private ParkingCouponUseHistoryService useHistoryService;
	@Autowired
	private ParkingCouponExchangeHistoryService exchangeHistoryService;
	@Autowired
	private ParkingCouponMemberService parkingCouponMemberService;
	@Autowired
	private EntityManager em;
	@Value(value = "${coupon.amount}")
	private double amount;
	@Value(value = "${parkingCoupon.reason}")
	private String reason;

	@Override
	public ParkingCoupon save(ParkingCoupon coupon) {
		coupon.setValid(ValidEnum.VALID);
		coupon.setAmount(amount);
		return repository.save(coupon);
	}

	@CachePut(value = "coupon", key = "#coupon.id")
	public ParkingCoupon cachePut(ParkingCoupon coupon) {
		return coupon;
	}

	@Override
	@CachePut(value = "coupon", key = "#coupon.id")
	public Optional<ParkingCoupon> update(ParkingCoupon coupon) {
		return repository.findById(coupon.getId()).map(i -> {
			i.setAmount(coupon.getAmount());
			i.setName(coupon.getName());
			i.setScore(coupon.getScore());
			i.setSummary(coupon.getSummary());
			i.setContentImg(coupon.getContentImg());
			i.setCoverImg(coupon.getCoverImg());
			i.setBeginTime(coupon.getBeginTime());
			i.setEndTime(coupon.getEndTime());
			i.setCouponEndTime(coupon.getCouponEndTime());
			return i;
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<ParkingCoupon> findAll() {
		return Lists.newArrayList(repository.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ParkingCoupon> findById(int id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public ParkingCoupon findOne(int id) {
		return repository.findOne(id);
	}

	@Override
	@Cacheable("coupon")
	public ParkingCoupon findOneCache(int id) {
		return repository.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ParkingCoupon> findByValid(ValidEnum valid, Pageable pageable) {
		return repository.findByValid(valid, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ParkingCoupon> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ParkingCoupon> findFirstPage(int size) {
		return repository.findAll(new PageRequest(0, size, Sort.Direction.DESC, "createdTime"));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CouponTemplateJson> findAll(BaseCouponForm form, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ParkingCoupon> c = cb.createQuery(ParkingCoupon.class);
		Root<ParkingCoupon> root = c.from(ParkingCoupon.class);
		c.select(root);
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(ParkingCoupon.class)));
		Predicate[] predicates = couponService.getPredicateList(form, root, cb).toArray(new Predicate[0]);
		c.where(predicates);
		c.orderBy(cb.desc(root.get("beginTime")));
		cq.where(predicates);

		List<ParkingCoupon> lists = em.createQuery(c)
				.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize()).getResultList();
		Long count = em.createQuery(cq).getSingleResult();
		List<CouponTemplateJson> results = lists.stream().map(i -> new CouponTemplateJson(i))
				.collect(Collectors.toList());
		return new PageImpl<>(results, pageable, count);
	}

	@Override
	@CachePut(value = "coupon", key = "#coupon.id")
	public ParkingCoupon validOrInValid(ParkingCoupon coupon) {
		ParkingCoupon c = repository.findOne(coupon.getId());
		if (c.getValid().equals(ValidEnum.VALID)) {
			Calendar today = Calendar.getInstance();
			if (c.getBeginTime().after(today) && c.getEndTime().after(today))
				c.setValid(ValidEnum.VALID);
		} else
			c.setValid(ValidEnum.VALID);
		return c;
	}

	@Override
	@Transactional
	public void useCoupon(Member member, int total, String license) {
		List<ParkingCouponMember> pcm = parkingCouponMemberService.findByMember(member);
		// 找出哪些需要被删除
		deleteResult(total, pcm);
		licenseService.save(member, license);
		useHistoryService.save(member, total, license);
	}

	@Override
	@Transactional
	public void deleteResult(int total, List<ParkingCouponMember> pcm) {
		int sum = Member.findSumParkingCouponCount(pcm);
		List<ParkingCouponMember> results = ParkingCouponMember.findWhichToDelete(total, pcm);
		if (total > 0) {
			if (sum < total) {
				throw new OutOfStorageException();
			} else if (sum > total) {
				if (results.size() == 1) {
					ParkingCouponMember p = results.get(0);
					p.setTotal(p.getTotal() - total);
					em.merge(p);
				} else {
					ParkingCouponMember last = results.get(results.size() - 1);
					last.setTotal(sum - total);
					em.merge(last);
					results.remove(last);
					parkingCouponMemberService.delete(results);
				}
			} else if (sum == total) {
				parkingCouponMemberService.delete(results);
			}
		}
	}

	@Override
	@Cacheable(value = "coupon", key = "T(com.by.utils.CalendarUtil).getToday()")
	public ParkingCoupon findActivate() {
		List<ParkingCoupon> lists = repository.findActivate(ValidEnum.VALID, Calendar.getInstance());
		return ParkingCoupon.minScoreCoupon(lists);
	}

	@Override
	@CacheEvict(value = "coupon", key = "T(com.by.utils.CalendarUtil).getToday()")
	public ParkingCoupon refresh() {
		List<ParkingCoupon> lists = repository.findActivate(ValidEnum.VALID, Calendar.getInstance());
		return ParkingCoupon.minScoreCoupon(lists);
	}

	@Override
	public void exchangeCoupon(Member member, ParkingCoupon coupon, int total) {
		Member m = em.find(Member.class, member.getId());
		ParkingCoupon sourceCoupon = em.find(ParkingCoupon.class, coupon.getId());

		if (sourceCoupon == null)
			throw new NotFoundException();
		if (m.getScore() < total * sourceCoupon.getScore())
			throw new NotEnoughScoreException();
		if (sourceCoupon.isValid() && sourceCoupon.isEffective(Calendar.getInstance())) {
			memberService.minusScore(m, sourceCoupon.getScore() * total, reason, ScoreHistoryEnum.COUPONEXCHANGE);
			exchangeHistoryService.save(m, sourceCoupon, total);
			parkingCouponMemberService.save(m, sourceCoupon, total);
		} else {
			throw new NotValidException();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ParkingCouponHistoryJson> findByMemberHistory(Member member, Pageable pageable) {
		Page<ParkingCouponUseHistory> useHistory = useHistoryService.findByMember(member, pageable);
		Page<ParkingCouponExchangeHistory> exchangeHistory = exchangeHistoryService.findByMember(member, pageable);
		List<ParkingCouponHistoryJson> results = new ArrayList<>();
		List<ParkingCouponHistoryJson> useJson = useHistory.getContent().stream().map(i -> {
			ParkingCouponHistoryJson json = new ParkingCouponHistoryJson(i);
			json.setTotal(-i.getTotal());
			return json;
		}).collect(Collectors.toList());
		List<ParkingCouponHistoryJson> exchangeJson = exchangeHistory.getContent().stream()
				.map(i -> new ParkingCouponHistoryJson(i)).collect(Collectors.toList());
		results.addAll(useJson);
		results.addAll(exchangeJson);
		results.sort((o1, o2) -> {
			if (o1.getCreatedTime().before(o2.getCreatedTime()))
				return 1;
			return -1;
		});
		Long max = Math.max(useHistory.getTotalElements(), exchangeHistory.getTotalElements());
		return new PageImpl<>(results.stream().limit(pageable.getPageSize()).collect(Collectors.toList()), pageable,
				max);
	}

	@Override
	@Transactional(readOnly = true)
	public Long countByName(String name) {
		return repository.countByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public ParkingCoupon findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public void delete(ParkingCoupon coupon) {
		if (coupon != null)
			repository.delete(coupon);
	}
}
