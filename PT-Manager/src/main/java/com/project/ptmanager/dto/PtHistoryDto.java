package com.project.ptmanager.dto;

import com.project.ptmanager.domain.member.PtHistory;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PtHistoryDto {

  private Long id;
  private String changeType;
  private Integer amount;
  private LocalDateTime changedAt;

  public static PtHistoryDto fromEntity(PtHistory ptHistory) {
    return PtHistoryDto.builder()
        .id(ptHistory.getId())
        .changeType(ptHistory.getChangeType().toString())
        .amount(ptHistory.getAmount())
        .changedAt(ptHistory.getChangedAt())
        .build();
  }
}
