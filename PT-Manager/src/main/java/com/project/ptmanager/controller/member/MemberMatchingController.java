package com.project.ptmanager.controller.member;

import com.project.ptmanager.dto.MatchingResponseDto;
import com.project.ptmanager.security.CustomUserDetails;
import com.project.ptmanager.service.TrainerMemberMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('MEMBER')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member/matching")
public class MemberMatchingController {

  private final TrainerMemberMatchingService trainerMemberMatchingService;

  @GetMapping("")
  public ResponseEntity<MatchingResponseDto> getMatching(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long memberId = userDetails.getId();

    MatchingResponseDto matchingResponseDto = trainerMemberMatchingService.getByMemberId(memberId);

    return ResponseEntity.ok(matchingResponseDto);
  }
}
