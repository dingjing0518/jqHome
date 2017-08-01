package com.by.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.json.MemberDetailJson;
import com.by.model.Member;
import com.by.model.MemberDetail;
import com.by.repository.MemberDetailRepository;
import com.by.service.MemberDetailService;
import com.by.service.MemberService;

@Service
@Transactional
public class MemberDetailServiceImpl implements MemberDetailService {
    @Autowired
    private MemberDetailRepository repository;
    @Autowired
    private MemberService memberService;
    @PersistenceContext
    private EntityManager em;

    @Override
    @CachePut(value = "member", key = "#memberId")
    public Member update(Long memberId, MemberDetailJson detail) {
        Member member = memberService.findOne(memberId);
        MemberDetail d = member.getMemberDetail();
        d.setAddress(detail.getAddress());
        d.setBirthday(detail.getBirthday());
        d.setRealName(detail.getRealName());
        return member;
    }

    @Override
    @CachePut(value = "member", key = "#memberId")
    public Member updatePassword(Long memberId, MemberDetailJson detail) {
        Member member = memberService.findOne(memberId);
        MemberDetail d = member.getMemberDetail();
        d.setPassword(detail.getPassword());
        return member;
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDetail findByMember(Member m) {
        return repository.findByMember(m);
    }

    @Override
    public MemberDetail save(MemberDetail detail) {
        return repository.save(detail);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDetail findAuditByRevision(Long id, int revision) {
        AuditReader auditReader = AuditReaderFactory.get(em);
        return auditReader.find(MemberDetail.class, id, revision);
    }

    public List<MemberDetail> findAllAuditRevision(Long id) {
        AuditReader auditReader = AuditReaderFactory.get(em);
        return auditReader.createQuery().forRevisionsOfEntity(MemberDetail.class, true, true)
                .add(AuditEntity.id().eq(id)).addOrder(AuditEntity.revisionNumber().asc()).getResultList();
    }
}
