package com.project.ptmanager.controller.trainer;

import com.project.ptmanager.dto.member.MatchingResponseDto;
import com.project.ptmanager.security.CustomUserDetails;
import com.project.ptmanager.service.member.TrainerMemberMatchingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('TRAINER')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/trainer/matching")
public class TrainerMatchingController {

  private final TrainerMemberMatchingService trainerMemberMatchingService;

  @GetMapping("")
  public ResponseEntity<List<MatchingResponseDto>> getMatching(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long trainerId = userDetails.getId();

    List<MatchingResponseDto> list = trainerMemberMatchingService.getByTrainerId(trainerId);

    return ResponseEntity.ok(list);
  }
}
