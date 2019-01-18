package com.eadvocate.service;

import com.eadvocate.persistence.model.OppositeSidePerson;
import com.eadvocate.persistence.repo.OppositeSidePersonRepository;
import com.eadvocate.rest.dto.OppositeSidePersonDto;
import com.eadvocate.util.ConversionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Class with business logic implementations related to OppositeSidePerson.
 */
@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class OppositeSidePersonService {

    private OppositeSidePersonRepository oppositeSidePersonRepository;
    private ConversionUtil conversionUtil;

    /**
     * Method for adding OppositeSidePerson to the system.
     *
     * @param oppositeSidePersonDto OppositeSidePersonDto
     * @return added OppositeSidePersonDto
     */
    public OppositeSidePersonDto add(OppositeSidePersonDto oppositeSidePersonDto) {
        log.info("Adding new OppositeSidePerson with name {}", oppositeSidePersonDto.getName());
        OppositeSidePerson savedOppositeSidePerson = this.oppositeSidePersonRepository
                .save(conversionUtil.convertObjectTo(oppositeSidePersonDto, OppositeSidePerson.class));
        return conversionUtil.convertObjectTo(savedOppositeSidePerson, OppositeSidePersonDto.class);
    }

}
