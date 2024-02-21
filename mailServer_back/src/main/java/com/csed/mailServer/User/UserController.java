package com.csed.mailServer.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "user")


public class UserController {
    UserServices userServices;

    @Autowired
    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping
    public boolean signUp(@RequestBody User user) {
            return userServices.signUp(user);
    }

    @GetMapping
    public String[] logIn(@RequestParam(value = "email") String email,
                          @RequestParam(value = "password") String password) {
        return userServices.logIn(email, password);
    }
    @GetMapping(path = "all")
    public List<User> readUsers() {
        return userServices.getUsers();
    }
}
