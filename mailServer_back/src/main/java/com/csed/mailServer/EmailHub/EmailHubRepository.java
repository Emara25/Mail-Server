package com.csed.mailServer.EmailHub;

import com.csed.mailServer.Email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class EmailHubRepository implements EmailHubRepositoryI{
    EmailHubServices emailHubServices ;
    @Autowired
    EmailHubRepository(EmailHubServices emailHubServices) {
        this.emailHubServices = emailHubServices;
    }
    public boolean saveSentMail(Email email) {
        return emailHubServices.saveMail(email, email.getFrom(), "sent");
    }
    public boolean saveInboxMail(Email email){
        return emailHubServices.saveMail(email, email.getTo(), "inbox");
    }
    public boolean saveDraftMail(Email mail){
        return emailHubServices.saveMail(mail , mail.getFrom() , "draft");
    }
    public boolean saveTrashMail(Email mail , String email) {return emailHubServices.saveMail(mail, email , "trash");}
}
