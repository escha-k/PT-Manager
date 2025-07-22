package com.project.ptmanager.service.member;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.dto.member.MemberDto;
import com.project.ptmanager.enums.MemberRole;
import com.project.ptmanager.exception.impl.MemberNotFoundException;
import com.project.ptmanager.repository.member.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerBranchService {

  private final MemberRepository memberRepository;

  public List<MemberDto> getMemberList(Long managerId) {
    Member manager = memberRepository.findById(managerId).orElseThrow(MemberNotFoundException::new);

    List<Member> list = memberRepository.findAllByRoleAndBranchName(
        MemberRole.MEMBER, manager.getBranch().getName());

    if (list.isEmpty()) {
      throw new MemberNotFoundException();
    }

    return list.stream().map(MemberDto::fromEntity).toList();
  }

  public List<MemberDto> getTrainerList(Long managerId) {
    Member manager = memberRepository.findById(managerId).orElseThrow(MemberNotFoundException::new);

    List<Member> list = memberRepository.findAllByRoleAndBranchName(
        MemberRole.TRAINER, manager.getBranch().getName());

    if (list.isEmpty()) {
      throw new MemberNotFoundException();
    }

    return list.stream().map(MemberDto::fromEntity).toList();
  }
}
