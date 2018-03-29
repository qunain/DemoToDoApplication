/*
 * Copyright (c) 2017 JCPenney Co. All rights reserved.
 */

package com.emirates.ToDoApplication.exceptions;

import lombok.Getter;

/**
 *
 * This is used when some biz logic fails from the code.
 */

@Getter
public class BusinessException extends RuntimeException {

  private ErrorResponse errorResponse;
  private int errorStatus;
  private String errorCode;
  private String errorMessage;
  private String errorSubCode;
  private Throwable throwable;

  public BusinessException(String errorMessage) {
    super(errorMessage);
  }

  public BusinessException(String errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public BusinessException(String errorCode, String errorMessage, Throwable throwable) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.throwable = throwable;
  }

  public BusinessException(String errorCode, String errorMessage, String errorSubCode, Throwable throwable) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.throwable = throwable;
    this.errorSubCode = errorSubCode;
  }

  public BusinessException(String errorCode, String errorMessage, int errorStatus) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.errorStatus = errorStatus;
  }

  public BusinessException(String errorCode, String errorMessage, int errorStatus, Throwable throwable) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.errorStatus = errorStatus;
    this.throwable = throwable;
  }


  public BusinessException(String errorCode, String errorMessage, String severity, int errorStatus) {
    super("severity=" + severity + ". errorCode=" + errorStatus + ". message=[" + errorMessage + "]");
    this.errorCode = errorCode;
    this.errorStatus = errorStatus;
    this.errorMessage = errorMessage;
  }

  public BusinessException(ErrorResponse errorResponse, int statusCode) {
    this(errorResponse.getErrorCode(), errorResponse.getErrorMessage(), statusCode);
    this.errorResponse = errorResponse;
  }

  public ErrorResponse getErrorResponse() {
    if (errorResponse == null) {
      errorResponse = new ErrorResponse();
      errorResponse.setErrorCode(getErrorCode());
      errorResponse.setErrorMessage(getErrorMessage());
    }
    return errorResponse;
  }

}
