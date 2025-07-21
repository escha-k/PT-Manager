package com.project.ptmanager.controller.matching;

import com.project.ptmanager.dto.MatchingResponseDto;
import com.project.ptmanager.service.TrainerMemberMatchingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import com.project.ptmanager.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@PreAuthorize("hasRole('TRAINER')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/trainer")
public class TrainerMatchingController {

  private final TrainerMemberMatchingService trainerMemberMatchingService;

  @GetMapping("/matched-member")
  public ResponseEntity<List<MatchingResponseDto>> getMatching(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long trainerId = userDetails.getId();

    List<MatchingResponseDto> list = trainerMemberMatchingService.getByTrainerId(trainerId);

    return ResponseEntity.ok(list);
  }
}
