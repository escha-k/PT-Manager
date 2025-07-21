package com.project.ptmanager.service;

import static com.project.ptmanager.utils.DateUtils.endOfMonth;
import static com.project.ptmanager.utils.DateUtils.startOfMonth;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.domain.workout.WorkoutSchedule;
import com.project.ptmanager.dto.WorkoutScheduleCreateRequest;
import com.project.ptmanager.dto.WorkoutScheduleDto;
import com.project.ptmanager.enums.WorkoutType;
import com.project.ptmanager.exception.AuthenticationException;
import com.project.ptmanager.exception.MemberNotFoundException;
import com.project.ptmanager.exception.WorkoutScheduleNotFoundException;
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
      throw new WorkoutScheduleNotFoundException("해당 회원에게 등록된 스케줄이 없습니다.");
    }

    return list.stream().map(WorkoutScheduleDto::fromEntity).toList();
  }

  public WorkoutScheduleDto getSchedule(Long trainerId, Long memberId, Long scheduleId) {

    validateMatching(trainerId, memberId);

    WorkoutSchedule workoutSchedule = workoutScheduleRepository.findById(scheduleId)
        .orElseThrow(() -> new WorkoutScheduleNotFoundException("등록된 스케줄 정보가 없습니다."));

    if (!Objects.equals(memberId, workoutSchedule.getMember().getId())) {
      throw new AuthenticationException("선택한 회원의 운동 스케줄이 아닙니다.");
    }

    return WorkoutScheduleDto.fromEntity(workoutSchedule);
  }

  public Long createSchedule(Long trainerId, Long memberId,
      WorkoutScheduleCreateRequest request) {

    validateMatching(trainerId, memberId);

    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException("회원 정보를 찾을 수 없습니다."));
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
      throw new AuthenticationException("본인 담당 회원이 아닙니다.");
    }
  }
}
