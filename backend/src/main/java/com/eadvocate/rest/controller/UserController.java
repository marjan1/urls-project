package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.CUserDto;
import com.eadvocate.rest.dto.UserDto;
import com.eadvocate.service.UserService;
import com.eadvocate.validation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    public Page<CUserDto> listUser(@RequestParam @NotNull Integer pageNumber,
                                  @RequestParam @NotNull Integer size,
                                  @RequestParam String filter,
                                  @RequestParam(defaultValue = "asc") String sortOrder) {
        log.info("Request for getting all users received for page number {} with size {}", pageNumber, size);
        return userService.findAllAdmins(pageNumber, size, sortOrder, filter);
    }

    /**
     * Get page of users for appropriate page number and size.
     * @param pageNumber Integer
     * @param size Integer
     * @return Page of users
     */
    @GetMapping(value = "/admins/page")
    public Page<CUserDto> listAdmins(@RequestParam @NotNull Integer pageNumber,
                                    @RequestParam @NotNull Integer size,
                                    @RequestParam String filter,
                                    @RequestParam(defaultValue = "asc") String sortOrder) {
        log.info("Request for getting all users received for page number {} with size {}", pageNumber, size);
        return userService.findAllAdmins(pageNumber, size, sortOrder, filter);
    }

    @PostMapping(value = "/edit")
    public CUserDto editUser(@RequestBody @ValidEmail CUserDto userDto) {
        log.info("Request for activation of user with email {} received", userDto.getEmail());
        return userService.updateUser(userDto);
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








//    @GetMapping(value = "/advocates")
//    @PreAuthorize("hasRole('ROLE_ADVOCATE')")
//    public List<UserDto> listAdvocates() {
//        return userService.findAllAdmins();
//    }
//
//    @GetMapping(value = "/apprentices")
//    @PreAuthorize("hasRole('ROLE_APPRENTICE')")
//    public List<UserDto> listApprentices() {
//        return userService.findAllAdmins();
//    }


}