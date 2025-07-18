package com.project.ptmanager.repository.member;

import com.project.ptmanager.domain.member.TrainerMemberMatching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerMemberMatchingRepository extends JpaRepository<TrainerMemberMatching, Long> {

}
