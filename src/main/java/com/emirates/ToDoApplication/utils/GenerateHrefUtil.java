package com.emirates.ToDoApplication.utils;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.emirates.ToDoApplication.restservice.ToDoApplicationResource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenerateHrefUtil {

  public static String getHrefForTaskDetails(String taskId) {
    return linkTo(methodOn(ToDoApplicationResource.class)
        .getTaskById(UUID.fromString(taskId))).withSelfRel().getHref();
  }

}