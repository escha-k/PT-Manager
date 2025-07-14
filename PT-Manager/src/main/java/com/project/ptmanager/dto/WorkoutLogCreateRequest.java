package com.project.ptmanager.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class WorkoutLogCreateRequest {

  LocalDate date;
  String exerciseList;
}
