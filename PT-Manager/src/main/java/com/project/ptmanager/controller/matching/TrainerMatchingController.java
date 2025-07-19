package com.project.ptmanager.controller.matching;

import com.project.ptmanager.dto.MatchingResponseDto;
import com.project.ptmanager.service.TrainerMemberMatchingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainer")
public class TrainerMatchingController {

  private final TrainerMemberMatchingService trainerMemberMatchingService;

  @GetMapping("/matched-member")
  public ResponseEntity<List<MatchingResponseDto>> getMatching() {

    Long trainerId = null; // TODO: 인증에서 트레이너 정보 가져오기

    List<MatchingResponseDto> list = trainerMemberMatchingService.getByTrainerId(trainerId);

    return ResponseEntity.ok(list);
  }
}
