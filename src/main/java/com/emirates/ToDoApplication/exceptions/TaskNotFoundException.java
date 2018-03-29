package com.emirates.ToDoApplication.exceptions;

/**
 * Created by qmushtaq on 3/29/18.
 */
public class TaskNotFoundException  extends BusinessException {

  public TaskNotFoundException(String errorCode, String errorMessage, int errorStatus) {
    super(errorCode, errorMessage, errorStatus);
  }

}
