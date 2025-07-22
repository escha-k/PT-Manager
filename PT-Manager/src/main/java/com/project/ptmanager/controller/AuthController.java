package com.project.ptmanager.controller;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.dto.auth.LoginRequestDto;
import com.project.ptmanager.dto.auth.RegisterRequestDto;
import com.project.ptmanager.dto.auth.RegisterResponseDto;
import com.project.ptmanager.security.JwtTokenProvider;
import com.project.ptmanager.service.auth.MemberAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final MemberAuthService memberAuthService;
  private final JwtTokenProvider tokenProvider;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponseDto> register(
      @RequestBody RegisterRequestDto request
  ) {

    RegisterResponseDto responseDto = memberAuthService.register(request);

    return ResponseEntity.ok(responseDto);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(
      @RequestBody LoginRequestDto request
  ) {
    Member member = memberAuthService.loginAuthenticate(request);
    String token = tokenProvider.generateToken(member.getUsername(), member.getRole().toString());

    return ResponseEntity.ok(token);
  }
}
