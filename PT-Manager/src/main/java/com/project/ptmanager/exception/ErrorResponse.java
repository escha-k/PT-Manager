package com.project.ptmanager.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

  private final int errorCode;
  private final String message;
} 