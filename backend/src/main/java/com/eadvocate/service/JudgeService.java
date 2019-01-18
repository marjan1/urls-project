package com.eadvocate.service;

import com.eadvocate.persistence.model.Judge;
import com.eadvocate.persistence.repo.JudgeRepository;
import com.eadvocate.rest.dto.JudgeDto;
import com.eadvocate.util.ConversionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Class with business logic implementations related to Court.
 */
@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class JudgeService {

    private JudgeRepository judgeRepository;
    private ConversionUtil conversionUtil;

    /**
     * Method for adding judge to the system.
     *
     * @param judgeDto JudgeDto
     * @return added JudgeDto
     */
    public JudgeDto add(JudgeDto judgeDto) {
        log.info("Adding new judge with name {}", judgeDto.getName());
        Judge savedJudge = this.judgeRepository
                .save(conversionUtil.convertObjectTo(judgeDto, Judge.class));
        return conversionUtil.convertObjectTo(savedJudge, JudgeDto.class);
    }

}
