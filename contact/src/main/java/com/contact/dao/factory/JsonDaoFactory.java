package com.contact.dao.factory;

import com.contact.dao.impl.JsonContactDaoImpl;
import com.contact.dao.IContactDao;
import com.contact.observer.MySubjectObserver;

public class JsonDaoFactory extends AbstractDaoFactory {

    private IContactDao contactDao;

    private JsonDaoFactory(MySubjectObserver subject) {
        this.mySubjectObserver = subject;
        this.contactDao = JsonContactDaoImpl.getInstance(subject);
    }

    @Override
    public IContactDao getContactDao() {
        return this.contactDao;
    }

    private static class JsonDaoFactoryHolder
    {
        private static JsonDaoFactory instance = null;
    }

    static JsonDaoFactory getInstance(MySubjectObserver subject)
    {
        JsonDaoFactoryHolder.instance = new JsonDaoFactory(subject);
        return JsonDaoFactoryHolder.instance;
    }

    @Override
    public void updateSource() {

        switch (this.mySubjectObserver.getState()) {
            case 3 : case 5 :
                this.contactDao.setSendNotification(false);
                this.contactDao.create(this.mySubjectObserver.getContact());
                break;
            case 6 : case 4 :
                this.contactDao.setSendNotification(false);
                this.contactDao.delete(this.mySubjectObserver.getContact());
                break;
        }
    }
}
