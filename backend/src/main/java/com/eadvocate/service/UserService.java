package com.eadvocate.service;


import com.eadvocate.rest.dto.CUserDto;
import com.eadvocate.rest.dto.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {


    UserDto addNewUser(UserDto user);

    CUserDto updateUser(CUserDto user);

    List<UserDto> findAllAdmins();

    Page<CUserDto> findAllAdmins(int page, int size, String sortOrder, String filter);

    boolean checkEmailExistence(String email);

    boolean checkEmailExistenceForUser(Long id,String email);

    UserDto deleteUser(String email);

    UserDto activateUser(String email);

    void delete(long id);

    UserDto findOne(String username);

    UserDto findById(Long id);
}
