package com.project.ptmanager.dto;

import com.project.ptmanager.domain.member.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDto {

  private String username;
  private String name;
  private String role;
  private String branchName;

  public static RegisterResponseDto fromEntity(Member member) {
    return RegisterResponseDto.builder()
        .username(member.getUsername())
        .name(member.getName())
        .role(member.getRole().toString())
        .branchName(member.getBranch().getName())
        .build();
  }
}
