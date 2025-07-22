package com.project.ptmanager.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class MembershipDurationDto {

  LocalDate startDate;
  LocalDate endDate;
}
