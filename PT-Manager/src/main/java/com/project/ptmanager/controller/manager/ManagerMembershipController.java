package com.project.ptmanager.controller.manager;

import com.project.ptmanager.dto.member.MembershipDurationDto;
import com.project.ptmanager.service.ManagerMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('MANAGER')")
@RestController
@RequestMapping("/manager/membership/{memberId}")
@RequiredArgsConstructor
public class ManagerMembershipController {

  private final ManagerMembershipService managerMembershipService;

  @PostMapping("/duration")
  public ResponseEntity<Void> setMembershipDuration(
      @PathVariable Long memberId,
      @RequestBody MembershipDurationDto request
  ) {

    // 매니저-회원 지점 확인 로직 생략

    managerMembershipService.setMembershipDuration(memberId, request);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/pt")
  public ResponseEntity<Void> addPtRemaining(
      @PathVariable Long memberId,
      @RequestParam int amount
  ) {

    // 매니저-회원 지점 확인 로직 생략

    managerMembershipService.addPtRemaining(memberId, amount);

    return ResponseEntity.ok().build();
  }
} 