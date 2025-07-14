package com.project.ptmanager.service;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.domain.workout.WorkoutLog;
import com.project.ptmanager.dto.WorkoutLogDto;
import com.project.ptmanager.enums.WorkoutType;
import com.project.ptmanager.repository.member.MemberRepository;
import com.project.ptmanager.repository.workout.WorkoutLogRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutLogService {

  private final MemberRepository memberRepository;
  private final WorkoutLogRepository workoutLogRepository;

  public List<WorkoutLogDto> getWorkoutLogList(Long memberId, int year, int month) {
    LocalDate start = LocalDate.of(year, month, 1);
    LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

    List<WorkoutLog> list = workoutLogRepository.findAllByMemberIdAndDateBetween(
        memberId, start, end);

    return list.stream().map(WorkoutLogDto::fromEntity).toList();
  }


  public WorkoutLogDto getWorkoutLog(Long memberId, LocalDate date) {
    WorkoutLog workoutLog = workoutLogRepository.findByMemberIdAndDate(memberId,
        date).orElseThrow();

    return WorkoutLogDto.fromEntity(workoutLog);
  }

  public Long createWorkoutLog(LocalDate date, WorkoutType type,
      String exerciseList, Long memberId, Long trainerId) {

    // TODO: 쿼리 2번 발생하는 문제 해결 방법 고민해보기
    Member member = memberRepository.findById(memberId).orElseThrow();
    Member trainer = null;
    if (trainerId != null) {
      trainer = memberRepository.findById(trainerId).orElseThrow();
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
