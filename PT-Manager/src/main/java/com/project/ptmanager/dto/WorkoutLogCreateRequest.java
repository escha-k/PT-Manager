package com.project.ptmanager.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class WorkoutLogCreateRequest {

  private LocalDate date;
  private String exerciseList;
}
