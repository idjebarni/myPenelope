package com.user.dao.factory;

import com.user.dao.IUserDao;
import com.user.observer.Observer;
import com.user.observer.UserObserver;

public abstract class AbstractDaoFactory extends Observer {

    public abstract IUserDao getUserDao();

    public enum FactoryType {
         JSON_DAO
    }

    public static AbstractDaoFactory getDaoFactory(FactoryType type) {
        UserObserver subject = new UserObserver();
        AbstractDaoFactory tmp = null;

        subject.addObserver(JsonDaoFactory.getInstance(subject));

        if (type == FactoryType.JSON_DAO) {
            tmp = JsonDaoFactory.getInstance(subject);
        }
        return tmp;
    }
}
