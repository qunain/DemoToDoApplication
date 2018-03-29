/*
 * Copyright (c) 2017 JCPenney Co. All rights reserved.
 */

package com.emirates.ToDoApplication.repository.impl;



import com.datastax.driver.core.querybuilder.Delete;
import com.emirates.ToDoApplication.repository.CommonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CommonRepositoryImpl<T> implements CommonRepository<T> {

  private CassandraOperations cassandraOperations;



  public CommonRepositoryImpl(CassandraOperations cassandraOperations) {
    this.cassandraOperations = cassandraOperations;
  }

  public List<? extends T> select(String statement, Class<? extends T> clazz) {
    return cassandraOperations.select(statement, clazz);
  }

  public T selectOne(String statement, Class<? extends T> clazz) {

    return cassandraOperations.selectOne(statement, clazz);

  }

  @Override
  public T insert(T entity) {
    return cassandraOperations.insert(entity);
  }


  @Override
  public T update(T entity) {
    return cassandraOperations.update(entity);
  }

  @Override
  public void delete(T entity) {
    cassandraOperations.delete(entity);
  }

  @Override
  public void execute(Delete delete) {
    cassandraOperations.execute(delete);
  }

}