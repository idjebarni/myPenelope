package com.contact.main;


import com.contact.dao.factory.AbstractDaoFactory;
import com.contact.dao.factory.JsonDaoFactory;
import com.contact.model.Contact;

public class MainDao {

    public static void main(String[] args) {

        JsonDaoFactory json = (JsonDaoFactory) JsonDaoFactory.getDaoFactory(AbstractDaoFactory.FactoryType.JSON_DAO);
        json.getContactDao().create(new Contact(json.getContactDao().getPersonnesSize(), "Imad sEddine", "DJEBARNI", "djebarni.imed@yahoo.fr", "0610317146", "Afak", "Alger", "16000"
        ));
    }
}
