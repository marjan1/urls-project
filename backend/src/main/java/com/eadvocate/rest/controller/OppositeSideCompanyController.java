package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.OppositeSideCompanyDto;
import com.eadvocate.service.OppositeSideCompanyService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller class used for requests and responses
 * related to oppositeSideCompanys.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/osc")
@AllArgsConstructor
@Log4j2
public class OppositeSideCompanyController {

    private OppositeSideCompanyService oppositeSideCompanyService;

    /**
     * Add oppositeSideCompany end-point.
     *
     * @param oppositeSideCompanyDto OppositeSideCompanyDto
     * @return added OppositeSideCompanyDto
     */
    @PostMapping(value = "/add")
    public OppositeSideCompanyDto add(@RequestBody @Valid OppositeSideCompanyDto oppositeSideCompanyDto) {
        log.info("Request for adding new oppositeSideCompany with name {}", oppositeSideCompanyDto.getName());
        return oppositeSideCompanyService.add(oppositeSideCompanyDto);
    }
}
