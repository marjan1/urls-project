package com.eadvocate.service;

import com.eadvocate.persistence.model.OppositeSideAdvocate;
import com.eadvocate.persistence.repo.OppositeSideAdvocateRepository;
import com.eadvocate.rest.dto.OppositeSideAdvocateDto;
import com.eadvocate.util.ConversionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Class with business logic implementations related to OppositeSideAdvocate.
 */
@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class OppositeSideAdvocateService {

    private OppositeSideAdvocateRepository oppositeSideAdvocateRepository;
    private ConversionUtil conversionUtil;

    /**
     * Method for adding OppositeSideAdvocate to the system.
     *
     * @param oppositeSideAdvocateDto OppositeSideAdvocateDto
     * @return added OppositeSideAdvocateDto
     */
    public OppositeSideAdvocateDto add(OppositeSideAdvocateDto oppositeSideAdvocateDto) {
        log.info("Adding new OppositeSideAdvocate with name {}", oppositeSideAdvocateDto.getName());
        OppositeSideAdvocate savedOppositeSideAdvocate = this.oppositeSideAdvocateRepository
                .save(conversionUtil.convertObjectTo(oppositeSideAdvocateDto, OppositeSideAdvocate.class));
        return conversionUtil.convertObjectTo(savedOppositeSideAdvocate, OppositeSideAdvocateDto.class);
    }

}
