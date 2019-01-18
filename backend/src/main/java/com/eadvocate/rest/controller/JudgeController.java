package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.JudgeDto;
import com.eadvocate.service.JudgeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller class used for requests and responses
 * related to judges.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/judge")
@AllArgsConstructor
@Log4j2
public class JudgeController {

    private JudgeService judgeService;

    /**
     * Add judge end-point.
     *
     * @param judgeDto JudgeDto
     * @return added JudgeDto
     */
    @PostMapping(value = "/add")
    public JudgeDto add(@RequestBody @Valid JudgeDto judgeDto) {
        log.info("Request for adding new judge with name {}", judgeDto.getName());
        return judgeService.add(judgeDto);
    }
}
