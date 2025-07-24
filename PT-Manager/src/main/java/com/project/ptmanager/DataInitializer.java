package com.project.ptmanager;

import com.project.ptmanager.domain.member.Branch;
import com.project.ptmanager.repository.member.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;




@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  private final BranchRepository branchRepository;

  @Override
  public void run(String... args) {
    if (branchRepository.count() == 0) {
      branchRepository.save(Branch.builder().name("더미 지점").build());
    }
  }
}

