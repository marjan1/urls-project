package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.StatusDto;
import com.eadvocate.service.AppService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest controller class used for requests and responses
 * related to courts.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/app")
@AllArgsConstructor
@Log4j2
public class AppController {

    private AppService appService;


    @GetMapping(value = "/statuses")
    public List<StatusDto> getAllStatuses() {
        log.info("Request for adding new court with name {}");
        return appService.getAllStatuses();
    }
}
