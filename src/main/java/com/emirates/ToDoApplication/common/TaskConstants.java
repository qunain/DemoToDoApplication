package com.emirates.ToDoApplication.common;

/**
 * Created by qmushtaq on 3/29/18.
 */
public enum TaskConstants {
  /*
 * TABLE_NAME , is the constant for "tasks" table name.
 */
  TABLE_NAME("tasks"), ID("id");

  private String name;

  TaskConstants(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
