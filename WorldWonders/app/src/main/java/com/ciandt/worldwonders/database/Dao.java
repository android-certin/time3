package com.ciandt.worldwonders.database;

import java.util.List;

public interface Dao<T> {

    List<T> getAll();
    T getById(int id);
    List<T> search(String word);
    boolean update(T data);
    boolean delete(T data);
    boolean insert(T data);
    void close();

}