package com.project.ptmanager.dto.statistics;

import com.project.ptmanager.domain.statistics.Statistics;
import com.project.ptmanager.enums.StatisticsType;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticsDto {

  private StatisticsType type;
  private LocalDate startDate;
  private double attendance;
  private double totalWeight;

  public static StatisticsDto fromEntity(Statistics statistics) {
    return StatisticsDto.builder()
        .type(statistics.getType())
        .startDate(statistics.getStartDate())
        .attendance(statistics.getAttendance())
        .totalWeight(statistics.getTotalWeight())
        .build();
  }
} 