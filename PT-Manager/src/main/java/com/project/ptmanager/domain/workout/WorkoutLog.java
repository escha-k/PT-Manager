package com.project.ptmanager.domain.workout;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.domain.workout.model.Workout;
import com.project.ptmanager.enums.WorkoutType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate date;
  private WorkoutType type;

  @Type(JsonType.class)
  private List<Workout> exerciseList; // 운동 결과 - json 문자열로 저장

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member trainer; // 담당 트레이너
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member; // 대상 회원
}
