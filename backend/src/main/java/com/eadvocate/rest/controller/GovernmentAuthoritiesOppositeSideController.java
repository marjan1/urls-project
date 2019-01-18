package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.GovernmentAuthoritiesOppositeSideDto;
import com.eadvocate.service.GovernmentAuthoritiesOppositeSideService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller class used for requests and responses
 * related to GovernmentAuthoritiesOppositeSides.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/gaos")
@AllArgsConstructor
@Log4j2
public class GovernmentAuthoritiesOppositeSideController {

    private GovernmentAuthoritiesOppositeSideService governmentAuthoritiesOppositeSideService;

    /**
     * Add GovernmentAuthoritiesOppositeSide end-point.
     *
     * @param governmentAuthoritiesOppositeSideDto governmentAuthoritiesOppositeSideDto
     * @return added governmentAuthoritiesOppositeSideDto
     */
    @PostMapping(value = "/add")
    public GovernmentAuthoritiesOppositeSideDto add(@RequestBody @Valid GovernmentAuthoritiesOppositeSideDto governmentAuthoritiesOppositeSideDto) {
        log.info("Request for adding new GovernmentAuthoritiesOppositeSide with name {}", governmentAuthoritiesOppositeSideDto.getName());
        return governmentAuthoritiesOppositeSideService.add(governmentAuthoritiesOppositeSideDto);
    }
}
