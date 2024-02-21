package com.csed.mailServer.Contacts;
import com.csed.mailServer.EmailHub.EmailHubFacade;
import com.csed.mailServer.EmailHub.EmailHubServices;
import com.csed.mailServer.File.JsonFileRepository;
import com.csed.mailServer.User.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service

public class ContactsServices {
    UserServices userServices;
    EmailHubFacade emailHubFacade;
    EmailHubServices emailHubServices;
    JsonFileRepository jsonFileRepository ;

    @Autowired

    public ContactsServices(UserServices userServices, EmailHubFacade emailHubFacade, EmailHubServices emailHubServices) {
        this.userServices = userServices;
        this.emailHubFacade = emailHubFacade;
        this.emailHubServices = emailHubServices;
        this.jsonFileRepository = JsonFileRepository.getInstance();
    }

    public List<Contacts> getContacts(String email) {
       return  jsonFileRepository.readContacts(email);
    }
    public boolean saveContacts(List<Contacts> contacts , String email) {
        return jsonFileRepository.writeContacts(email, contacts);
    }
    public List<Contacts> searchContact(String email, String key){
        List<Contacts> contacts = getContacts(email);
        List<Contacts> searched = new ArrayList<>();
        for(Contacts c: contacts){
            if(c.getName().toLowerCase().contains(key.toLowerCase())) {
                searched.add(c);
            }
            for(String s: c.getEmail()){
                if(s.toLowerCase().contains(key.toLowerCase())) {
                    searched.add(c);
                }
            }
        }
        searched = searched.stream().distinct().toList();
        return searched;
    }





}
