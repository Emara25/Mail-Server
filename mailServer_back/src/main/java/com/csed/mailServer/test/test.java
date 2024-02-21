package com.csed.mailServer.test;

import com.csed.mailServer.Email.Email;
import com.csed.mailServer.User.User;
import com.csed.mailServer.User.UserServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {

    public static void main(String[] args) {
 /*       LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
    }
    public static boolean checkUserExistence( String email) {
        List<User> users = getUsers();
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }
    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("database/users.json");
        try {
            User[] userArray = objectMapper.readValue(file, User[].class);
            users.addAll(Arrays.asList(userArray));
            return users ;
        } catch (Exception e) {
            return null;
        }*/
        Email email = findEmailUsingId("amr", "sent", "amyohi17035203946273b858d56");
        System.out.println(email.toString());

    }


    public static Email findEmailUsingId(String user, String type , String id) {
        List<Email> mails = getMails(user, type);
        if (mails == null) {
            return null;
        }
        return mails.stream().
                filter(email -> email.getId().equals(id)).
                findFirst().
                orElse(null);
    }


    public static  boolean sendMail(Email email) {
        if (true) {
            List<Email> sentEmails = getMails(email.getFrom(), "sent");
            if (sentEmails == null) {
                sentEmails = new ArrayList<>();
            }
            List<Email> inbox = getMails(email.getTo(), "inbox");
            if (inbox == null) {
                inbox = new ArrayList<>();
            }
            sentEmails.add(email);
            inbox.add(email);
            try {
                ObjectMapper mapper = new ObjectMapper();
                File sentFile = new File("database/" + email.getFrom() + "/sent.json");
                File inboxFile = new File("database/" + email.getTo() + "/inbox.json");
                mapper.writeValue(sentFile, sentEmails);
                mapper.writeValue(inboxFile, inbox);
                return true;
            } catch (Exception e) {
                System.out.println("error");
                return false;
            }
        } else {
            System.out.println("there is no to");
            return false;
        }
    }

    public static List<Email> getMails(String email, String type) {
        File file = new File("database/" + email + "/" + type + ".json");
        List<Email> mails = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            Email[] mailsArr = mapper.readValue(file, Email[].class);
            mails.addAll(Arrays.asList(mailsArr));
            return mails;
        } catch (Exception e) {
            return null;

        }
    }

}
