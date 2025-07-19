package com.project.ptmanager.controller.matching;

import com.project.ptmanager.dto.TrainerMemberMatchingDto;
import com.project.ptmanager.service.TrainerMemberMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerMatchingController {

  private final TrainerMemberMatchingService trainerMemberMatchingService;

  @PostMapping("/matching")
  public ResponseEntity<Long> createMatching(
      @RequestBody TrainerMemberMatchingDto trainerMemberMatchingDto
  ) {

    Long matchingId = trainerMemberMatchingService.createMatching(trainerMemberMatchingDto);

    return ResponseEntity.ok(matchingId);
  }
}
