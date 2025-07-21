package com.project.ptmanager.dto;

import com.project.ptmanager.domain.workout.model.Workout;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class WorkoutScheduleCreateRequest {

  private LocalDate date;
  private String type;
  private List<Workout> exercisePlan;
  private String memo;
}
