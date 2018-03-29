package com.emirates.ToDoApplication.restservice;

import static com.emirates.ToDoApplication.common.LogConstants.LOG_KO_PATTERN;
import static com.emirates.ToDoApplication.common.LogConstants.LOG_OK_PATTERN;

import com.emirates.ToDoApplication.exceptions.InvalidRequestException;
import com.emirates.ToDoApplication.restservice.dto.TaskDetailsDto;
import com.emirates.ToDoApplication.restservice.dto.TaskDetailsRequest;
import com.emirates.ToDoApplication.restservice.dto.TaskDetailsResponse;
import com.emirates.ToDoApplication.restservice.dto.Tasks;
import com.emirates.ToDoApplication.restservice.dto.mapper.TaskDetailsRequestDtoMapper;
import com.emirates.ToDoApplication.restservice.dto.mapper.TaskDetailsResponseMapper;
import com.emirates.ToDoApplication.service.ToDoApplicationService;
import com.emirates.ToDoApplication.utils.GenerateHrefUtil;
import com.emirates.ToDoApplication.validations.constraints.Inner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Created by qmushtaq on 3/28/18.
 */
@RestController
@Slf4j
@RequestMapping(value = "/v1/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ToDoApplicationResource extends BaseResource{

  @Autowired
  ToDoApplicationService taskService;

  @GetMapping
  public ResponseEntity<Tasks> getTasks() {

    log.debug(LOG_OK_PATTERN, "getTasks", "get all tasks");
    List<TaskDetailsDto> taskDetailsList = taskService.getTaskList();
    log.info(LOG_OK_PATTERN, "getTasks", taskDetailsList!= null ? taskDetailsList.toString(): "no tasks available");
    List<TaskDetailsResponse> taskDetails = TaskDetailsResponseMapper.toTaskDetails(taskDetailsList);
    return new ResponseEntity(taskDetails, HttpStatus.OK);
  }

  @GetMapping(path="/{taskStatus}")
  public ResponseEntity<Tasks> getTasksByStatus(@PathVariable("taskStatus") String
                                                                      taskStatus) {
    log.debug(LOG_OK_PATTERN, "getTasks", "get all tasks");
    List<TaskDetailsDto> taskDetailsList = taskService.getTasksByStatus(taskStatus);
    log.info(LOG_OK_PATTERN, "getTasks", taskDetailsList!= null ? taskDetailsList.toString():
                "no tasks available for given status=" + taskStatus );
    List<TaskDetailsResponse> taskDetails = TaskDetailsResponseMapper.toTaskDetails(taskDetailsList);
    return new ResponseEntity(taskDetails, HttpStatus.OK);
  }

  @GetMapping(path="/task/{taskId}")
  public ResponseEntity<TaskDetailsResponse> getTaskById(@PathVariable("taskId") UUID
                                                                    taskId) {
    log.debug(LOG_OK_PATTERN, "getTaskById", "get all tasks");
    TaskDetailsDto taskDetail = taskService.getTaskById(taskId.toString());
    log.info(LOG_OK_PATTERN, "getTaskById", taskDetail != null ? taskDetail.toString():
          "no tasks available for given id=" + taskId );
    TaskDetailsResponse taskDetails = TaskDetailsResponseMapper.toTask(taskDetail);
    return new ResponseEntity<TaskDetailsResponse>(taskDetails, HttpStatus.OK);
  }


  @PostMapping
  public ResponseEntity addTask(
        @Validated @RequestBody TaskDetailsRequest taskDetailsRequest,
        BindingResult result) {

     log.info(LOG_OK_PATTERN, "addTask", "Add task for request =" + taskDetailsRequest);

     if (result.hasErrors()) {
      log.error(LOG_KO_PATTERN, "addTask", "Error for Add task for request =" + taskDetailsRequest + "with result =" +
            result.toString());

      throw new InvalidRequestException(result);
    }
    //mapping the request to dto
    TaskDetailsDto taskDetailsDto =
          TaskDetailsRequestDtoMapper.toTaskDetailsDto(taskDetailsRequest);

    UUID id = taskService.addTaskDetails(taskDetailsDto);

    TaskDetailsResponse responseDto =  TaskDetailsResponse.builder().build();
    responseDto.setId(id != null ? id.toString() : null);

    HttpHeaders httpHeaders = new HttpHeaders();
    setLocationHeader(httpHeaders, GenerateHrefUtil.getHrefForTaskDetails(id.toString()));

    log.info(LOG_OK_PATTERN, "addTask", " successfully created task for request =" + taskDetailsRequest);
    return new ResponseEntity(responseDto, httpHeaders, HttpStatus.CREATED);
  }

  @PutMapping("/task/{taskId}")
  public ResponseEntity updateTask(@RequestBody TaskDetailsRequest taskDetailsRequest,
                                   @PathVariable("taskId") String taskId, BindingResult result) {

    log.info(LOG_OK_PATTERN, "updateTask", "update task for request =" + taskDetailsRequest);

    if (result.hasErrors()) {
      log.error(LOG_KO_PATTERN, "updateTask", "Error for update task for request =" + taskDetailsRequest +
            "with result =" + result.toString());

      throw new InvalidRequestException(result);
    }
    //mapping the request to dto
    TaskDetailsDto taskDetailsDto = TaskDetailsRequestDtoMapper.toTaskDetailsDto(taskDetailsRequest);
    taskDetailsDto.setId(UUID.fromString(taskId));
    TaskDetailsDto updatedTaskDto = taskService.updateTaskDetails(taskDetailsDto);

    TaskDetailsResponse taskDetails = TaskDetailsResponseMapper.toTask(updatedTaskDto);
    return new ResponseEntity<TaskDetailsResponse>(taskDetails, HttpStatus.OK);
  }

  @DeleteMapping("/task/{taskId}")
  public ResponseEntity deleteTask(@PathVariable("taskId") UUID taskId) {

    log.info(LOG_OK_PATTERN, "deleteTask", "delete task for taskId =" + taskId);

    if (null != taskId) {
      log.error(LOG_KO_PATTERN, "updateTask", "taskId is null") ;
      throw new InvalidRequestException("task Id cannot be null");
    }

    taskService.deleteTask(taskId.toString());

    log.debug(LOG_OK_PATTERN, "deleteTask", "delete task is success");
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

}
