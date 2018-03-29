/*
 * Copyright (c) 2017 JCPenney Co. All rights reserved.
 */

package com.emirates.ToDoApplication.exceptions;


import com.emirates.ToDoApplication.common.LogConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ResponseEntityAdvice extends ResponseEntityExceptionHandler {

  @Value("${api.error.code.prefix}")
  private String errorCodePrefix;

  @Value("${api.error.message.prefix:task.errors.messages.}")
  private String errorMessagePrefix;

  @Autowired
  private Environment environment;


  @ExceptionHandler(value = {ApplicationException.class})
  public ResponseEntity applicationException(ApplicationException aexec) {
    return new ResponseEntity<>(getErrorResponse(aexec.getErrorCode(), aexec.getErrorMessage(), aexec),
        getHttpStatus(aexec, aexec.getErrorStatus()));
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity runTimeException(Exception exc) {
    return new ResponseEntity<>(getErrorResponse(null, exc.getMessage(),exc),
          getHttpStatus(exc, 500));
  }


  @ExceptionHandler(value = {BusinessException.class})
  public ResponseEntity businessException(BusinessException bexec) {
    return new ResponseEntity<>(getErrorResponse(bexec.getErrorCode(), bexec.getErrorMessage(), bexec),
        getHttpStatus(bexec, bexec.getErrorStatus()));
  }


  @ExceptionHandler(value = {BusinessExceptions.class})
  public ResponseEntity businessExceptions(BusinessExceptions be) {
    return new ResponseEntity(be.getErrorResponseList(),
        HttpStatus.valueOf(be.getBusinessExceptions().get(0).getErrorStatus()));
  }

  @ExceptionHandler({InvalidRequestException.class})
  public ResponseEntity handleInvalidRequest(InvalidRequestException ire) {
    List<ErrorResponse> fieldErrorResponseResources = new ArrayList<>();
    List<FieldError> fieldErrorResponses = ire.getErrors().getFieldErrors();
    for (FieldError fieldError : fieldErrorResponses) {
      String errorKey = fieldError.getDefaultMessage();
      String errorCode = environment.getProperty(errorCodePrefix + errorKey);
      String errorMessage = environment.getProperty(errorMessagePrefix + errorKey);
      ErrorResponse errorResponse = new ErrorResponse();
      errorResponse.setErrorCode(errorCode == null ? errorKey : errorCode);
      errorResponse.setErrorMessage(errorMessage == null ? errorKey : errorMessage);
      fieldErrorResponseResources.add(errorResponse);
    }
    return new ResponseEntity<>(fieldErrorResponseResources, getHttpStatus(ire, HttpStatus.BAD_REQUEST));
  }

  private HttpStatus getHttpStatus(Exception exception, int errHttpStatus) {
    HttpStatus httpStatus;
    try {
      if (errHttpStatus > 199) {
        return HttpStatus.valueOf(errHttpStatus);
      } else {
        return getHttpStatus(exception, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } catch (IllegalArgumentException illegalArgumentException) {
      log.error(LogConstants.LOG_KO_PATTERN, "getHttpStatus", "Invalid response code received " + errHttpStatus);
      log.debug(LogConstants.LOG_KO_PATTERN, "getHttpStatus", "Invalid response code received " + errHttpStatus,
          illegalArgumentException);
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return getHttpStatus(exception, httpStatus);
  }

  private HttpStatus getHttpStatus(Exception exception, HttpStatus errHttpStatus) {
    HttpStatus httpStatus;
    Annotation statusAnnotation = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);
    if (statusAnnotation != null) {
      httpStatus = (HttpStatus) AnnotationUtils.getValue(statusAnnotation);
    } else {
      httpStatus = errHttpStatus;
    }
    return httpStatus;
  }

  private List<ErrorResponse> getErrorResponse(String errorCodeParam, String errorMessageParam, Exception exec) {
    List<ErrorResponse> errorsList = new ArrayList<ErrorResponse>();
    ErrorResponse errorResponse = new ErrorResponse();
    String errorCode = environment.getProperty(errorCodePrefix + errorCodeParam);
    String errorMessage = environment.getProperty(errorMessagePrefix + errorCodeParam);
    errorResponse.setErrorCode(errorCode == null ? errorCodeParam : errorCode);
    errorResponse.setErrorMessage(errorMessage == null ? errorMessageParam : errorMessage);
    errorsList.add(errorResponse);
    return errorsList;
  }




}
