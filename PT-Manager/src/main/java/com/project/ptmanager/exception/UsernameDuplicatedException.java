package com.project.ptmanager.exception;

public class UsernameDuplicatedException extends RuntimeException {

  public UsernameDuplicatedException(String message) {
    super(message);
  }
}
