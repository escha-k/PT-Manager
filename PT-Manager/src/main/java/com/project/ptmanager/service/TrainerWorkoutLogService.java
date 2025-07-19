package com.project.ptmanager.service;

import static com.project.ptmanager.utils.DateUtils.endOfMonth;
import static com.project.ptmanager.utils.DateUtils.startOfMonth;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.domain.workout.WorkoutLog;
import com.project.ptmanager.dto.WorkoutLogCreateRequest;
import com.project.ptmanager.dto.WorkoutLogDto;
import com.project.ptmanager.enums.WorkoutType;
import com.project.ptmanager.exception.AuthenticationException;
import com.project.ptmanager.exception.MemberNotFoundException;
import com.project.ptmanager.exception.WorkoutLogNotFoundException;
import com.project.ptmanager.repository.member.MemberRepository;
import com.project.ptmanager.repository.member.TrainerMemberMatchingRepository;
import com.project.ptmanager.repository.workout.WorkoutLogRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerWorkoutLogService {

  private final MemberRepository memberRepository;
  private final WorkoutLogRepository workoutLogRepository;
  private final TrainerMemberMatchingRepository trainerMemberMatchingRepository;

  public List<WorkoutLogDto> getWorkoutLogList(Long trainerId, Long memberId, int year,
      int month) {

    validateMatching(trainerId, memberId);

    LocalDate start = startOfMonth(year, month);
    LocalDate end = endOfMonth(year, month);

    List<WorkoutLog> list = workoutLogRepository.findAllByMemberIdAndDateBetween(trainerId, start,
        end);

    if (list.isEmpty()) {
      throw new WorkoutLogNotFoundException("등록된 운동 일지 정보가 없습니다.");
    }

    return list.stream().map(WorkoutLogDto::fromEntity).toList();

  }

  public WorkoutLogDto getWorkoutLog(Long trainerId, Long memberId, Long logId) {

    validateMatching(trainerId, memberId);

    WorkoutLog workoutLog = workoutLogRepository.findById(logId)
        .orElseThrow(() -> new WorkoutLogNotFoundException("등록된 운동 일지 정보가 없습니다."));

    if (!Objects.equals(memberId, workoutLog.getMember().getId())) {
      throw new AuthenticationException("본인 담당 회원의 일지가 아닙니다.");
    }

    return WorkoutLogDto.fromEntity(workoutLog);
  }

  public Long createLog(Long trainerId, Long memberId,
      WorkoutLogCreateRequest request) {

    validateMatching(trainerId, memberId);

    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException("회원 정보를 찾을 수 없습니다."));
    Member trainer = memberRepository.findById(trainerId)
        .orElseThrow(() -> new MemberNotFoundException("트레이너 정보를 찾을 수 없습니다."));

    WorkoutLog workoutLog = WorkoutLog.builder()
        .date(request.getDate())
        .type(WorkoutType.PT)
        .exerciseList(request.getExerciseList())
        .trainer(trainer)
        .member(member)
        .build();

    WorkoutLog saved = workoutLogRepository.save(workoutLog);

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
