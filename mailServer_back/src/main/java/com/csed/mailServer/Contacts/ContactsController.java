package com.csed.mailServer.Contacts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "contacts")


public class ContactsController {
    ContactsServices contactsServices ;

    @Autowired
    public ContactsController(ContactsServices contactsServices) {
        this.contactsServices = contactsServices;
    }
    @GetMapping
    public List<Contacts> getContacts(@RequestParam(value = "user") String email){
        return contactsServices.getContacts(email);
    }
    @GetMapping(value = "search")
    public List<Contacts> searchContact(@RequestParam(value = "user") String email,
                                        @RequestParam(value = "key") String key){
        return contactsServices.searchContact(email, key);
    }
    @PostMapping
    public boolean submit(@RequestBody List<Contacts> contacts, @RequestParam(value = "user") String email){
       return  contactsServices.saveContacts( contacts, email );
    }

}
