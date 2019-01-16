package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.UserDto;
import com.eadvocate.service.UserService;
import com.eadvocate.validation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Rest controller class used for requests and responses
 * related to User.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Log4j2
public class UserController {

    private UserService userService;

    /**
     * Get page of users for appropriate page number and size.
     * @param pageNumber Integer
     * @param size Integer
     * @return Page of users
     */
    @GetMapping(value = "/page")
    public Page<UserDto> listUser(@RequestParam @NotNull Integer pageNumber, @RequestParam @NotNull Integer size) {
        log.info("Request for getting all users received for page number {} with size {}", pageNumber, size);
        return userService.findAll(pageNumber, size);
    }

    /**
     * Check if email already existing in db.
     * @param email String
     * @return true or false if email exist
     */
    @PostMapping(value = "/emailcheck")
    public boolean checkEmailExistence(@RequestBody @ValidEmail String email) {
        log.info("Request for getting if email present {}", email);
        return userService.checkEmailExistence(email);
    }

    /**
     * Change the status of user found by email String to Active.
     * @param email String
     * @return Changed user
     */
    @PostMapping(value = "/activate")
    public UserDto activateUser(@RequestBody @ValidEmail String email) {
        log.info("Request for activation of user with email {} received", email);
        return userService.activateUser(email);
    }

    /**
     * Change the status of user found by email String to Deleted.
     * @param email String
     * @return Changed user
     */
    @PostMapping(value = "/deactivate")
    public UserDto deactivateUser(@RequestBody @ValidEmail String email) {
        log.info("Request for deactivation of user with email {} received", email);
        return userService.deleteUser(email);
    }

    /**
     * Add new user in the system.
     * @param userDto Object with data of the new user
     * @return object with added user data
     */
    @PostMapping(value = "/add")
    public UserDto addNewUser(@RequestBody @Valid UserDto userDto) {
        log.info("Request for signup new user with data {} received", userDto);
        return userService.addNewUser(userDto);
    }






//    @GetMapping(value = "/advocates")
//    @PreAuthorize("hasRole('ROLE_ADVOCATE')")
//    public List<UserDto> listAdvocates() {
//        return userService.findAll();
//    }
//
//    @GetMapping(value = "/apprentices")
//    @PreAuthorize("hasRole('ROLE_APPRENTICE')")
//    public List<UserDto> listApprentices() {
//        return userService.findAll();
//    }


}