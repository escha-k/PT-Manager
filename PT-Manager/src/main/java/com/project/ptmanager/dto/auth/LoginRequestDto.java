package com.project.ptmanager.dto.auth;

import lombok.Data;

@Data
public class LoginRequestDto {

  private String username;
  private String password;
}
