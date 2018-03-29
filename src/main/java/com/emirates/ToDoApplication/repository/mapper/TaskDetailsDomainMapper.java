package com.emirates.ToDoApplication.repository.mapper;

import com.emirates.ToDoApplication.Dao.TaskDetails;
import com.emirates.ToDoApplication.restservice.dto.TaskDetailsDto;
import org.springframework.beans.BeanUtils;

/**
 * Created by qmushtaq on 3/28/18.
 */
public class TaskDetailsDomainMapper {

  public static TaskDetails toTaskDetails(TaskDetailsDto taskDetailsDto) {
      TaskDetails taskDetails = new TaskDetails();
      BeanUtils.copyProperties(taskDetailsDto, taskDetails);
      return taskDetails;
      }
}
