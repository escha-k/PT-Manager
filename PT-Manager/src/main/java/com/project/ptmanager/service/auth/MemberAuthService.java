package com.project.ptmanager.service.auth;

import com.project.ptmanager.domain.member.Branch;
import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.domain.member.Membership;
import com.project.ptmanager.dto.auth.LoginRequestDto;
import com.project.ptmanager.dto.auth.RegisterRequestDto;
import com.project.ptmanager.dto.auth.RegisterResponseDto;
import com.project.ptmanager.enums.MemberRole;
import com.project.ptmanager.exception.impl.AuthenticationException;
import com.project.ptmanager.exception.impl.MemberNotFoundException;
import com.project.ptmanager.exception.impl.PhoneNumberDuplicatedException;
import com.project.ptmanager.exception.impl.UsernameDuplicatedException;
import com.project.ptmanager.repository.member.BranchRepository;
import com.project.ptmanager.repository.member.MemberRepository;
import com.project.ptmanager.repository.member.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberAuthService {

  private final MemberRepository memberRepository;
  private final BranchRepository branchRepository;
  private final MembershipRepository membershipRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public RegisterResponseDto register(RegisterRequestDto request) {

    String username = request.getUsername();
    if (memberRepository.existsByUsername(username)) {
      throw new UsernameDuplicatedException();
    }

    String phoneNumber = request.getPhoneNumber();
    if (memberRepository.existsByPhoneNumber(phoneNumber)) {
      throw new PhoneNumberDuplicatedException();
    }

    String encodedPassword = passwordEncoder.encode(request.getPassword());
    MemberRole role = MemberRole.valueOf(request.getRole());
    Branch branch = branchRepository.findByName(request.getBranchName())
        .orElse(null); // branch 기능 미구현으로 인해 null 처리

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

  public Member loginAuthenticate(LoginRequestDto request) throws AuthenticationException {
    Member member = memberRepository.findByUsername(request.getUsername())
        .orElseThrow(MemberNotFoundException::new);

    if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
      throw new AuthenticationException();
    }

    return member;
  }
}
