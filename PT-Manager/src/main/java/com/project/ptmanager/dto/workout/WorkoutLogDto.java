package com.project.ptmanager.dto.workout;

import com.project.ptmanager.domain.workout.WorkoutLog;
import com.project.ptmanager.domain.workout.model.Workout;
import com.project.ptmanager.enums.WorkoutType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkoutLogDto {

  private Long logId;
  private LocalDate date;
  private WorkoutType type;
  private List<Workout> exerciseList;
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
