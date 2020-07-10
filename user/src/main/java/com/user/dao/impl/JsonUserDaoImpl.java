package com.user.dao.impl;


import com.user.dao.IUserDao;
import com.user.model.User;
import com.user.observer.UserObserver;

public class JsonUserDaoImpl extends AbstractJsonDao<User> implements IUserDao {

    private JsonUserDaoImpl(UserObserver subject) {
        super(User.class, subject, "user/userJson.json");
    }

    private static class JsonPersonneDaoImplHolder {
        private static JsonUserDaoImpl instance = null;
    }

    public static JsonUserDaoImpl getInstance(UserObserver subject) {
        JsonPersonneDaoImplHolder.instance = new JsonUserDaoImpl(subject);
        return JsonPersonneDaoImplHolder.instance;
    }

    @Override
    public User create(User obj) {
        return super.create(obj);
    }

    @Override
    public boolean delete(User user) {
        return super.delete(user);
    }

    @Override
    public void setSendNotification(boolean sendNotification) {
        this.sendNotification = sendNotification;
    }

    @Override
    public User findById(int idUser) {
        return super.findById(idUser);
    }

    @Override
    public Integer getPersonnesSize() {
        return super.getPersonnesSize();
    }

    @Override
    public User update(User p) {
        return super.update(p);
    }

}