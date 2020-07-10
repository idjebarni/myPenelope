package com.user.dao;


import com.user.model.User;

import java.util.List;

public interface IUserDao {

    User create(User p);

    boolean delete(User p);

    void setSendNotification(boolean sendNotification);

    Integer getPersonnesSize();

    User findById(int id);

    List findAll();

    User update(User p);
}
