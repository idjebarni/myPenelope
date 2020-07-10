package com.user.dao.factory;

import com.user.dao.IUserDao;
import com.user.dao.impl.JsonUserDaoImpl;
import com.user.observer.UserObserver;

public class JsonDaoFactory extends AbstractDaoFactory {

    private IUserDao userDao;

    private JsonDaoFactory(UserObserver subject) {
        this.mySubjectObserver = subject;
        this.userDao = JsonUserDaoImpl.getInstance(subject);
    }

    @Override
    public IUserDao getUserDao() {
        return this.userDao;
    }

    private static class JsonDaoFactoryHolder
    {
        private static JsonDaoFactory instance = null;
    }

    static JsonDaoFactory getInstance(UserObserver subject)
    {
        JsonDaoFactoryHolder.instance = new JsonDaoFactory(subject);
        return JsonDaoFactoryHolder.instance;
    }

    @Override
    public void updateSource() {

        switch (this.mySubjectObserver.getState()) {
            case 3 : case 5 :
                this.userDao.setSendNotification(false);
                this.userDao.create(this.mySubjectObserver.getUser());
                break;
            case 6 : case 4 :
                this.userDao.setSendNotification(false);
                this.userDao.delete(this.mySubjectObserver.getUser());
                break;
        }
    }
}
