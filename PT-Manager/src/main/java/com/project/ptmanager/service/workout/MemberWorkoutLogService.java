package com.project.ptmanager.service.workout;

import static com.project.ptmanager.utils.DateUtils.endOfMonth;
import static com.project.ptmanager.utils.DateUtils.startOfMonth;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.domain.member.TrainerMemberMatching;
import com.project.ptmanager.domain.workout.WorkoutLog;
import com.project.ptmanager.domain.workout.model.Workout;
import com.project.ptmanager.dto.workout.WorkoutLogDto;
import com.project.ptmanager.enums.WorkoutType;
import com.project.ptmanager.exception.impl.AuthenticationException;
import com.project.ptmanager.exception.impl.MemberNotFoundException;
import com.project.ptmanager.exception.impl.WorkoutLogNotFoundException;
import com.project.ptmanager.repository.member.MemberRepository;
import com.project.ptmanager.repository.member.TrainerMemberMatchingRepository;
import com.project.ptmanager.repository.workout.WorkoutLogRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWorkoutLogService {

  private final MemberRepository memberRepository;
  private final WorkoutLogRepository workoutLogRepository;
  private final TrainerMemberMatchingRepository trainerMemberMatchingRepository;

  public List<WorkoutLogDto> getWorkoutLogList(Long memberId, int year, int month) {
    LocalDate start = startOfMonth(year, month);
    LocalDate end = endOfMonth(year, month);

    List<WorkoutLog> list = workoutLogRepository.findAllByMemberIdAndDateBetween(memberId, start,
        end);

    if (list.isEmpty()) {
      throw new WorkoutLogNotFoundException();
    }

    return list.stream().map(WorkoutLogDto::fromEntity).toList();
  }

  public WorkoutLogDto getWorkoutLog(Long memberId, Long logId) {
    WorkoutLog workoutLog = workoutLogRepository.findById(logId)
        .orElseThrow(WorkoutLogNotFoundException::new);

    if (!Objects.equals(memberId, workoutLog.getMember().getId())) {
      throw new AuthenticationException();
    }

    return WorkoutLogDto.fromEntity(workoutLog);
  }

  public Long createWorkoutLog(LocalDate date, List<Workout> exerciseList, Long memberId) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);

    Member trainer = null;
    Optional<TrainerMemberMatching> matching = trainerMemberMatchingRepository.findByMemberId(
        memberId);
    if (matching.isPresent()) {
      trainer = matching.get().getTrainer();
    }

    WorkoutLog log = WorkoutLog.builder()
        .date(date)
        .type(WorkoutType.SELF)
        .exerciseList(exerciseList)
        .trainer(trainer)
        .member(member)
        .build();

    return workoutLogRepository.save(log).getId();
  }
}
