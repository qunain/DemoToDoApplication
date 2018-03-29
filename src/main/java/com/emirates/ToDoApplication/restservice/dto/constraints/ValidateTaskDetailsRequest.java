/*
 * Copyright (c) 2017 JCPenney Co. All rights reserved.
 */

package com.emirates.ToDoApplication.restservice.dto.constraints;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TaskDetailsRequestValidator.class)
public @interface ValidateTaskDetailsRequest {


  /**
   * Message.
   *
   * @return message
   */
  String message() default "{com.emirates.todoapplication.restservice.dto.constraints" +
        ".ValidateTaskDetailsRequest}";


  /**
   * Groups.
   *
   * @return groups
   */
  Class<?>[] groups() default {};

  /**
   * Payload.
   *
   * @return payload
   */
  Class<? extends Payload>[] payload() default {};

}
