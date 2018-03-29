package com.emirates.ToDoApplication.restservice.dto;

import lombok.Data;

import java.util.UUID;

/**
 * Created by qmushtaq on 3/28/18.
 */
@Data
public class TaskDetailsDto {

  private UUID id;

  private String taskName;

  private String taskStatus;

}
