package com.csed.mailServer.EmailHub;

import com.csed.mailServer.Email.Email;
import com.csed.mailServer.File.JsonFileHandler;
import com.csed.mailServer.File.JsonFileRepository;
import com.csed.mailServer.Utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class EmailHubServices {
     JsonFileRepository jsonFileRepository;
    ICriteria criteriaSender = new CriteriaSender();
    ICriteria criteriaSubject = new CriteriaSubject();
    PriorityQueue<Email> emailPriorityQ = new PriorityQueue<Email>();
    @Autowired

    public EmailHubServices() {
        this.jsonFileRepository = JsonFileRepository.getInstance();
    }
    public boolean saveMail(Email mail , String email, String type) {
        List<Email> mails = jsonFileRepository.readEmails(email, type);
        if (mails == null) {
            mails = new ArrayList<>();
        }
        mails.add(mail);
        return jsonFileRepository.writeEmails(email, type, mails);
        }
    public void removeMail(String email, String type, String id) {
        List<Email> mails = jsonFileRepository.readEmails(email, type);
        mails.removeIf(mail -> mail.getId().equals(id));
        jsonFileRepository.writeEmails( email, type,mails);
    }
    public void cleanUpEmails(String user) {
        LocalDateTime minute = LocalDateTime.now().minusMinutes(1);
        List<Email> mails = jsonFileRepository.readEmails(user, "trash");
        mails.removeIf(email -> email.getDeleteDate().isBefore(minute));
        jsonFileRepository.writeEmails( user, "trash", mails);
    }

    public List SearchBy(String folderName, String userEmail, String word ) throws IOException {
        List<Email> list = jsonFileRepository.readEmails(userEmail, folderName);
        ICriteria criteriaSender = new CriteriaSender();
        ICriteria criteriaSubject = new CriteriaSubject();
        ICriteria criteriaBody = new CriteriaBody();
        ICriteria criteriaDate = new CriteriaDate();
        ICriteria criteriaReceiver = new CriteriaReciever();
        ICriteria search = new SearchDif( criteriaBody,criteriaReceiver,criteriaSender, criteriaSubject, criteriaDate);
        return  search.meetCriteria( list, word);
    }

    public List filter(String userEmail, String currentFolder,String word,String choice) throws IOException {
        List list = jsonFileRepository.readEmails(userEmail, currentFolder);
        if(choice.compareTo("sender")==0  ){
            list = criteriaSender.meetCriteria( list,word );
        }
        else if(choice.compareTo("subject")==0){
            list = criteriaSubject.meetCriteria( list,word );
        }
        return list;
    }

    public List sortByPriority(String folderName, String userEmail, String sortType){
        List list = jsonFileRepository.readEmails(userEmail, folderName);
        PriorityQueue<Email> myPrQ = new PriorityQueue<>((email1, email2) ->
                Integer.compare(email1.getPriority(), email2.getPriority()));

        myPrQ.addAll(list);
        List<Email> sorted = new ArrayList<Email>();
        while (!myPrQ.isEmpty()) {
            Email email = myPrQ.poll();
            sorted.add(email);
        }

        if(sortType.equals("descending"))
            return sorted.reversed();
        else
            return sorted;
    }

    public List<Email> changePriority(String folderName, String userEmail, String emailID, Integer priority){
        List<Email> list = jsonFileRepository.readEmails(userEmail, folderName);
        for(Email e: list){
            if(e.getId().equals(emailID)) {
                e.setPriority(priority);
                break;
            }
        }
        jsonFileRepository.writeEmails( userEmail, folderName, list);
        return jsonFileRepository.readEmails(userEmail, folderName);
    }

    public  void generateEmailId(Email email,String sender, String receiver, String subject) {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        long timestamp = System.currentTimeMillis();
        String id = sender.substring(0, 2) +
                receiver.substring(0, 2) +
                subject.substring(0, 2) +
                timestamp +
                uniqueId;
        email.setId(id);
    }

    public void setDate(Email email) {
        LocalDateTime date = LocalDateTime.now();
        email.setDate(date);
    }
    public Email findEmailUsingId(String user, String type , String id) {
        List<Email> mails = jsonFileRepository.readEmails(user, type);
        if (mails == null) {
            return null;
        }
        return mails.stream().
                filter(email -> email.getId().equals(id)).
                findFirst().
                orElse(null);
    }
    }

