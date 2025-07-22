package com.project.ptmanager.service;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.domain.member.Membership;
import com.project.ptmanager.domain.member.PtHistory;
import com.project.ptmanager.dto.member.MembershipDurationDto;
import com.project.ptmanager.enums.PtChangeType;
import com.project.ptmanager.exception.impl.MemberNotFoundException;
import com.project.ptmanager.repository.member.MemberRepository;
import com.project.ptmanager.repository.member.MembershipRepository;
import com.project.ptmanager.repository.member.PtHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerMembershipService {

  private final MemberRepository memberRepository;
  private final MembershipRepository membershipRepository;
  private final PtHistoryRepository ptHistoryRepository;

  @Transactional
  public void setMembershipDuration(Long memberId, MembershipDurationDto request) {

    Membership membership = membershipRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);

    membership.setStartedAt(request.getStartDate());
    membership.setExpiredAt(request.getEndDate());

    membershipRepository.save(membership);
  }

  @Transactional
  public void addPtRemaining(Long memberId, int amount) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);

    Membership membership = membershipRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);

    int currentRemaining = membership.getPtRemaining();
    membership.setPtRemaining(currentRemaining + amount);

    membershipRepository.save(membership);

    PtHistory history = PtHistory.builder()
        .member(member)
        .changeType(PtChangeType.ADD)
        .amount(amount)
        .build();

    ptHistoryRepository.save(history);
  }
}
