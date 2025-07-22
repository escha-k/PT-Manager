package com.project.ptmanager.dto.member;

import com.project.ptmanager.domain.member.Member;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDto {

  private Long id;
  private String username;
  private String name;
  private String phoneNumber;
  private String role;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static MemberDto fromEntity(Member member) {
    return MemberDto.builder()
        .id(member.getId())
        .username(member.getUsername())
        .name(member.getName())
        .phoneNumber(member.getPhoneNumber())
        .role(member.getRole().toString())
        .createdAt(member.getCreatedAt())
        .updatedAt(member.getUpdatedAt())
        .build();
  }
}
