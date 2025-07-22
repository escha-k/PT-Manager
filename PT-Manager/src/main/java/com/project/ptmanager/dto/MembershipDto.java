package com.project.ptmanager.dto;

import com.project.ptmanager.domain.member.Membership;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MembershipDto {

  private LocalDate startedAt;
  private LocalDate expiredAt;
  private Integer ptRemaining;

  public static MembershipDto fromEntity(Membership membership) {
    return MembershipDto.builder()
        .startedAt(membership.getStartedAt())
        .expiredAt(membership.getExpiredAt())
        .ptRemaining(membership.getPtRemaining())
        .build();
  }
}
