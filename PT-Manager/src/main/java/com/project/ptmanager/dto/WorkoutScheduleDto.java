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

  Long scheduleId;
  LocalDate date;
  WorkoutType type;
  String exercisePlan;
  String memo;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;

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
