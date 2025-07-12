package com.project.ptmanager.domain.workout;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.enums.WorkoutType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSchedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate date;
  private WorkoutType type;

  @Column(columnDefinition = "json")
  private String exercisePlan; // 운동 플랜 - json 문자열로 저장

  private String memo;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member trainer; // 작성 트레이너
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member; // 대상 회원
}
