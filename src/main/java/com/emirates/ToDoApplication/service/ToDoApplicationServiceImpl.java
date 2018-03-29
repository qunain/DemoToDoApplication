package com.emirates.ToDoApplication.service;

import static com.emirates.ToDoApplication.common.LogConstants.LOG_OK_PATTERN;

import com.emirates.ToDoApplication.exceptions.TaskNotFoundException;
import com.emirates.ToDoApplication.repository.impl.ToDoApplicationRepositoryImpl;
import com.emirates.ToDoApplication.restservice.dto.TaskDetailsDto;
import com.emirates.ToDoApplication.restservice.dto.mapper.TaskDetailsDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by qmushtaq on 3/28/18.
 */
@Slf4j
@Service
public class ToDoApplicationServiceImpl implements ToDoApplicationService {

  @Autowired
  ToDoApplicationRepositoryImpl taskRepository;

  @Override
  public List<TaskDetailsDto> getTaskList() {
    log.debug("op={}, status=OK, desc={}", "getTaskList",
          "get all tasks");
    return taskRepository.getTaskList();
  }

  @Override
  public List<TaskDetailsDto> getTasksByStatus(String taskStatus) {
    log.debug("op={}, status=OK, desc={}", "getTaskListByStatus",
          "get all tasks");
    return taskRepository.getTaskListByStatus(taskStatus);
  }

  @Override
  public UUID addTaskDetails(TaskDetailsDto taskDetailsDto) {
    log.debug(LOG_OK_PATTERN, "addTaskDetails", "add task details");

    return taskRepository.addTaskDetails(taskDetailsDto);
  }

  @Override
  public TaskDetailsDto getTaskById(String taskId) {

    log.debug("op={}, status=OK, desc={}", "getTaskListByStatus",
          "get all tasks");
    return taskRepository.getTaskDetailById(UUID.fromString(taskId));
  }

  @Override
  public TaskDetailsDto updateTaskDetails(TaskDetailsDto taskDetailsDto) {
    log.info(LOG_OK_PATTERN, "updateTaskDetails",
          "for update task, get task from repo first");

    UUID taskId = taskDetailsDto.getId();
    TaskDetailsDto updatedDomainTaskDetails = null;
    TaskDetailsDto taskDetail = taskRepository.getTaskDetailById(taskId);

    if (taskDetail != null) {

      log.debug("op={}, status=OK, desc= got task from repo,task={}", "updateTaskDetails", taskDetail);

      log.debug("op={}, status=OK, desc= update only non null values", "updateTaskDetails");

       updatedDomainTaskDetails = TaskDetailsDtoMapper.updateDomainTaskDetails(taskDetail,
            taskDetailsDto);

      log.debug(LOG_OK_PATTERN, "updateTaskDetails", "dto is updated with only non null values");

    } else {

      log.error("op={}, status=OK, desc= no task found in repo for taskId={}",  "updateTaskDetails", taskId);

      throw new TaskNotFoundException("TASK_NOT_FOUND",
            "No task found for given taskId", HttpStatus.NOT_FOUND.value());

    }
    return taskRepository.updateTask(updatedDomainTaskDetails);
  }

  @Override
  public void deleteTask(String taskId) {
    log.info(LOG_OK_PATTERN, "deleteTask",
           "for delete task, get task from repo first");

    TaskDetailsDto taskDetail = taskRepository.getTaskDetailById(UUID.fromString(taskId));

    if (taskDetail != null) {

      log.debug("op={}, status=OK, desc= got task from repo,task={}", "deleteTask", taskDetail);

      taskRepository.deleteTask(taskId);

      log.info(LOG_OK_PATTERN, "deleteTask",
            "delete task is sucess");
    } else {

      log.error("op={}, status=OK, desc= no task found in repo for taskId={}",  "deleteTask", taskId);

      throw new TaskNotFoundException("TASK_NOT_FOUND",
            "No task found for given taskId", HttpStatus.NOT_FOUND.value());

    }
  }

}
