package com.by.service.impl;

import com.by.json.LicenseJson;
import com.by.model.License;
import com.by.model.Member;
import com.by.repository.LicenseRepository;
import com.by.service.LicenseService;
import com.by.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LicenseServiceImpl implements LicenseService {
    @Autowired
    private LicenseRepository repository;
    @Autowired
    private MemberService memberService;

    @Override
    @CachePut(value = "license", key = "#member.id")
    public List<LicenseJson> save(Member member, String licenseName) {
        License license = repository.findByName(licenseName);
        Member m = memberService.findOne(member.getId());
        License l = new License();
        l.setName(licenseName);
        List<Member> members = new ArrayList<>();
        members.add(member);
        l.setMembers(members);
        if (!m.getLicenses().contains(license))
            m.getLicenses().add(l);
        return findByMember(member);
    }

    @Override
    @Transactional(readOnly = true)
    public License findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "license", key = "#member.id")
    public List<LicenseJson> findByMember(Member member) {
        return memberService.findOne(member.getId()).getLicenses().stream().map(i -> {
            LicenseJson license = new LicenseJson();
            license.setId(i.getId());
            license.setLicenseName(i.getName());
            return license;
        }).collect(Collectors.toList());
    }
}
