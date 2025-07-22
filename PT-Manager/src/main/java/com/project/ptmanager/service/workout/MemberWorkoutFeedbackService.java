package com.project.ptmanager.service.workout;

import com.project.ptmanager.domain.workout.WorkoutFeedback;
import com.project.ptmanager.dto.workout.WorkoutFeedbackResponseDto;
import com.project.ptmanager.exception.impl.AuthenticationException;
import com.project.ptmanager.exception.impl.WorkoutFeedbackNotFoundException;
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
      throw new WorkoutFeedbackNotFoundException();
    }

    if (!Objects.equals(memberId, list.get(0).getLog().getMember().getId())) {
      throw new AuthenticationException();
    }

    return list.stream().map(WorkoutFeedbackResponseDto::fromEntity).toList();
  }
}
