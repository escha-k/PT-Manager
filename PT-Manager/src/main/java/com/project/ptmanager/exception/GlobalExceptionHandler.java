package com.project.ptmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
    ErrorResponse error = ErrorResponse.builder()
        .errorCode(ex.getStatusCode())
        .message(ex.getMessage())
        .build();
    return ResponseEntity.status(ex.getStatusCode()).body(error);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
    ErrorResponse error = ErrorResponse.builder()
        .errorCode(HttpStatus.FORBIDDEN.value())
        .message("접근 권한이 없습니다.")
        .build();
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    ErrorResponse error = ErrorResponse.builder()
        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message("서버 내부 오류가 발생했습니다.")
        .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}