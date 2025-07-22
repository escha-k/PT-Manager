package com.project.ptmanager.controller.manager;

import com.project.ptmanager.dto.member.MemberDto;
import com.project.ptmanager.security.CustomUserDetails;
import com.project.ptmanager.service.member.ManagerBranchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager/branch")
@PreAuthorize("hasRole('MANAGER')")
public class ManagerBranchController {

  private final ManagerBranchService managerBranchService;

  @GetMapping("/members")
  public ResponseEntity<List<MemberDto>> getMembers(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long managerId = userDetails.getId();

    List<MemberDto> list = managerBranchService.getMemberList(managerId);

    return ResponseEntity.ok(list);
  }

  public ResponseEntity<List<MemberDto>> getTrainers(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    Long managerId = userDetails.getId();

    List<MemberDto> list = managerBranchService.getTrainerList(managerId);

    return ResponseEntity.ok(list);
  }
}
