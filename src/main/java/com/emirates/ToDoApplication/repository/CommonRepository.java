package com.emirates.ToDoApplication.repository;

import com.datastax.driver.core.querybuilder.Delete;

import java.util.List;

public interface CommonRepository<T> {

  T insert(T entity);

  T update(T entity);

  void delete(T entity);

  List<? extends T> select(String statement, Class<? extends T> clazz);

  T selectOne(String statement, Class<? extends T> clazz);

  void execute(Delete delete);
}