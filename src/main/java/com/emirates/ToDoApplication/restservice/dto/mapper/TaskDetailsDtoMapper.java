package com.emirates.ToDoApplication.restservice.dto.mapper;

import com.emirates.ToDoApplication.Dao.TaskDetails;
import com.emirates.ToDoApplication.restservice.dto.TaskDetailsDto;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qmushtaq on 3/28/18.
 */
public class TaskDetailsDtoMapper {


  public static List<TaskDetailsDto> toTaskDetailsDtoList(List<? extends TaskDetails> taskDetailsList) {
    List<TaskDetailsDto> taskDtoList = new ArrayList<TaskDetailsDto>();

    taskDetailsList.forEach(taskDetails -> {
      taskDtoList.add(toTaskDetailDto(taskDetails));
    });

    return taskDtoList;
  }

  public static TaskDetailsDto toTaskDetailDto(TaskDetails taskDetails) {
    TaskDetailsDto taskDetailsDto = new TaskDetailsDto();

    BeanUtils.copyProperties(taskDetails , taskDetailsDto);
    return taskDetailsDto;
  }

  public static TaskDetailsDto updateDomainTaskDetails(TaskDetailsDto domainTaskDetail, TaskDetailsDto inputTaskDetailsDto) {
    if(inputTaskDetailsDto.getTaskStatus() != null ){
      domainTaskDetail.setTaskStatus(inputTaskDetailsDto.getTaskStatus());
    }
    return domainTaskDetail;
  }
}
