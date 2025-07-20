package com.project.ptmanager.repository.member;

import com.project.ptmanager.domain.member.TrainerMemberMatching;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerMemberMatchingRepository extends
    JpaRepository<TrainerMemberMatching, Long> {

  Boolean existsByMemberIdAndTrainerId(Long memberId, Long trainerId);

  Optional<TrainerMemberMatching> findByMemberId(Long memberId);

  List<TrainerMemberMatching> findByTrainerId(Long trainerId);
}
