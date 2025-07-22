package com.project.ptmanager.controller.manager;

import com.project.ptmanager.dto.member.TrainerMemberMatchingDto;
import com.project.ptmanager.service.member.TrainerMemberMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('MANAGER')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/manager/matching")
public class ManagerMatchingController {

  private final TrainerMemberMatchingService trainerMemberMatchingService;

  @PostMapping("")
  public ResponseEntity<Long> createMatching(
      @RequestBody TrainerMemberMatchingDto request
  ) {

    Long matchingId = trainerMemberMatchingService.createMatching(request);

    return ResponseEntity.ok(matchingId);
  }
}
