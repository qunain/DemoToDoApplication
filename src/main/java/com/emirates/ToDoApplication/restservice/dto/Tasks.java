package com.emirates.ToDoApplication.restservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qmushtaq on 3/28/18.
 */
@Data
@NoArgsConstructor
public class Tasks {

  @JsonProperty("task")
  private List<TaskDetailsResponse> taskDetailsResponse = new ArrayList<>();
}
