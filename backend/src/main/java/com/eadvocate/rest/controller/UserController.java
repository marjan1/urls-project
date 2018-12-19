package com.eadvocate.rest.controller;

import com.eadvocate.persistence.model.User;
import com.eadvocate.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="/users", method = RequestMethod.GET)
    public List<User> listUser(){
        return userService.findAll();
    }

    @RequestMapping(value="/size", method = RequestMethod.GET)
    public int numberOfUsers(){
        return userService.findAll().size();
    }
}