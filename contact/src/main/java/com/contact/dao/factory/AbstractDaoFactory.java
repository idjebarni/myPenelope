package com.contact.dao.factory;

import com.contact.observer.MyObserver;
import com.contact.observer.MySubjectObserver;
import com.contact.dao.IContactDao;

public abstract class AbstractDaoFactory extends MyObserver {

    public abstract IContactDao getContactDao();

    public enum FactoryType {
         JSON_DAO
    }

    public static AbstractDaoFactory getDaoFactory(FactoryType type) {
        MySubjectObserver subject = new MySubjectObserver();
        AbstractDaoFactory tmp = null;

        subject.addObserver(JsonDaoFactory.getInstance(subject));

        if (type == FactoryType.JSON_DAO) {
            tmp = JsonDaoFactory.getInstance(subject);
        }
        return tmp;
    }
}
