package com.project.ptmanager.service;

import com.project.ptmanager.domain.workout.WorkoutSchedule;
import com.project.ptmanager.dto.WorkoutScheduleDto;
import com.project.ptmanager.repository.workout.WorkoutScheduleRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutScheduleService {

  private final WorkoutScheduleRepository workoutScheduleRepository;

  public List<WorkoutScheduleDto> getScheduleList(Long memberId, int year, int month) {
    LocalDate start = LocalDate.of(year, month, 1);
    LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

    List<WorkoutSchedule> list = workoutScheduleRepository.findAllByMemberIdAndDateBetween(
        memberId, start, end);

    return list.stream().map(WorkoutScheduleDto::fromEntity).toList();
  }

  public WorkoutScheduleDto getSchedule(Long memberId, LocalDate date) {

    WorkoutSchedule workoutSchedule = workoutScheduleRepository.findByMemberIdAndDate(memberId,
        date).orElseThrow();

    return WorkoutScheduleDto.fromEntity(workoutSchedule);
  }
}
