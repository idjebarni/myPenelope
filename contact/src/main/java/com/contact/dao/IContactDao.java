package com.contact.dao;


import com.contact.model.Contact;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IContactDao {

    Contact create(Contact p);

    boolean delete(Contact p);

    void setSendNotification(boolean sendNotification);

    Integer getPersonnesSize();

    Contact findById(int id);

    List findAll();

    Contact update(Contact p);
}
