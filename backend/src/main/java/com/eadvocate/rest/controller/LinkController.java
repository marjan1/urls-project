package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.AddLinkDto;
import com.eadvocate.rest.dto.LinkDto;
import com.eadvocate.service.LinkService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/link")
@AllArgsConstructor
@Log4j2
public class LinkController {

    private LinkService linkService;


    @PostMapping(value = "/add")
    public LinkDto add(@RequestBody @Valid AddLinkDto addLinkDto) {
        log.info("Request for adding new link with text {}", addLinkDto.getLink());
        return this.linkService.addLink(addLinkDto);
    }
}
