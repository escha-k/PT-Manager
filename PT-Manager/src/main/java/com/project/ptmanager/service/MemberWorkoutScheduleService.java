package com.project.ptmanager.service;

import static com.project.ptmanager.utils.DateUtils.endOfMonth;
import static com.project.ptmanager.utils.DateUtils.startOfMonth;

import com.project.ptmanager.domain.workout.WorkoutSchedule;
import com.project.ptmanager.dto.workout.WorkoutScheduleDto;
import com.project.ptmanager.exception.impl.AuthenticationException;
import com.project.ptmanager.exception.impl.WorkoutScheduleNotFoundException;
import com.project.ptmanager.repository.workout.WorkoutScheduleRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWorkoutScheduleService {

  private final WorkoutScheduleRepository workoutScheduleRepository;

  public List<WorkoutScheduleDto> getScheduleList(Long memberId, int year, int month) {
    LocalDate start = startOfMonth(year, month);
    LocalDate end = endOfMonth(year, month);

    List<WorkoutSchedule> list = workoutScheduleRepository.findAllByMemberIdAndDateBetween(
        memberId, start, end);

    if (list.isEmpty()) {
      throw new WorkoutScheduleNotFoundException();
    }

    return list.stream().map(WorkoutScheduleDto::fromEntity).toList();
  }

  public WorkoutScheduleDto getSchedule(Long memberId, Long scheduleId) {

    WorkoutSchedule workoutSchedule = workoutScheduleRepository.findById(scheduleId)
        .orElseThrow(WorkoutScheduleNotFoundException::new);

    if (!Objects.equals(memberId, workoutSchedule.getMember().getId())) {
      throw new AuthenticationException();
    }

    return WorkoutScheduleDto.fromEntity(workoutSchedule);
  }
}
