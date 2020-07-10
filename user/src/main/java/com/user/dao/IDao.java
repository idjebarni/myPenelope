package com.user.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IDao<T> {

    List<T> findAll();

    T findById(int id);

    List<T> findByCriteria(String criteria, Object valueCriteria);

    T create(T t);

    boolean sendNotification();

    boolean delete(T t);

    T update(T t) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    Integer getPersonnesSize();

    Integer generateIdNewPersonne() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}