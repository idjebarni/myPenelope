package com.contact.controller;

import com.contact.dao.factory.AbstractDaoFactory;
import com.contact.dao.factory.JsonDaoFactory;
import com.contact.model.Contact;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ContactController {

    private JsonDaoFactory t = (JsonDaoFactory) JsonDaoFactory.getDaoFactory(AbstractDaoFactory.FactoryType.JSON_DAO);

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public List getContact() {
        return t.getContactDao().findAll();
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
    public Contact getUserById(@PathVariable final int id) throws Exception {
        if (t.getContactDao().findById(id) == null) {
            throw new Exception("Le produit avec l'id : " + id + " est introuvable");
        }
        return t.getContactDao().findById(id);
    }

    @PostMapping(value = "/contacts")
    public ResponseEntity<Contact> ajouterContact(@Valid @RequestBody Contact contact) {

        if (t.getContactDao().create(contact) == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(t.getContactDao().create(contact).getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/contact/{id}")
    public void removeContact(@PathVariable int id) {
        t.getContactDao().delete(t.getContactDao().findById(id));
    }

    @PutMapping(value = "/contact/{id}")
    public ResponseEntity<Contact> updateContact(@Valid @RequestBody Contact contact) {

        Contact c = t.getContactDao().findById(contact.getId());

        t.getContactDao().delete(c);

        if (c == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        c.setId(contact.getId());
        c.setNom(contact.getNom());
        c.setPrenom(contact.getPrenom());
        c.setEmail(contact.getEmail());
        c.setTelephone(contact.getTelephone());
        c.setRue(contact.getRue());
        c.setVille(contact.getVille());
        c.setCodePostal(contact.getCodePostal());

        Contact updatedContact = t.getContactDao().update(c);
        return new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }

}