package com.eadvocate.service;

import com.eadvocate.persistence.model.OppositeSideCompany;
import com.eadvocate.persistence.repo.OppositeSideCompanyRepository;
import com.eadvocate.rest.dto.OppositeSideCompanyDto;
import com.eadvocate.util.ConversionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Class with business logic implementations related to OppositeSideCompany.
 */
@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class OppositeSideCompanyService {

    private OppositeSideCompanyRepository oppositeSideCompanyRepository;
    private ConversionUtil conversionUtil;

    /**
     * Method for adding OppositeSideCompany to the system.
     *
     * @param oppositeSideCompanyDto OppositeSideCompanyDto
     * @return added OppositeSideCompanyDto
     */
    public OppositeSideCompanyDto add(OppositeSideCompanyDto oppositeSideCompanyDto) {
        log.info("Adding new OppositeSideCompany with name {}", oppositeSideCompanyDto.getName());
        OppositeSideCompany savedOppositeSideCompany = this.oppositeSideCompanyRepository
                .save(conversionUtil.convertObjectTo(oppositeSideCompanyDto, OppositeSideCompany.class));
        return conversionUtil.convertObjectTo(savedOppositeSideCompany, OppositeSideCompanyDto.class);
    }

}
