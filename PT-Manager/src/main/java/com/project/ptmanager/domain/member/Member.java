package com.project.ptmanager.domain.member;

import com.project.ptmanager.enums.MemberRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username; // 로그인용 id

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name; // 회원 이름

  @Column(nullable = false, unique = true)
  private String phoneNumber;

  @Column(nullable = false)
  private MemberRole role;

  private String deviceToken; // FCM 디바이스 토큰

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private Branch branch;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}

