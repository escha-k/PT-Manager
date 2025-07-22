package com.project.ptmanager.service;

import com.project.ptmanager.domain.workout.WorkoutFeedback;
import com.project.ptmanager.dto.WorkoutFeedbackResponseDto;
import com.project.ptmanager.exception.AuthenticationException;
import com.project.ptmanager.exception.WorkoutFeedbackNotFoundException;
import com.project.ptmanager.repository.workout.WorkoutFeedbackRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWorkoutFeedbackService {

  private final WorkoutFeedbackRepository workoutFeedbackRepository;

  public List<WorkoutFeedbackResponseDto> getFeedbackList(Long memberId, Long logId) {

    List<WorkoutFeedback> list = workoutFeedbackRepository.findByLogId(logId);

    if (list.isEmpty()) {
      throw new WorkoutFeedbackNotFoundException("등록된 피드백이 없습니다.");
    }

    if (!Objects.equals(memberId, list.get(0).getLog().getMember().getId())) {
      throw new AuthenticationException("본인의 일지가 아닙니다.");
    }

    return list.stream().map(WorkoutFeedbackResponseDto::fromEntity).toList();
  }
}
