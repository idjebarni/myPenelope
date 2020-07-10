package com.contact.dao.impl;

import com.contact.dao.IContactDao;
import com.contact.model.Contact;
import com.contact.observer.MySubjectObserver;

import java.lang.reflect.InvocationTargetException;

public class JsonContactDaoImpl extends AbstractJsonDao<Contact> implements IContactDao {

    private JsonContactDaoImpl(MySubjectObserver subject) {
        super(Contact.class, subject, "contact/contactJson.json");
    }

    private static class JsonPersonneDaoImplHolder
    {
        private static JsonContactDaoImpl instance = null;
    }

    public static JsonContactDaoImpl getInstance(MySubjectObserver subject)
    {
        JsonPersonneDaoImplHolder.instance = new JsonContactDaoImpl(subject);
        return JsonPersonneDaoImplHolder.instance;
    }

    @Override
    public Contact create(Contact obj) {
       return super.create(obj);
    }

    @Override
    public boolean delete(Contact contact) {
        return super.delete(contact);
    }

    @Override
    public void setSendNotification(boolean sendNotification) {
        this.sendNotification = sendNotification;
    }

    @Override
    public Contact findById(int idContact) {
        return super.findById(idContact);
    }

    @Override
    public Integer getPersonnesSize() {
        return super.getPersonnesSize();
    }

    @Override
    public Contact update(Contact p) {
        return super.update(p);
    }
}