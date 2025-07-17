package com.project.ptmanager.service;

import static com.project.utils.DateUtils.endOfMonth;
import static com.project.utils.DateUtils.startOfMonth;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.domain.workout.WorkoutLog;
import com.project.ptmanager.dto.WorkoutLogDto;
import com.project.ptmanager.enums.WorkoutType;
import com.project.ptmanager.exception.MemberNotFoundException;
import com.project.ptmanager.exception.WorkoutLogNotFoundException;
import com.project.ptmanager.repository.member.MemberRepository;
import com.project.ptmanager.repository.workout.WorkoutLogRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWorkoutLogService {

  private final MemberRepository memberRepository;
  private final WorkoutLogRepository workoutLogRepository;

  public List<WorkoutLogDto> getWorkoutLogList(Long memberId, int year, int month) {
    LocalDate start = startOfMonth(year, month);
    LocalDate end = endOfMonth(year, month);

    List<WorkoutLog> list = workoutLogRepository.findAllByMemberIdAndDateBetween(
        memberId, start, end);

    if (list.isEmpty()) {
      throw new WorkoutLogNotFoundException("등록된 운동 일지가 없습니다.");
    }

    return list.stream().map(WorkoutLogDto::fromEntity).toList();
  }

  public WorkoutLogDto getWorkoutLog(Long memberId, LocalDate date) {
    WorkoutLog workoutLog = workoutLogRepository.findByMemberIdAndDate(memberId,
        date).orElseThrow(() -> new WorkoutLogNotFoundException("등록된 운동 일지가 없습니다."));

    return WorkoutLogDto.fromEntity(workoutLog);
  }

  public Long createWorkoutLog(LocalDate date, WorkoutType type,
      String exerciseList, Long memberId, Long trainerId) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException("회원 정보를 찾을 수 없습니다."));
    Member trainer = null;
    if (trainerId != null) {
      trainer = memberRepository.findById(trainerId)
          .orElseThrow(() -> new MemberNotFoundException("트레이너 정보를 찾을 수 없습니다."));
    }

    WorkoutLog log = WorkoutLog.builder()
        .date(date)
        .type(type)
        .exerciseList(exerciseList)
        .trainer(trainer)
        .member(member)
        .build();

    return workoutLogRepository.save(log).getId();
  }
}
