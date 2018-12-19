package com.eadvocate.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LoggedUser {

    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
