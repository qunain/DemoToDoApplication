package com.emirates.ToDoApplication.restservice.dto;

import com.emirates.ToDoApplication.restservice.dto.constraints.ValidateTaskDetailsRequest;
import com.emirates.ToDoApplication.validations.constraints.Inner;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * Created by qmushtaq on 3/28/18.
 */
@Builder
@Data
@ValidateTaskDetailsRequest
public class TaskDetailsRequest {

  @Tolerate
  TaskDetailsRequest(){

  }
  @JsonProperty("status")
  @NotNull(message = "API_ERR_FIELD_TASK_STATUS_MISSING")
  private String status;

  @JsonProperty("taskName")
  @NotNull(message = "API_ERR_FIELD_TASK_NAME_MISSING")
  private String taskName;
}
