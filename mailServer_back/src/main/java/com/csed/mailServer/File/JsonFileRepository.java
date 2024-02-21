package com.csed.mailServer.File;
import com.csed.mailServer.Contacts.Contacts;
import com.csed.mailServer.Email.Email;
import java.util.List;

public class JsonFileRepository implements JsonFileRepositoryI {

    private static JsonFileRepository jsonFileRepository;
    JsonFileHandler jsonFileHandler;
    private  JsonFileRepository(){
        jsonFileHandler = JsonFileHandler.getInstance();
    }
    public static synchronized JsonFileRepository getInstance() {
        if (jsonFileRepository == null) {
            jsonFileRepository = new JsonFileRepository();
        }
        return jsonFileRepository;
    }
    @Override
    public List<Email> readEmails(String email, String type) {
        return jsonFileHandler.readData(email, type, Email[].class);
    }
    @Override
    public List<Contacts> readContacts(String email) {
        return jsonFileHandler.readData(email, "contacts", Contacts[].class);
    }
    @Override
    public boolean writeEmails(String email, String type, List<Email> emails) {
        return jsonFileHandler.writeData(email, type, emails);
    }
    @Override

    public boolean writeContacts(String email, List<Contacts> contacts) {
        return jsonFileHandler.writeData(email, "contacts", contacts);
    }


}
