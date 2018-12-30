package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.UserDto;
import com.eadvocate.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest controller class used for requests and responses
 * related to User.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@AllArgsConstructor
@Log4j2
public class UserController {

    private UserService userService;


    @GetMapping(value = "/users")
    public List<UserDto> listUser() {
        log.info("Request for getting all users received");
        return userService.findAll();
    }

    @GetMapping(value = "/size")
    public int numberOfUsers() {
        return userService.findAll().size();
    }

    /**
     * Receives a request for adding new user.
     * Validate recived data and call appropriate service implementation.
     *
     * @param userDto UserDto Dto object populated with data that is send in the request.
     * @return UserDto with data from the user that is added.
     */
    @PostMapping(value = "/signup")
    public UserDto addNewUser(@RequestBody @Valid UserDto userDto) {
        log.info("Request for adding new user with data {} received", userDto);
        return userService.addNewUser(userDto);
    }

    @GetMapping(value = "/portaladmins")
    @PreAuthorize("hasRole('ROLE_PORTAL_ADMINISTRATOR')")
    public List<UserDto> listPortalAdmins() {
        return userService.findAll();
    }

    @GetMapping(value = "/companyadmins")
    @PreAuthorize("hasRole('ADD_ADVOCATE_ACCOUNTS')")
    public List<UserDto> listCompanyAdmins() {
        return userService.findAll();
    }

    @GetMapping(value = "/advocates")
    @PreAuthorize("hasRole('ROLE_ADVOCATE')")
    public List<UserDto> listAdvocates() {
        return userService.findAll();
    }

    @GetMapping(value = "/apprentices")
    @PreAuthorize("hasRole('ROLE_APPRENTICE')")
    public List<UserDto> listApprentices() {
        return userService.findAll();
    }

    //TO DO check email  already exist !!!
}