package com.project.ptmanager.repository.member;

import com.project.ptmanager.domain.member.PtHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PtHistoryRepository extends JpaRepository<PtHistory, Long> {

  List<PtHistory> findAllByMemberId(Long memberId);
}
