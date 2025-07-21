package com.project.ptmanager.dto;

import lombok.Data;

@Data
public class RegisterRequest {

  private String username;
  private String password;
  private String name;
  private String phoneNumber;
  private String role;
  private String branchName;
}
