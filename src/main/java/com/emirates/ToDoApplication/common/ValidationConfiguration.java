package com.emirates.ToDoApplication.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class ValidationConfiguration implements InitializingBean {

  private static ValidationConfiguration instance = null;

  @Value("${validation.task.statusValues}")
  String taskStatusValues;

  Set<String> taskStatusStrings;

  public Set<String> taskStatusStrings() {
    return taskStatusStrings;
  }


  public static ValidationConfiguration config() {
    return instance;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.taskStatusStrings = this.parseSet(this.taskStatusValues);
    setInstance(this);
  }

  private Set<String> parseSet(String toParse) {
    return toParse != null
          ? Arrays.stream(toParse.split(","))
          .map(String::trim)
          .filter(StringUtils::isNotBlank)
          .collect(Collectors.toSet())
          : Collections.emptySet();
  }

  public String getTaskStatusValues() {
    return this.taskStatusValues;
  }

  private synchronized void setInstance(ValidationConfiguration instance) {
    ValidationConfiguration.instance = instance;
  }

}