package com.emirates.ToDoApplication.restservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by qmushtaq on 3/28/18.
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDetailsResponse {

  @JsonProperty("id")
  private String id;

  @JsonProperty("taskStatus")
  private String status;

  @JsonProperty("taskName")
  private String taskName;

  @JsonProperty("href")
  private String href = null;

}
