package com.user.main;


import com.user.dao.factory.AbstractDaoFactory;
import com.user.dao.factory.JsonDaoFactory;
import com.user.model.User;
public class MainDao {

    public static void main(String[] args) {

        /*JsonDaoFactory json = (JsonDaoFactory) JsonDaoFactory.getDaoFactory(AbstractDaoFactory.FactoryType.JSON_DAO);
        json.getUserDao().create(new User(json.getUserDao().getPersonnesSize(), "Imad sEddine", "La vie est un crash test"));*/
    }
}
