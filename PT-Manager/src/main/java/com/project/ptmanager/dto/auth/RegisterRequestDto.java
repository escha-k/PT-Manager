package com.project.ptmanager.dto.auth;

import lombok.Data;

@Data
public class RegisterRequestDto {

  private String username;
  private String password;
  private String name;
  private String phoneNumber;
  private String role;
  private String branchName;
}
