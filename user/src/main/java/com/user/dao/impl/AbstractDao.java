package com.user.dao.impl;



import com.user.model.User;
import com.user.observer.UserObserver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Imad Eddine
 * @param <T>
 */
abstract class AbstractDao<T> {

    Class<T> myClass = null;
    UserObserver mySubject = null;
    Constructor<T> constructor;
    boolean sendNotification = true;

    {
        try {
            Class<T> theClass = (Class<T>) User.class;
            constructor = theClass.getConstructor(Integer.class, String.class, String.class, String.class, String.class);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    T findById(int id, List<T> obj) {
        T tmp = null;
        for (T per : obj) {
            try {
                if(Integer.parseInt(per.getClass().getMethod("getId").invoke(per).toString()) == id) {
                    tmp = per;
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return tmp;
    }

    Integer generateIdNewPersonne(List<T> obj){
        List<Integer> id = new ArrayList<>();
        for (T t : obj) {
            Method UserId;
            try {
                UserId = t.getClass().getMethod("getId");
                id.add((Integer) UserId.invoke(t));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return Collections.max(id) + 1;
    }

}
