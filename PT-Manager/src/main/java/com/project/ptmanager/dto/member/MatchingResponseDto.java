package com.project.ptmanager.dto.member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchingResponseDto {

  private Long id;
  private String name;
}
