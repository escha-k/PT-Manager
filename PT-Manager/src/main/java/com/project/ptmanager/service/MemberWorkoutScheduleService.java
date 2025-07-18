package com.project.ptmanager.service;

import static com.project.utils.DateUtils.endOfMonth;
import static com.project.utils.DateUtils.startOfMonth;

import com.project.ptmanager.domain.workout.WorkoutSchedule;
import com.project.ptmanager.dto.WorkoutScheduleDto;
import com.project.ptmanager.exception.AuthenticationException;
import com.project.ptmanager.exception.WorkoutScheduleNotFoundException;
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
      throw new WorkoutScheduleNotFoundException("등록된 스케줄 정보가 없습니다.");
    }

    return list.stream().map(WorkoutScheduleDto::fromEntity).toList();
  }

  public WorkoutScheduleDto getSchedule(Long memberId, Long scheduleId) {

    WorkoutSchedule workoutSchedule = workoutScheduleRepository.findById(scheduleId)
        .orElseThrow(() -> new WorkoutScheduleNotFoundException("등록된 스케줄 정보가 없습니다."));

    if (Objects.equals(memberId, workoutSchedule.getMember().getId())) {
      throw new AuthenticationException("본인의 운동 스케줄이 아닙니다.");
    }

    return WorkoutScheduleDto.fromEntity(workoutSchedule);
  }
}
