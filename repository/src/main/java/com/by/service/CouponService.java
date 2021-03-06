package com.by.service;

import com.by.form.BaseCouponForm;
import com.by.json.CouponJson;
import com.by.model.Coupon;
import com.by.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public interface CouponService {
    Page<CouponJson> findAll(Pageable pageable);

    Page<CouponJson> findByMember(Member member, Pageable pageable);

    Page<CouponJson> findByNameLike(String name, Pageable pageable);

    <T extends Coupon> List<Predicate> getPredicateList(BaseCouponForm form, Root<T> root, CriteriaBuilder cb);

    Coupon valid(int id);
}
