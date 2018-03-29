package com.emirates.ToDoApplication.restservice.dto.mapper;

import com.emirates.ToDoApplication.restservice.dto.TaskDetailsDto;
import com.emirates.ToDoApplication.restservice.dto.TaskDetailsRequest;

/**
 * Created by qmushtaq on 3/28/18.
 */
public class TaskDetailsRequestDtoMapper {

  public static TaskDetailsDto toTaskDetailsDto(TaskDetailsRequest taskDetailsRequest) {
    TaskDetailsDto taskDetailsDto = new TaskDetailsDto();
    taskDetailsDto.setTaskName(taskDetailsRequest.getTaskName());
    taskDetailsDto.setTaskStatus(taskDetailsRequest.getStatus());
    return taskDetailsDto;
  }
}
