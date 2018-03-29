/*
 * Copyright (c) 2017 JCPenney Co. All rights reserved.
 */

package com.emirates.ToDoApplication.exceptions;

import lombok.Getter;

/**
 *
 * This application is creating during application
 * like Database connection / downstream http error /
 *
 */

@Getter
public class ApplicationException extends RuntimeException {

  private int errorStatus;
  private String errorCode;
  private String errorMessage;
  private String errorSubCode;
  private Throwable throwable;

  public ApplicationException(String errorMessage) {
    super(errorMessage);
  }

  public ApplicationException(String errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public ApplicationException(String errorCode, String errorMessage, Throwable throwable) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.throwable = throwable;
  }

  public ApplicationException(String errorCode, String errorMessage, String errorSubCode, Throwable throwable) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.errorSubCode = errorSubCode;
    this.throwable = throwable;
  }

}
