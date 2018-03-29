package com.emirates.ToDoApplication.service;

import com.emirates.ToDoApplication.restservice.dto.TaskDetailsDto;

import java.util.List;
import java.util.UUID;

/**
 * Created by qmushtaq on 3/28/18.
 */
public interface ToDoApplicationService {

  List<TaskDetailsDto> getTaskList();

  List<TaskDetailsDto> getTasksByStatus(String taskStatus);

  UUID addTaskDetails(TaskDetailsDto taskDetailsDto);

  TaskDetailsDto getTaskById(String taskId);

  TaskDetailsDto updateTaskDetails(TaskDetailsDto taskDetailsDto);

  void deleteTask(String s);
}
