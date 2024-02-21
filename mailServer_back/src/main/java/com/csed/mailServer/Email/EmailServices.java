package com.csed.mailServer.Email;
import com.csed.mailServer.EmailHub.EmailHubFacade;
import com.csed.mailServer.EmailHub.EmailHubServices;
import com.csed.mailServer.File.JsonFileRepository;
import com.csed.mailServer.User.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service

public class EmailServices {
    UserServices userServices;
    EmailHubFacade emailHubFacade;
    EmailHubServices emailHubServices;
    JsonFileRepository jsonFileRepository;

    @Autowired

    public EmailServices(UserServices userServices, EmailHubFacade emailHubFacade, EmailHubServices emailHubServices) {
        this.userServices = userServices;
        this.emailHubFacade = emailHubFacade;
        this.emailHubServices = emailHubServices;
        this.jsonFileRepository = JsonFileRepository.getInstance();
    }

    public boolean sendMail(Email email) {
        return emailHubFacade.sendMail(email);
    }

    public boolean saveAsDraft(Email email) {
        return emailHubFacade.saveAsDraft(email);
    }

    public List<Email> getMails(String email, String type) {
        if (type.equals("trash")) {
            emailHubServices.cleanUpEmails(email);
        }
        return jsonFileRepository.readEmails(email, type);
    }

    public boolean copyMail(String user , String source , String id,  String name) {
        Email email = emailHubServices.findEmailUsingId(user, source, id);
        return emailHubServices.saveMail(email,user, name);
    }

    public void deleteMail( String user, String type , String id) {
        emailHubFacade.deleteMail(user, type, id);
    }

    public List search(String folderName, String userEmail, String word) throws IOException {
        return emailHubServices.SearchBy(folderName, userEmail, word);
    }

    public List filter(String userEmail, String currentFolder,String word,String choice)throws IOException{
        return emailHubServices.filter(userEmail, currentFolder, word, choice);
    }

    public List sortByPrio(String folderName, String userEmail, String sortType)throws IOException{
        return emailHubServices.sortByPriority(folderName, userEmail, sortType);
    }

    public List<Email> changePriority(String folderName, String userEmail, String emailID, Integer priority){
        return emailHubServices.changePriority(folderName, userEmail, emailID, priority);
    }


}
