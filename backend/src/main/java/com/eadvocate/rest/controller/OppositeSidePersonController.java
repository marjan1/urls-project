package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.OppositeSidePersonDto;
import com.eadvocate.service.OppositeSidePersonService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller class used for requests and responses
 * related to oppositeSidePersons.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/osp")
@AllArgsConstructor
@Log4j2
public class OppositeSidePersonController {

    private OppositeSidePersonService oppositeSidePersonService;

    /**
     * Add oppositeSidePerson end-point.
     *
     * @param oppositeSidePersonDto OppositeSidePersonDto
     * @return added OppositeSidePersonDto
     */
    @PostMapping(value = "/add")
    public OppositeSidePersonDto add(@RequestBody @Valid OppositeSidePersonDto oppositeSidePersonDto) {
        log.info("Request for adding new oppositeSidePerson with name {}", oppositeSidePersonDto.getName());
        return oppositeSidePersonService.add(oppositeSidePersonDto);
    }
}
