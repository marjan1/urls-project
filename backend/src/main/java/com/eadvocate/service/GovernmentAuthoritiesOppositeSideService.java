package com.eadvocate.service;

import com.eadvocate.persistence.model.GovernmentAuthoritiesOppositeSide;
import com.eadvocate.persistence.repo.GovernmentAuthoritiesOppositeSideRepository;
import com.eadvocate.rest.dto.GovernmentAuthoritiesOppositeSideDto;
import com.eadvocate.util.ConversionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Class with business logic implementations related to GovernmentAuthoritiesOppositeSide.
 */
@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class GovernmentAuthoritiesOppositeSideService {

    private GovernmentAuthoritiesOppositeSideRepository gaosRepository;
    private ConversionUtil conversionUtil;

    /**
     * Method for adding GovernmentAuthoritiesOppositeSide to the system.
     *
     * @param courtDto GovernmentAuthoritiesOppositeSideDto
     * @return added GovernmentAuthoritiesOppositeSideDto
     */
    public GovernmentAuthoritiesOppositeSideDto add(GovernmentAuthoritiesOppositeSideDto courtDto) {
        log.info("Adding new governmentAuthoritiesOppositeSide with name {}", courtDto.getName());
        GovernmentAuthoritiesOppositeSide savedGaos = this.gaosRepository
                .save(conversionUtil.convertObjectTo(courtDto, GovernmentAuthoritiesOppositeSide.class));
        return conversionUtil.convertObjectTo(savedGaos, GovernmentAuthoritiesOppositeSideDto.class);
    }

}
