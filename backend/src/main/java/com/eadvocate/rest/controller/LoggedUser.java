package com.eadvocate.rest.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Log4j2
public class LoggedUser {
    //TO DO - check if this is needed !!!
    @GetMapping("/user")
    public Principal user(Principal user) {
        log.info("IS THIS CALLED !!!");
        return user;
    }
}
