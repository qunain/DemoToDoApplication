package com.emirates.ToDoApplication.repository;

import com.emirates.ToDoApplication.restservice.dto.TaskDetailsDto;

import java.util.List;
import java.util.UUID;

/**
 * Created by qmushtaq on 3/28/18.
 */
public interface ToDoApplicationRepository {

  List<TaskDetailsDto> getTaskListByStatus(String taskStatus);

  TaskDetailsDto getTaskDetailById(UUID taskId);

  public void deleteTask(String taskId);

}
