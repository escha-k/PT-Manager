package com.project.ptmanager.repository.workout;

import com.project.ptmanager.domain.workout.WorkoutFeedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutFeedbackRepository extends JpaRepository<WorkoutFeedback, Long> {

  List<WorkoutFeedback> findByLogId(Long logId);
}
