package com.eadvocate.service;

import com.eadvocate.persistence.model.Court;
import com.eadvocate.persistence.repo.CourtRepository;
import com.eadvocate.rest.dto.CourtDto;
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
public class CourtService {

    private CourtRepository courtRepository;
    private ConversionUtil conversionUtil;

    /**
     * Method for adding court to the system.
     *
     * @param courtDto CourtDto
     * @return added CourtDto
     */
    public CourtDto add(CourtDto courtDto) {
        log.info("Adding new court with name {}", courtDto.getName());
        Court savedCourt = this.courtRepository
                .save(conversionUtil.convertObjectTo(courtDto, Court.class));
        return conversionUtil.convertObjectTo(savedCourt, CourtDto.class);
    }

}
