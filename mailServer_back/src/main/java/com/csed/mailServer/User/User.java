package com.csed.mailServer.User;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
public class User {
    private String name ;
    private String email;
    private String password;


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public User(){}


}
