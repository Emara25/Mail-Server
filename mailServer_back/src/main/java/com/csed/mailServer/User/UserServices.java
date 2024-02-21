package com.csed.mailServer.User;
import com.csed.mailServer.File.FileServices;
import com.csed.mailServer.File.JsonFileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.*;
@Service
public class UserServices {
    JsonFileHandler jsonFileHandler;
    FileServices fileServices;
    @Autowired
    public UserServices(FileServices fileServices) {
        this.jsonFileHandler = JsonFileHandler.getInstance();
        this.fileServices = fileServices;
    }

    public boolean signUp(User user){
        if(!checkUserExistence(user.getEmail())) {
            try {
                List<User> users = jsonFileHandler.readUsers();
                addUser(users, user);
                return jsonFileHandler.writeUsers(users);
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
    public String[] logIn(String email , String password) {
        List<User> users = jsonFileHandler.readUsers();
        User userX = users.stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        if (userX != null) {
            return fileServices.getFileNames(email);
        }
        return null;
    }

    public void addUser(List<User> users, User user) throws IOException {
        users.add(user);
        String fileName = user.getEmail();
        File file = new File(STR."database/\{fileName}");
        if (file.mkdir()) {
            File file1 = new File(STR."\{file}/sent.json");
            file1.createNewFile();
            file1 = new File(STR."\{file}/inbox.json");
            file.createNewFile();
            file1 = new File(STR."\{file}/draft.json");
            file1.createNewFile();
            file1 = new File(STR."\{file}/trash.json");
            file1.createNewFile();
            file1 = new File(STR."\{file}/contacts.json");
            file1.createNewFile();
        }
    }

    public List<User> getUsers() {
        return jsonFileHandler.readUsers();
    }
        public boolean checkUserExistence( String email) {
            List<User> users = jsonFileHandler.readUsers();
            if (users == null) {
                return false;
            }
            return users.stream().anyMatch(user -> user.getEmail().equals(email));
        }



    }

