/*
 * Copyright (c) 2017 JCPenney Co. All rights reserved.
 */

package com.emirates.ToDoApplication.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by skumar47 and byadav on 3/8/2017.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class InvalidRequestException extends BusinessException {

  @SuppressWarnings({"squid:S1948"})
  private Errors errors;

  public InvalidRequestException(String errorMessage) {
    super(errorMessage);
  }

  public InvalidRequestException(String errorCode, String errorMessage) {
    super(errorCode, errorMessage);
  }

  public InvalidRequestException(String errorCode, String errorMessage, Errors errors) {
    super(errorCode, errorMessage);
    this.errors = errors;
  }

  public InvalidRequestException(Errors errors) {
    super("REQUEST_BINDING_EXECEPTION", "Invalid Request");
    this.errors = errors;
  }

}
