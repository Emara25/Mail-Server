package com.csed.mailServer.Email;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Email{
    private String id;
    private int priority ;
    private String from;
    private String to;
    private String subject;
    private String body;
    private LocalDateTime date;
    private LocalDateTime deleteDate;
    private Object[] attachments;
    private Object formData;

    public Email(){}

    public Email(String from, String to, String subject, String body) {
        this.from = from;
        this.to = to;
        this.body = body;
        this.subject = subject;
    }

}

