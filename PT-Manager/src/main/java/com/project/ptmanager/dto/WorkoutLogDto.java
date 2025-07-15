package com.project.ptmanager.dto;

import com.project.ptmanager.domain.workout.WorkoutLog;
import com.project.ptmanager.enums.WorkoutType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkoutLogDto {

  private Long logId;
  private LocalDate date;
  private WorkoutType type;
  private String exerciseList;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static WorkoutLogDto fromEntity(WorkoutLog workoutLog) {
    return WorkoutLogDto.builder()
        .logId(workoutLog.getId())
        .date(workoutLog.getDate())
        .type(workoutLog.getType())
        .exerciseList(workoutLog.getExerciseList())
        .createdAt(workoutLog.getCreatedAt())
        .updatedAt(workoutLog.getUpdatedAt())
        .build();
  }
}
