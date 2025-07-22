package com.project.ptmanager.dto.workout;

import com.project.ptmanager.domain.workout.model.Workout;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class WorkoutLogCreateRequestDto {

  private LocalDate date;
  private List<Workout> exerciseList;
}
