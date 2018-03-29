package com.emirates.ToDoApplication.Dao;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.UUID;

/**
 * Created by qmushtaq on 3/28/18.
 */
@Data
@Table("tasks")
public class TaskDetails {

  @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED)
  private UUID id;

  @Column("task_name")
  private String taskName;

  @Column("task_status")
  private String taskStatus;


}
