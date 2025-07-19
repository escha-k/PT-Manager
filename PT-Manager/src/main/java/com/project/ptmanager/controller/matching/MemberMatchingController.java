package com.project.ptmanager.controller.matching;

import com.project.ptmanager.dto.MatchingResponseDto;
import com.project.ptmanager.service.TrainerMemberMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberMatchingController {

  private final TrainerMemberMatchingService trainerMemberMatchingService;

  @GetMapping("/matched-trainer")
  public ResponseEntity<MatchingResponseDto> getMatching() {

    Long memberId = null; // TODO: 인증에서 회원 정보 가져오기

    MatchingResponseDto matchingResponseDto = trainerMemberMatchingService.getByMemberId(memberId);

    return ResponseEntity.ok(matchingResponseDto);
  }
}
