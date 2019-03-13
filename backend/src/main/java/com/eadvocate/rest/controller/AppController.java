package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.RoleDto;
import com.eadvocate.rest.dto.StatusDto;
import com.eadvocate.rest.dto.UserDto;
import com.eadvocate.service.AppService;
import com.eadvocate.service.UserService;
import com.eadvocate.validation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest controller class used for requests and responses
 * related to courts.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/app")
@AllArgsConstructor
@Log4j2
public class AppController {

    private AppService appService;
    private UserService userService;

    @GetMapping(value = "/us")
    public UserDto getAllStatuses1() {
        log.info("Request for get all statuses");
        return userService.findOne("marr@co.cv");
    }

    @GetMapping(value = "/statuses")
    public List<StatusDto> getAllStatuses() {
        log.info("Request for get all statuses");
        return appService.getAllStatuses();
    }

    @GetMapping(value = "/roles")
    public List<RoleDto> getAllRoles() {
        log.info("Request for get all roles");
        return appService.getAllRoles();
    }

    /**
     * Check if email already existing in db.
     *
     * @param email String
     * @return true or false if email exist
     */
    @PostMapping(value = "/emailcheck")
    public boolean checkEmailExistence(@RequestBody @ValidEmail String email) {
        log.info("Request for getting if email present {}", email);
        return userService.checkEmailExistence(email);
    }

    /**
     * Add new user in the system.
     *
     * @param userDto Object with data of the new user
     * @return object with added user data
     */
    @PostMapping(value = "/add")
    public UserDto addNewUser(@RequestBody @Valid UserDto userDto) {
        log.info("Request for signup new user with data {} received", userDto);
        return userService.addNewUser(userDto);
    }
}
