package com.user.controller;

import com.user.dao.factory.AbstractDaoFactory;
import com.user.dao.factory.JsonDaoFactory;
import org.springframework.web.bind.annotation.*;
import com.user.model.User;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    private JsonDaoFactory t = (JsonDaoFactory) JsonDaoFactory.getDaoFactory(AbstractDaoFactory.FactoryType.JSON_DAO);

    @GetMapping(value = "/Login")
    public void addUser() {
        System.out.println("addUser method" + t.getUserDao().findById(2));
    }


    @PostMapping(value = "/Login")
    public User addUser(@Valid @RequestBody User user) {

        System.out.println(user.getUsername());
        System.out.println(user.getMdp());
        System.out.println(user.toString());

        return user;
    }
}
