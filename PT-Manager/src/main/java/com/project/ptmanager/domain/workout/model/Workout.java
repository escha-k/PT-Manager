package com.project.ptmanager.domain.workout.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workout {

  private String name;    // 운동 이름
  private int sets;       // 세트 수
  private int replays;    // 세트당 반복 횟수
  private double weight;  // 중량
}
