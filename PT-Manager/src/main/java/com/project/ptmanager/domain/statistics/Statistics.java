package com.project.ptmanager.domain.statistics;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.enums.StatisticsType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statistics {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private StatisticsType type;

  private LocalDate startDate;

  private Double attendance;

  private Double totalWeight;

  @ManyToOne
  private Member member;
}
