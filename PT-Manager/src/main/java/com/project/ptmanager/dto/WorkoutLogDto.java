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

  Long logId;
  LocalDate date;
  WorkoutType type;
  String exerciseList;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;

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
