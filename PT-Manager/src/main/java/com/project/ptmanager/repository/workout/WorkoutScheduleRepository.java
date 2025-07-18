package com.project.ptmanager.repository.workout;

import com.project.ptmanager.domain.workout.WorkoutSchedule;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutScheduleRepository extends JpaRepository<WorkoutSchedule, Long> {

  List<WorkoutSchedule> findAllByMemberIdAndDateBetween(Long memberId, LocalDate start,
      LocalDate end);

  Optional<WorkoutSchedule> findByMemberIdAndDate(Long memberId, LocalDate date);
}
