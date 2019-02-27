package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.AdvocateCompanyDto;
import com.eadvocate.rest.dto.CompanyUserDto;
import com.eadvocate.service.AdvocateCompanyService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Rest controller class used for requests and responses
 * related to AdvocateCompany.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/advocatecompany")
@AllArgsConstructor
@Log4j2
public class AdvocateCompanyController {

    private AdvocateCompanyService advocateCompanyService;

    /**
     * Get page of advocate companies for appropriate page number and size.
     *
     * @param pageNumber Integer
     * @param size       Integer
     * @return Page of users
     */
    @GetMapping(value = "/page")
    public Page<AdvocateCompanyDto> listUser(@RequestParam @NotNull Integer pageNumber,
                                             @RequestParam @NotNull Integer size,
                                             @RequestParam String filter,
                                             @RequestParam(defaultValue = "asc") String sortOrder) {
        log.info("Request for getting all advocate companies received for page number {}, size {}, filter {} and sort order {} ",
                pageNumber, size, filter, sortOrder);
        return advocateCompanyService.getPage(pageNumber, size, sortOrder, filter);
    }


    /**
     * Change the status of advocate company to active.
     *
     * @param advocateCompanyDto AdvocateCompanyDto
     * @return Changed advocateCompanyDto
     */
    @PostMapping(value = "/activate")
    public AdvocateCompanyDto activateAdvocateCompany(@RequestBody @Valid AdvocateCompanyDto advocateCompanyDto) {
        log.info("Request for activation of advocate company with name {} received", advocateCompanyDto.getName());
        return advocateCompanyService.activateAdvocateCompany(advocateCompanyDto);
    }


    /**
     * Change the status of advocate company to deactivate.
     *
     * @param advocateCompanyDto AdvocateCompanyDto
     * @return Changed advocateCompanyDto
     */
    @PostMapping(value = "/deactivate")
    public AdvocateCompanyDto deactivateAdvocateCompany(@RequestBody @Valid AdvocateCompanyDto advocateCompanyDto) {
        log.info("Request for deactivation of advocate company with name {} received", advocateCompanyDto.getName());
        return advocateCompanyService.deactivateAdvocateCompany(advocateCompanyDto);
    }

    /**
     * Add new advocate company in the system.
     *
     * @param advocateCompanyDto Object with data of the new advocate company
     * @return object with added advocate company data
     */
    @PostMapping(value = "/add")
    public AdvocateCompanyDto addAdvocateCompany(@RequestBody @Valid AdvocateCompanyDto advocateCompanyDto) {
        log.info("Request for adding of advocate company with name {} received", advocateCompanyDto.getName());
        return advocateCompanyService.add(advocateCompanyDto);
    }

    @PostMapping(value = "/addwithadmin")
    public AdvocateCompanyDto addAdvocateCompanyWithAdmin(@RequestBody @Valid CompanyUserDto companyUserDto) {
        log.info("Request for adding of advocate company with name {} received", companyUserDto.getAdvocateCompany().getName());
        return advocateCompanyService.addCompanyWithAdmin(companyUserDto.getAdvocateCompany(), companyUserDto.getUser());
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