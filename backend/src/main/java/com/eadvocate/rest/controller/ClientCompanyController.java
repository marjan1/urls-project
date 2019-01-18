package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.AdvocateCompanyDto;
import com.eadvocate.rest.dto.ClientCompanyDto;
import com.eadvocate.service.ClientCompanyService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Rest controller class used for requests and responses
 * related to Client companies.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/clientcompany")
@AllArgsConstructor
@Log4j2
public class ClientCompanyController {

    private ClientCompanyService clientCompanyService;

    /**
     * Get page of a client companies  for appropriate advocate company, page number and size.
     *
     * @param pageNumber Integer
     * @param size       Integer
     * @return Page of client persons
     */
    @PostMapping(value = "/page")
    public Page<ClientCompanyDto> getPageWithClientCompaniesForCompany(@RequestParam @NotNull Integer pageNumber,
                                                                         @RequestParam @NotNull Integer size,
                                                                         @RequestBody @Valid AdvocateCompanyDto advocateCompanyDto) {
        log.info("Request for getting client companies  received for page number {} with size {} for advocate company with name {}", pageNumber, size, advocateCompanyDto.getName());
        return clientCompanyService.getPage(advocateCompanyDto, pageNumber, size);
    }

    /**
     * Add client company end-point.
     * @param clientCompanyDto ClientPersonDto
     * @return added ClientPersonDto
     */
    @PostMapping(value = "/add")
    public ClientCompanyDto addClientPerson( @RequestBody @Valid ClientCompanyDto clientCompanyDto) {
        log.info("Request for adding new client person with name {}",clientCompanyDto.getName());
        return clientCompanyService.add(clientCompanyDto);
    }
}
