package com.project.ptmanager.service.workout;

import static com.project.ptmanager.utils.DateUtils.endOfMonth;
import static com.project.ptmanager.utils.DateUtils.startOfMonth;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.domain.member.Membership;
import com.project.ptmanager.domain.member.PtHistory;
import com.project.ptmanager.domain.workout.WorkoutLog;
import com.project.ptmanager.dto.workout.WorkoutLogCreateRequestDto;
import com.project.ptmanager.dto.workout.WorkoutLogDto;
import com.project.ptmanager.enums.PtChangeType;
import com.project.ptmanager.enums.WorkoutType;
import com.project.ptmanager.exception.impl.AuthenticationException;
import com.project.ptmanager.exception.impl.MemberNotFoundException;
import com.project.ptmanager.exception.impl.WorkoutLogNotFoundException;
import com.project.ptmanager.repository.member.MemberRepository;
import com.project.ptmanager.repository.member.MembershipRepository;
import com.project.ptmanager.repository.member.PtHistoryRepository;
import com.project.ptmanager.repository.member.TrainerMemberMatchingRepository;
import com.project.ptmanager.repository.workout.WorkoutLogRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrainerWorkoutLogService {

  private final MemberRepository memberRepository;
  private final WorkoutLogRepository workoutLogRepository;
  private final TrainerMemberMatchingRepository trainerMemberMatchingRepository;
  private final MembershipRepository membershipRepository;
  private final PtHistoryRepository ptHistoryRepository;

  public List<WorkoutLogDto> getWorkoutLogList(Long trainerId, Long memberId, int year,
      int month) {

    validateMatching(trainerId, memberId);

    LocalDate start = startOfMonth(year, month);
    LocalDate end = endOfMonth(year, month);

    List<WorkoutLog> list = workoutLogRepository.findAllByMemberIdAndDateBetween(trainerId, start,
        end);

    if (list.isEmpty()) {
      throw new WorkoutLogNotFoundException();
    }

    return list.stream().map(WorkoutLogDto::fromEntity).toList();

  }

  public WorkoutLogDto getWorkoutLog(Long trainerId, Long memberId, Long logId) {

    validateMatching(trainerId, memberId);

    WorkoutLog workoutLog = workoutLogRepository.findById(logId)
        .orElseThrow(WorkoutLogNotFoundException::new);

    if (!Objects.equals(memberId, workoutLog.getMember().getId())) {
      throw new AuthenticationException();
    }

    return WorkoutLogDto.fromEntity(workoutLog);
  }

  @Transactional
  public Long createLog(Long trainerId, Long memberId,
      WorkoutLogCreateRequestDto request) {

    validateMatching(trainerId, memberId);

    Member member = memberRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);
    Member trainer = memberRepository.findById(trainerId)
        .orElseThrow(MemberNotFoundException::new);
    Membership membership = membershipRepository.findById(memberId).get(); // null 검사 생략

    WorkoutLog workoutLog = WorkoutLog.builder()
        .date(request.getDate())
        .type(WorkoutType.PT)
        .exerciseList(request.getExerciseList())
        .trainer(trainer)
        .member(member)
        .build();

    WorkoutLog saved = workoutLogRepository.save(workoutLog);

    int ptRemaining = membership.getPtRemaining();
    membership.setPtRemaining(ptRemaining - 1);
    membershipRepository.save(membership);

    PtHistory history = PtHistory.builder()
        .member(member)
        .changeType(PtChangeType.USE)
        .amount(1)
        .build();

    ptHistoryRepository.save(history);

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
