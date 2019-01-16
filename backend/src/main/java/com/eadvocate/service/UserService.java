package com.eadvocate.service;


import com.eadvocate.rest.dto.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {



    UserDto addNewUser(UserDto user);

    List<UserDto> findAll();

    Page<UserDto> findAll(int page, int size);

    boolean checkEmailExistence(String email);

    UserDto deleteUser(String email);

    UserDto activateUser(String email);

    void delete(long id);

    UserDto findOne(String username);

    UserDto findById(Long id);
}
