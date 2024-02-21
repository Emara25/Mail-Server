package com.csed.mailServer.EmailHub;
import com.csed.mailServer.Email.Email;
import com.csed.mailServer.File.JsonFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
@Service
public class EmailHubFacade {
    EmailHubServices emailHubServices;
     JsonFileRepository jsonFileRepository ;
    EmailHubRepository emailHubRepository;
    @Autowired
    public EmailHubFacade(EmailHubServices emailHubServices , EmailHubRepository emailHubRepository) {
        this.emailHubServices = emailHubServices;
        this.emailHubRepository = emailHubRepository;
        this.jsonFileRepository = JsonFileRepository.getInstance();
    }


    public boolean sendMail(Email email) {
        emailHubServices.generateEmailId(email, email.getFrom(), email.getTo(), email.getBody());
        emailHubServices.setDate(email);
        return emailHubRepository.saveSentMail(email) && emailHubRepository.saveInboxMail(email);
    }

    public boolean saveAsDraft(Email email) {
        emailHubServices.generateEmailId(email, email.getFrom(), email.getFrom(), email.getBody());
        emailHubServices.setDate(email);
        return emailHubRepository.saveDraftMail(email);
    }

    public void deleteMail(String user, String type, String id) {
        Email email = emailHubServices.findEmailUsingId(user, type, id);
        email.setDeleteDate(LocalDateTime.now());
        emailHubServices.removeMail(user, type, id);
        emailHubRepository.saveTrashMail(email, user);
    }
}
