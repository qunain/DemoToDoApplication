/*
 * Copyright (c) 2017 JCPenney Co. All rights reserved.
 */

package com.emirates.ToDoApplication.exceptions;

import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessExceptions extends RuntimeException {

  public BusinessExceptions(List<BusinessException> businessExceptions) {
    this.businessExceptions = businessExceptions;
  }

  @Getter
  private List<BusinessException> businessExceptions;

  public List<ErrorResponse> getErrorResponseList() {
    if(CollectionUtils.isNotEmpty(businessExceptions)) {
      return getBusinessExceptions()
          .stream()
          .map(businessException -> businessException.getErrorResponse())
          .collect(Collectors.toList());
    }
    return Collections.EMPTY_LIST;
  }
}
