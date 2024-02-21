package com.csed.mailServer.EmailHub;

import com.csed.mailServer.Email.Email;


public interface EmailHubRepositoryI {

    boolean saveSentMail(Email email);
     boolean saveInboxMail(Email email);
     boolean saveDraftMail(Email mail);
     boolean saveTrashMail(Email mail, String email);

}
