package com.project.ptmanager.service;

import com.project.ptmanager.domain.member.Membership;
import com.project.ptmanager.domain.member.PtHistory;
import com.project.ptmanager.dto.member.MembershipDto;
import com.project.ptmanager.dto.member.PtHistoryDto;
import com.project.ptmanager.exception.impl.PtHistoryNotFoundException;
import com.project.ptmanager.repository.member.MembershipRepository;
import com.project.ptmanager.repository.member.PtHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberMembershipService {

  private final MembershipRepository membershipRepository;
  private final PtHistoryRepository ptHistoryRepository;

  public MembershipDto getMembershipInfo(Long memberId) {
    Membership membership = membershipRepository.findById(memberId).get();// null 검사 생략

    return MembershipDto.fromEntity(membership);
  }

  public List<PtHistoryDto> getHistory(Long memberId) {

    List<PtHistory> list = ptHistoryRepository.findAllByMemberId(memberId);

    if (list.isEmpty()) {
      throw new PtHistoryNotFoundException();
    }

    return list.stream().map(PtHistoryDto::fromEntity).toList();
  }


}
