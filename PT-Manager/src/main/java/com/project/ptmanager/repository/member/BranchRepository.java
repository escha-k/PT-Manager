package com.project.ptmanager.repository.member;

import com.project.ptmanager.domain.member.Branch;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

  Optional<Branch> findByName(String branchName);
}
