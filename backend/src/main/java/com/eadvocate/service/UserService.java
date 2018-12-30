package com.eadvocate.service;


import com.eadvocate.rest.dto.UserDto;

import java.util.List;

public interface UserService {



    UserDto addNewUser(UserDto user);

    List<UserDto> findAll();

    void delete(long id);

    UserDto findOne(String username);

    UserDto findById(Long id);
}
