package com.project.ptmanager.service;

import static com.project.ptmanager.utils.DateUtils.endOfMonth;
import static com.project.ptmanager.utils.DateUtils.startOfMonth;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.domain.workout.WorkoutSchedule;
import com.project.ptmanager.dto.workout.WorkoutScheduleCreateRequestDto;
import com.project.ptmanager.dto.workout.WorkoutScheduleDto;
import com.project.ptmanager.enums.WorkoutType;
import com.project.ptmanager.exception.impl.AuthenticationException;
import com.project.ptmanager.exception.impl.MemberNotFoundException;
import com.project.ptmanager.exception.impl.WorkoutScheduleNotFoundException;
import com.project.ptmanager.repository.member.MemberRepository;
import com.project.ptmanager.repository.member.TrainerMemberMatchingRepository;
import com.project.ptmanager.repository.workout.WorkoutScheduleRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerWorkoutScheduleService {

  private final MemberRepository memberRepository;
  private final WorkoutScheduleRepository workoutScheduleRepository;
  private final TrainerMemberMatchingRepository trainerMemberMatchingRepository;

  public List<WorkoutScheduleDto> getScheduleList(Long trainerId, Long memberId, int year,
      int month) {

    validateMatching(trainerId, memberId);

    LocalDate start = startOfMonth(year, month);
    LocalDate end = endOfMonth(year, month);

    List<WorkoutSchedule> list = workoutScheduleRepository.findAllByMemberIdAndDateBetween(
        memberId, start, end);

    if (list.isEmpty()) {
      throw new WorkoutScheduleNotFoundException();
    }

    return list.stream().map(WorkoutScheduleDto::fromEntity).toList();
  }

  public WorkoutScheduleDto getSchedule(Long trainerId, Long memberId, Long scheduleId) {

    validateMatching(trainerId, memberId);

    WorkoutSchedule workoutSchedule = workoutScheduleRepository.findById(scheduleId)
        .orElseThrow(WorkoutScheduleNotFoundException::new);

    if (!Objects.equals(memberId, workoutSchedule.getMember().getId())) {
      throw new AuthenticationException();
    }

    return WorkoutScheduleDto.fromEntity(workoutSchedule);
  }

  public Long createSchedule(Long trainerId, Long memberId,
      WorkoutScheduleCreateRequestDto request) {

    validateMatching(trainerId, memberId);

    Member member = memberRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);
    Member trainer = memberRepository.findById(trainerId).orElseThrow();

    WorkoutSchedule schedule = WorkoutSchedule.builder()
        .date(request.getDate())
        .type(WorkoutType.valueOf(request.getType()))
        .exercisePlan(request.getExercisePlan())
        .memo(request.getMemo())
        .member(member)
        .trainer(trainer)
        .build();

    WorkoutSchedule saved = workoutScheduleRepository.save(schedule);

    return saved.getId();
  }

  private void validateMatching(Long trainerId, Long memberId) {
    Boolean exists = trainerMemberMatchingRepository.existsByMemberIdAndTrainerId(memberId,
        trainerId);
    if (!exists) {
      throw new AuthenticationException();
    }
  }
}
