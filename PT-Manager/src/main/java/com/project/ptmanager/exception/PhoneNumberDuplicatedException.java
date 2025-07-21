package com.project.ptmanager.exception;

public class PhoneNumberDuplicatedException extends RuntimeException {
  public PhoneNumberDuplicatedException(String message) {
    super(message);
  }
}
