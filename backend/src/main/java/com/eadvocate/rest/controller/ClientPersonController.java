package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.AdvocateCompanyDto;
import com.eadvocate.rest.dto.ClientPersonDto;
import com.eadvocate.service.ClientPersonService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Rest controller class used for requests and responses
 * related to Client persons.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/clientperson")
@AllArgsConstructor
@Log4j2
public class ClientPersonController {

    private ClientPersonService clientCompanyService;

    /**
     * Get page of a client persons for appropriate advocate company, page number and size.
     *
     * @param pageNumber Integer
     * @param size       Integer
     * @return Page of client persons
     */
    @PostMapping(value = "/page")
    public Page<ClientPersonDto> getPageWithClientPersonsForCompany(@RequestParam @NotNull Integer pageNumber,
                                                                    @RequestParam @NotNull Integer size,
                                                                    @RequestBody @Valid AdvocateCompanyDto advocateCompanyDto) {
        log.info("Request for getting client persons  received for page number {} with size {} for advocate company with name {}", pageNumber, size, advocateCompanyDto.getName());
        return clientCompanyService.getPage(advocateCompanyDto, pageNumber, size);
    }

    /**
     * Add client person end-point.
     * @param clientPersonDto ClientPersonDto
     * @return added ClientPersonDto
     */
    @PostMapping(value = "/add")
    public ClientPersonDto addClientPerson( @RequestBody @Valid ClientPersonDto clientPersonDto) {
        log.info("Request for adding new client person with name {}",clientPersonDto.getName());
        return clientCompanyService.addClientPerson(clientPersonDto);
    }
}
