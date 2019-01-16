package com.eadvocate.rest.controller;


import com.eadvocate.rest.dto.UserDto;
import com.eadvocate.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Log4j2
public class AuthenticationController {

    private UserService userService;

    /**
     * Receives a request for adding new user.
     * Validate recived data and call appropriate service implementation.
     *
     * @param userDto UserDto Dto object populated with data that is send in the request.
     * @return UserDto with data from the user that is added.
     */
    @PostMapping(value = "/signup")
    public UserDto signupNewUser(@RequestBody @Valid UserDto userDto) {
        log.info("Request for signup new user with data {} received", userDto);
        return userService.addNewUser(userDto);
    }


}
