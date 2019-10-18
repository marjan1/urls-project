package com.url.service;


import com.url.persistence.model.User;
import com.url.rest.dto.UserDto;

public interface UserService {


    UserDto addNewUser(UserDto user);


    boolean checkEmailExistence(String email);

   User getLoggedUser();


    UserDto findOne(String username);

}
