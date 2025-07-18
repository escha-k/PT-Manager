package com.project.ptmanager.dto;

import com.project.ptmanager.domain.workout.WorkoutSchedule;
import com.project.ptmanager.enums.WorkoutType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkoutScheduleDto {

  private Long scheduleId;
  private LocalDate date;
  private WorkoutType type;
  private String exercisePlan;
  private String memo;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static WorkoutScheduleDto fromEntity(WorkoutSchedule workoutSchedule) {
    return WorkoutScheduleDto.builder()
        .scheduleId(workoutSchedule.getId())
        .date(workoutSchedule.getDate())
        .type(workoutSchedule.getType())
        .exercisePlan(workoutSchedule.getExercisePlan())
        .memo(workoutSchedule.getMemo())
        .createdAt(workoutSchedule.getCreatedAt())
        .updatedAt(workoutSchedule.getUpdatedAt())
        .build();
  }
}
