package com.csed.mailServer.File;

import com.csed.mailServer.Contacts.Contacts;
import com.csed.mailServer.Email.Email;

import java.util.List;

public interface JsonFileRepositoryI {
    public List<Email> readEmails(String email, String type);

    public List<Contacts> readContacts(String email);

    public boolean writeEmails(String email, String type, List<Email> emails);

    public boolean writeContacts(String email, List<Contacts> contacts);


}
