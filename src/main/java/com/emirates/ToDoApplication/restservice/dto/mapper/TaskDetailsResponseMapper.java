package com.emirates.ToDoApplication.restservice.dto.mapper;

import com.emirates.ToDoApplication.restservice.dto.TaskDetailsDto;
import com.emirates.ToDoApplication.restservice.dto.TaskDetailsResponse;
import com.emirates.ToDoApplication.restservice.dto.Tasks;
import com.emirates.ToDoApplication.utils.GenerateHrefUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qmushtaq on 3/28/18.
 */
public class TaskDetailsResponseMapper {

  public static ArrayList<TaskDetailsResponse> toTaskDetails(List<TaskDetailsDto> taskDetailsList) {
    ArrayList<TaskDetailsResponse> tasks = new ArrayList<>();
    if (!CollectionUtils.isEmpty(taskDetailsList)) {
      tasks.addAll(
            taskDetailsList.stream()
                  .map(taskDetail -> toTask(taskDetail))
                  .collect(Collectors.toList()));
    }
    return tasks;
  }

  public static TaskDetailsResponse toTask(TaskDetailsDto taskDetail) {
    return TaskDetailsResponse.builder().
          id(taskDetail.getId().toString()).
          taskName(taskDetail.getTaskName()).status(taskDetail.getTaskStatus()).
          href(GenerateHrefUtil.getHrefForTaskDetails(taskDetail.getId().toString())).build();

  }


}
