package com.project.ptmanager.service;

import com.project.ptmanager.domain.workout.WorkoutFeedback;
import com.project.ptmanager.domain.workout.WorkoutLog;
import com.project.ptmanager.dto.WorkoutFeedbackRequestDto;
import com.project.ptmanager.dto.WorkoutFeedbackResponseDto;
import com.project.ptmanager.exception.AuthenticationException;
import com.project.ptmanager.exception.WorkoutFeedbackNotFoundException;
import com.project.ptmanager.exception.WorkoutLogNotFoundException;
import com.project.ptmanager.repository.member.TrainerMemberMatchingRepository;
import com.project.ptmanager.repository.workout.WorkoutFeedbackRepository;
import com.project.ptmanager.repository.workout.WorkoutLogRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerWorkoutFeedbackService {

  private final TrainerMemberMatchingRepository trainerMemberMatchingRepository;
  private final WorkoutLogRepository workoutLogRepository;
  private final WorkoutFeedbackRepository workoutFeedbackRepository;

  public List<WorkoutFeedbackResponseDto> getFeedbackList(Long trainerId, Long memberId,
      Long logId) {

    validateMatching(trainerId, memberId);

    WorkoutLog workoutLog = workoutLogRepository.findById(logId)
        .orElseThrow(() -> new WorkoutLogNotFoundException("등록된 일지 정보가 없습니다."));

    validateMemberLog(memberId, workoutLog);

    List<WorkoutFeedback> list = workoutFeedbackRepository.findByLogId(logId);

    if (list.isEmpty()) {
      throw new WorkoutFeedbackNotFoundException("등록된 피드백이 없습니다.");
    }

    return list.stream().map(WorkoutFeedbackResponseDto::fromEntity).toList();
  }

  public Long createFeedback(Long trainerId, Long memberId, Long logId,
      WorkoutFeedbackRequestDto request) {

    validateMatching(trainerId, memberId);

    WorkoutLog workoutLog = workoutLogRepository.findById(logId)
        .orElseThrow(() -> new WorkoutLogNotFoundException("등록된 일지 정보가 없습니다."));

    validateMemberLog(memberId, workoutLog);

    WorkoutFeedback feedback = WorkoutFeedback.builder()
        .content(request.getContent())
        .log(workoutLog)
        .build();

    WorkoutFeedback saved = workoutFeedbackRepository.save(feedback);

    return saved.getId();
  }

  private void validateMatching(Long trainerId, Long memberId) {
    if (!trainerMemberMatchingRepository.existsByMemberIdAndTrainerId(memberId, trainerId)) {
      throw new AuthenticationException("본인 담당 회원이 아닙니다.");
    }
  }

  private static void validateMemberLog(Long memberId, WorkoutLog workoutLog) {
    if (!Objects.equals(memberId, workoutLog.getMember().getId())) {
      throw new AuthenticationException("회원과 일지 정보가 일치하지 않습니다.");
    }
  }
}
