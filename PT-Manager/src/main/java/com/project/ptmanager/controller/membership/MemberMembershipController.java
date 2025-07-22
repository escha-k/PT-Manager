package com.project.ptmanager.controller.membership;

import com.project.ptmanager.dto.MembershipDto;
import com.project.ptmanager.dto.PtHistoryDto;
import com.project.ptmanager.security.CustomUserDetails;
import com.project.ptmanager.service.MemberMembershipService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('MEMBER')")
@RestController
@RequestMapping("/member/membership")
@RequiredArgsConstructor
public class MemberMembershipController {

  private final MemberMembershipService memberMembershipService;

  @GetMapping("")
  public ResponseEntity<MembershipDto> getMembershipInfo(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long memberId = userDetails.getId();

    MembershipDto dto = memberMembershipService.getMembershipInfo(memberId);

    return ResponseEntity.ok(dto);
  }
  
  @GetMapping("/pt-history")
  public ResponseEntity<List<PtHistoryDto>> getPtHistory(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long memberId = userDetails.getId();

    List<PtHistoryDto> list = memberMembershipService.getHistory(memberId);

    return ResponseEntity.ok(list);
  }
} 