package com.user.controller;

import com.user.dao.factory.AbstractDaoFactory;
import com.user.dao.factory.JsonDaoFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.user.model.User;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private JsonDaoFactory t = (JsonDaoFactory) JsonDaoFactory.getDaoFactory(AbstractDaoFactory.FactoryType.JSON_DAO);

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List getUsers() {
        return t.getUserDao().findAll();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable final int id) throws Exception {
        if (t.getUserDao().findById(id) == null) {
            throw new Exception("Le produit avec l'id : " + id + " est introuvable");
        }
        return t.getUserDao().findById(id);
    }

    @PostMapping(value = "/users/register")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {

        if (t.getUserDao().create(user) == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(t.getUserDao().create(user).getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/users/{id}")
    public void removeUser(@PathVariable int id) {
        t.getUserDao().delete(t.getUserDao().findById(id));
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {

        /*User c = t.getUserDao().findById(user.getId());

        t.getUserDao().delete(c);

        if (c == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        c.setId(user.getId());
        c.setUsername(user.getUsername());
        c.setPassword(user.getPassword());
        c.setFirstname(user.getFirstname());
        c.setLastname(user.getLastname());

        User updatedUser = t.getUserDao().update(c);*/
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}