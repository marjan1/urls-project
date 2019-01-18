package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.OppositeSideAdvocateDto;
import com.eadvocate.service.OppositeSideAdvocateService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller class used for requests and responses
 * related to oppositeSideAdvocates.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/osa")
@AllArgsConstructor
@Log4j2
public class OppositeSideAdvocateController {

    private OppositeSideAdvocateService oppositeSideAdvocateService;

    /**
     * Add oppositeSideAdvocate end-point.
     *
     * @param oppositeSideAdvocateDto OppositeSideAdvocateDto
     * @return added OppositeSideAdvocateDto
     */
    @PostMapping(value = "/add")
    public OppositeSideAdvocateDto add(@RequestBody @Valid OppositeSideAdvocateDto oppositeSideAdvocateDto) {
        log.info("Request for adding new oppositeSideAdvocate with name {}", oppositeSideAdvocateDto.getName());
        return oppositeSideAdvocateService.add(oppositeSideAdvocateDto);
    }
}
