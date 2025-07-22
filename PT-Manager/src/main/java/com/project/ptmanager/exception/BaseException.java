package com.project.ptmanager.exception;

public abstract class BaseException extends RuntimeException {

  public abstract int getStatusCode();

  public abstract String getMessage();
} 