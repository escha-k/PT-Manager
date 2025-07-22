package com.project.ptmanager.dto;

import com.project.ptmanager.domain.workout.WorkoutFeedback;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkoutFeedbackResponseDto {

  private Long id;
  private Long logId;
  private String content;
  private LocalDateTime createdAt;

  public static WorkoutFeedbackResponseDto fromEntity(WorkoutFeedback workoutFeedback) {
    return WorkoutFeedbackResponseDto.builder()
        .id(workoutFeedback.getId())
        .content(workoutFeedback.getContent())
        .createdAt(workoutFeedback.getCreatedAt())
        .logId(workoutFeedback.getLog().getId())
        .build();
  }

}
