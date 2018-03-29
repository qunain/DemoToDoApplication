/*
 * Copyright (c) 2017 JCPenney Co. All rights reserved.
 */

package com.emirates.ToDoApplication.restservice.dto.constraints;


import com.emirates.ToDoApplication.common.ValidationConfiguration;
import com.emirates.ToDoApplication.restservice.dto.TaskDetailsRequest;
import org.apache.commons.lang.StringUtils;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class TaskDetailsRequestValidator implements
      ConstraintValidator<ValidateTaskDetailsRequest,
            TaskDetailsRequest> {
  @Override
  public void initialize(
        ValidateTaskDetailsRequest constraintAnnotation) {

  }

  @Override
  public boolean isValid(TaskDetailsRequest value, ConstraintValidatorContext context) {


    if ( StringUtils.isBlank(value.getTaskName()) || StringUtils.isNumeric(value.getTaskName())) {
      addViolationToContext(context, "taskName", "TASK_NAME_INVALID");
      return false;
    }
    if (!isValidTaskStatus(value)) {
      addViolationToContext(context, "status", "TASK_STATUS_INVALID");
      return false;
    }

    return true;
  }


  private boolean isValidTaskStatus(TaskDetailsRequest value) {
    return ValidationConfiguration.config().taskStatusStrings().contains(value.getStatus());
  }

  protected void addViolationToContext(
        ConstraintValidatorContext context,
        String property,
        String errorCode) {
    context
          .buildConstraintViolationWithTemplate(errorCode)
          .addPropertyNode(property)
          .addConstraintViolation();
  }
}
