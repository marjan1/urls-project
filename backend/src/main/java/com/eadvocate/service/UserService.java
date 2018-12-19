package com.eadvocate.service;


import com.eadvocate.persistence.model.User;
import com.eadvocate.rest.dto.UserDto;

import java.util.List;

public interface UserService {

    User save(UserDto user);

    List<User> findAll();

    void delete(long id);

    User findOne(String username);

    User findById(Long id);
}
