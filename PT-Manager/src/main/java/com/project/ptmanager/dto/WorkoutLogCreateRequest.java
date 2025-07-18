package com.project.ptmanager.dto;

import com.project.ptmanager.domain.workout.model.Workout;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class WorkoutLogCreateRequest {

  private LocalDate date;
  private List<Workout> exerciseList;
}
