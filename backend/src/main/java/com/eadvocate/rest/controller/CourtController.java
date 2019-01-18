package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.CourtDto;
import com.eadvocate.service.CourtService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller class used for requests and responses
 * related to courts.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/court")
@AllArgsConstructor
@Log4j2
public class CourtController {

    private CourtService courtService;

    /**
     * Add court end-point.
     *
     * @param courtDto CourtDto
     * @return added CourtDto
     */
    @PostMapping(value = "/add")
    public CourtDto add(@RequestBody @Valid CourtDto courtDto) {
        log.info("Request for adding new court with name {}", courtDto.getName());
        return courtService.add(courtDto);
    }
}
