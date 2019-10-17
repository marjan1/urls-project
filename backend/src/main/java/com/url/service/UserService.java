package com.url.service;


import com.url.rest.dto.UserDto;

public interface UserService {


    UserDto addNewUser(UserDto user);


    boolean checkEmailExistence(String email);

    boolean checkEmailExistenceForUser(Long id, String email);


    UserDto findOne(String username);

}
