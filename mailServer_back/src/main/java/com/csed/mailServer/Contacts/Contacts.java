package com.csed.mailServer.Contacts;

import lombok.Data;

import java.util.List;

@Data
public class Contacts {
    private String name;
    private List<String> email;

    public Contacts() {}

    public Contacts(String name, List<String> email) {
        this.name = name;
        this.email = email;
    }
}
