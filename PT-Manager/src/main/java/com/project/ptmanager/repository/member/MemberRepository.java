package com.project.ptmanager.repository.member;

import com.project.ptmanager.domain.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByUsername(String username);

  boolean existsByUsername(String username);
  boolean existsByPhoneNumber(String phoneNumber);
}
