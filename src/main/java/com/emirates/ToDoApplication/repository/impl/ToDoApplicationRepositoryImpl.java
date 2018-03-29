package com.emirates.ToDoApplication.repository.impl;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.emirates.ToDoApplication.common.LogConstants.LOG_OK_PATTERN;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.utils.UUIDs;
import com.emirates.ToDoApplication.Dao.TaskDetails;
import com.emirates.ToDoApplication.common.TaskConstants;
import com.emirates.ToDoApplication.repository.CommonRepository;
import com.emirates.ToDoApplication.repository.ToDoApplicationRepository;
import com.emirates.ToDoApplication.repository.mapper.TaskDetailsDomainMapper;
import com.emirates.ToDoApplication.restservice.dto.TaskDetailsDto;
import com.emirates.ToDoApplication.restservice.dto.mapper.TaskDetailsDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by qmushtaq on 3/28/18.
 */
@Slf4j
@Repository
public class ToDoApplicationRepositoryImpl implements ToDoApplicationRepository {

  CassandraOperations cassandraOperations;

  CommonRepository<TaskDetails> commonRepository;

  @Autowired
  public ToDoApplicationRepositoryImpl(
        CassandraOperations cassandraOperations,
        CommonRepository<TaskDetails> commonRepository) {
    this.cassandraOperations = cassandraOperations;
    this.commonRepository = commonRepository;
  }

  public UUID addTaskDetails(TaskDetailsDto taskDetailsDto) {

    log.debug(LOG_OK_PATTERN, "addTaskDetails",
          "Adding task details to repository for reqDto = " + taskDetailsDto);

    TaskDetails taskInfo = TaskDetailsDomainMapper.toTaskDetails(taskDetailsDto);
    UUID id = UUIDs.timeBased();
    taskInfo.setId(id);
    return commonRepository.insert(taskInfo).getId();
  }

  public List<TaskDetailsDto> getTaskList() {
    log.debug(LOG_OK_PATTERN, "getTaskList",
          "get list of tasks from task repository");

    Statement statement = QueryBuilder
          .select()
          .all()
          .from(TaskConstants.TABLE_NAME.getName());

    log.debug(LOG_OK_PATTERN, "getTaskList", statement);

    List<? extends TaskDetails> taskDetailsList = commonRepository.select(statement.toString(), TaskDetails.class);

    List<TaskDetailsDto> taskDetailsDto = TaskDetailsDtoMapper.toTaskDetailsDtoList(taskDetailsList);
    return taskDetailsDto;
  }

  @Override
  public List<TaskDetailsDto> getTaskListByStatus(String taskStatus) {
    log.debug(LOG_OK_PATTERN, "getTaskListByStatus",
          "get list of tasks by status from task repository");

    Statement statement = QueryBuilder
          .select()
          .all()
          .from("tasks");

    log.debug(LOG_OK_PATTERN, "getTaskList", statement);

    List<? extends TaskDetails> taskDetailsList = commonRepository.select(statement.toString(), TaskDetails.class);

    List<TaskDetailsDto> taskDetailsDto = TaskDetailsDtoMapper.toTaskDetailsDtoList(taskDetailsList);

     return taskDetailsDto.stream().filter(
           taskDetailDto -> taskDetailDto.getTaskStatus().equalsIgnoreCase(taskStatus))
          .collect(Collectors.toList());
  }

  @Override
  public TaskDetailsDto getTaskDetailById(UUID taskId) {
    log.debug(LOG_OK_PATTERN, "getTaskDetailById",
          "get task by Id from task repository");
    Statement statement = QueryBuilder
          .select()
          .all()
          .from(TaskConstants.TABLE_NAME.getName())
          .where(eq(TaskConstants.ID.getName(), taskId));

    TaskDetails taskDetails = commonRepository.selectOne(statement.toString(), TaskDetails.class);

    TaskDetailsDto taskDetailsDto = null;
    if (null != taskDetails) {
      taskDetailsDto = TaskDetailsDtoMapper.toTaskDetailDto(taskDetails);
    }
    return taskDetailsDto;
  }

  public void deleteTask(String taskId) {
    log.debug(LOG_OK_PATTERN,"deleteTask","Delete task details from repository");
    Delete delete = QueryBuilder.delete().from(TaskConstants.TABLE_NAME.getName());
    delete.where(QueryBuilder.eq(TaskConstants.ID.getName(), taskId));
    commonRepository.execute(delete);
  }

  public TaskDetailsDto updateTask(TaskDetailsDto updatedDomainTaskDetails) {
    log.debug(LOG_OK_PATTERN, "updateTask", "Update task details to repository");

    TaskDetails taskInfo = TaskDetailsDomainMapper.toTaskDetails(updatedDomainTaskDetails);
    TaskDetails result = commonRepository.update(taskInfo);

    TaskDetailsDto taskDetailsDto = TaskDetailsDtoMapper.toTaskDetailDto(result);

    return taskDetailsDto;
  }
}
