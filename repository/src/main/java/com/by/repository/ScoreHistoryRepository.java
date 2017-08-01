package com.by.repository;

import com.by.model.Member;
import com.by.model.ScoreHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ScoreHistoryRepository extends PagingAndSortingRepository<ScoreHistory, Long> {
    Page<ScoreHistory> findByMember(Member member, Pageable pageable);

    List<ScoreHistory> findByMember(Member member);

    void deleteByMember(Member member);
}
