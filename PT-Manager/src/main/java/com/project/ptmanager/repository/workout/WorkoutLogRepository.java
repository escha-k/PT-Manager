package com.project.ptmanager.repository.workout;

import com.project.ptmanager.domain.workout.WorkoutLog;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
  List<WorkoutLog> findAllByMemberIdAndDateBetween(Long member_id, LocalDate start, LocalDate end);

  Optional<WorkoutLog> findByMemberIdAndDate(Long member_id, LocalDate date);
}
