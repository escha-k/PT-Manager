package com.project.ptmanager.service;

import com.project.ptmanager.domain.member.Branch;
import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.domain.member.Membership;
import com.project.ptmanager.dto.LoginRequest;
import com.project.ptmanager.dto.RegisterRequest;
import com.project.ptmanager.dto.RegisterResponseDto;
import com.project.ptmanager.enums.MemberRole;
import com.project.ptmanager.exception.AuthenticationException;
import com.project.ptmanager.exception.BranchNotFoundException;
import com.project.ptmanager.exception.MemberNotFoundException;
import com.project.ptmanager.exception.PhoneNumberDuplicatedException;
import com.project.ptmanager.exception.UsernameDuplicatedException;
import com.project.ptmanager.repository.member.BranchRepository;
import com.project.ptmanager.repository.member.MemberRepository;
import com.project.ptmanager.repository.member.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final BranchRepository branchRepository;
  private final MembershipRepository membershipRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public RegisterResponseDto register(RegisterRequest request) {

    String username = request.getUsername();
    if (memberRepository.existsByUsername(username)) {
      throw new UsernameDuplicatedException("중복된 ID 입니다.");
    }

    String phoneNumber = request.getPhoneNumber();
    if (memberRepository.existsByPhoneNumber(phoneNumber)) {
      throw new PhoneNumberDuplicatedException("이미 가입된 전화번호입니다.");
    }

    String encodedPassword = passwordEncoder.encode(request.getPassword());
    MemberRole role = MemberRole.valueOf(request.getRole());
    Branch branch = branchRepository.findByName(request.getBranchName())
        .orElseThrow(() -> new BranchNotFoundException("지점 정보를 찾을 수 없습니다."));

    Member member = Member.builder()
        .username(username)
        .password(encodedPassword)
        .name(request.getName())
        .phoneNumber(phoneNumber)
        .role(role)
        .deviceToken(null) // TODO: FCM 푸시 기능 구현
        .branch(branch)
        .build();

    Member saved = memberRepository.save(member);

    Membership membership = Membership.builder()
        .member(saved)
        .ptRemaining(0)
        .build();

    membershipRepository.save(membership);

    return RegisterResponseDto.fromEntity(saved);
  }

  public Member loginAuthenticate(LoginRequest request) {
    Member member = memberRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new MemberNotFoundException("회원 정보를 찾을 수 없습니다."));

    if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
      throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
    }

    return member;
  }
}
