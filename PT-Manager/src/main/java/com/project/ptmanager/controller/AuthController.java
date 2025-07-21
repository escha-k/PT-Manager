package com.project.ptmanager.controller;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.dto.LoginRequest;
import com.project.ptmanager.dto.RegisterRequest;
import com.project.ptmanager.dto.RegisterResponseDto;
import com.project.ptmanager.security.CustomUserDetailsService;
import com.project.ptmanager.security.JwtTokenProvider;
import com.project.ptmanager.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final MemberService memberService;
  private final CustomUserDetailsService customUserDetailsService;
  private final JwtTokenProvider tokenProvider;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponseDto> register(
      @RequestBody RegisterRequest request
  ) {

    RegisterResponseDto responseDto = memberService.register(request);

    return ResponseEntity.ok(responseDto);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(
      @RequestBody LoginRequest request
  ) {
    Member member = memberService.loginAuthenticate(request);
    String token = tokenProvider.generateToken(member.getUsername(), member.getRole().toString());

    return ResponseEntity.ok(token);
  }
}
