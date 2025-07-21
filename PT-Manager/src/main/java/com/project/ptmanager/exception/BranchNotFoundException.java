package com.project.ptmanager.exception;

public class BranchNotFoundException extends RuntimeException {

  public BranchNotFoundException(String message) {
    super(message);
  }
}
