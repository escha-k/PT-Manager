package com.project.ptmanager.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
public class Membership {

  @Id
  private Long memberId;

  @OneToOne
  @MapsId
  @JoinColumn(name = "member_id")
  private Member member;

  private LocalDate startedAt;
  private LocalDate expiredAt;

  private int ptRemaining;
}

