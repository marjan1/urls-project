package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.UserDto;
import com.eadvocate.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;


    @GetMapping(value = "/users")
    public List<UserDto> listUser() {
        return userService.findAll();
    }

    @GetMapping(value = "/size")
    public int numberOfUsers() {
        return userService.findAll().size();
    }

    @PostMapping (value = "/signup")
    public UserDto addNewUser(@RequestBody @Valid UserDto userDto){
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