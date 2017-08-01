package com.by.service.impl;

import com.by.json.ParkingCouponMemberJson;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponMember;
import com.by.repository.ParkingCouponMemberRepository;
import com.by.service.ParkingCouponMemberService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParkingCouponMemberServiceImpl implements ParkingCouponMemberService {
    @Autowired
    private ParkingCouponMemberRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<ParkingCouponMember> findByMember(Member member, Pageable pageable) {
        return repository.findByMember(member, pageable);
    }

    @Override
    public ParkingCouponMember save(ParkingCouponMember pcm) {
        return repository.save(pcm);
    }

    @Override
    public ParkingCouponMember save(Member member, ParkingCoupon pc, int total) {
        ParkingCouponMember parkingCouponMember = repository.findByMemberAndCoupon(member, pc);
        if (parkingCouponMember == null) {
            ParkingCouponMember pcm = new ParkingCouponMember();
            pcm.setCoupon(pc);
            pcm.setMember(member);
            pcm.setTotal(total);
            return save(pcm);
        } else {
            parkingCouponMember.setTotal(parkingCouponMember.getTotal() + total);
            return parkingCouponMember;
        }
    }

    @Override
    public Page<ParkingCouponMemberJson> toJson(Page<ParkingCouponMember> page, Pageable pageable) {
        List<ParkingCouponMemberJson> json = page.getContent().stream().map(i -> new ParkingCouponMemberJson(i))
                .collect(Collectors.toList());
        return new PageImpl<>(json, pageable, page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public ParkingCouponMember findByMemberAndCoupon(Member member, ParkingCoupon coupon) {
        return repository.findByMemberAndCoupon(member, coupon);
    }

    @Override
    public List<ParkingCouponMember> findByMember(Member member, Sort sort) {
        Calendar today = Calendar.getInstance();
        return repository.findByMember(member, sort).stream().filter(i -> {
            if (i.getCoupon().getValid().equals(ValidEnum.INVALID)) {
                return false;
            }
            if (i.getCoupon().getCouponEndTime() != null) {
                if (i.getCoupon().getCouponEndTime().after(today))
                    return true;
            } else {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ParkingCouponMember> findByMember(Member member) {
        Calendar today = Calendar.getInstance();
        return repository.findByMember(member).stream().filter(i -> {
            if (i.getCoupon().isValid() && i.getCoupon().isBeforeCouponEndTime(today))
                return true;
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public void delete(ParkingCouponMember pcm) {
        repository.delete(pcm);
    }

    @Override
    public void delete(List<ParkingCouponMember> pcm) {
        repository.delete(pcm);
    }

    @Override
    public Long sumTotalByMember(Member member) {
        return null;
    }
}
