package com.eadvocate.service;

import com.eadvocate.persistence.model.AdvocateCompany;
import com.eadvocate.persistence.model.Status;
import com.eadvocate.persistence.repo.AdvocateCompanyRepository;
import com.eadvocate.persistence.repo.StatusRepository;
import com.eadvocate.rest.dto.AdvocateCompanyDto;
import com.eadvocate.util.ConversionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class with business logic implementations related to Advocate company.
 */
@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class AdvocateCompanyService {

    private AdvocateCompanyRepository advocateCompanyRepository;

    private StatusRepository statusRepository;

    private ConversionUtil conversionUtil;

    /**
     * Method for adding new Advocate company.
     * @param advocateCompanyDto AdvocateCompanyDto
     * @return AdvocateCompanyDto added object
     */
    public AdvocateCompanyDto addAdvocateCompany(AdvocateCompanyDto advocateCompanyDto){
        log.info("Adding new advocate company with name {}",advocateCompanyDto.getName());
        AdvocateCompany advocateCompany = conversionUtil.convertObjectTo(advocateCompanyDto,AdvocateCompany.class);
        advocateCompany = advocateCompanyRepository.save(advocateCompany);
        return  conversionUtil.convertObjectTo(advocateCompany,AdvocateCompanyDto.class);
    }

    /**
     * Method that returns a Page with Advocate companies.
     * @param pageNumber number of the page
     * @param size of the page
     * @return the page
     */
    public Page<AdvocateCompanyDto> getPage(int pageNumber, int size) {
        log.info("Get page of advocate companies with page number {} and size {}", pageNumber, size);
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("name"));

        List<AdvocateCompanyDto> dtos = advocateCompanyRepository.findAll(page).stream()
                .map(advocateCompany -> conversionUtil.convertObjectTo(advocateCompany,AdvocateCompanyDto.class))
                .collect(Collectors.toList());

        Page<AdvocateCompanyDto> userDtoPage = new PageImpl<>(dtos, page, size);
        return userDtoPage;
    }

    /**
     * Method for activation of Advocate company if is not already activated.
     * @param advocateCompanyDto AdvocateCompanyDto
     * @return AdvocateCompanyDto
     */
    public AdvocateCompanyDto activateAdvocateCompany(AdvocateCompanyDto advocateCompanyDto){
        if(!advocateCompanyDto.getStatus().getName().equals("Active")) {
            AdvocateCompany advocateCompany = conversionUtil.convertObjectTo(advocateCompanyDto, AdvocateCompany.class);
            Status activeStatus = statusRepository.getByName("Active");
            advocateCompany.setStatus(activeStatus);
            advocateCompany = advocateCompanyRepository.save(advocateCompany);
            return conversionUtil.convertObjectTo(advocateCompany, AdvocateCompanyDto.class);
        }
        return advocateCompanyDto;
    }

    /**
     * Method for deactivation of Advocate company if is not already deactivated.
     * @param advocateCompanyDto AdvocateCompanyDto
     * @return AdvocateCompanyDto
     */
    public AdvocateCompanyDto deactivateAdvocateCompany(AdvocateCompanyDto advocateCompanyDto){
        if(!advocateCompanyDto.getStatus().getName().equals("Deleted")) {
            AdvocateCompany advocateCompany = conversionUtil.convertObjectTo(advocateCompanyDto, AdvocateCompany.class);
            Status deleteStatus = statusRepository.getByName("Deleted");
            advocateCompany.setStatus(deleteStatus);
            advocateCompany = advocateCompanyRepository.save(advocateCompany);
            return conversionUtil.convertObjectTo(advocateCompany, AdvocateCompanyDto.class);
        }
        return advocateCompanyDto;
    }



}
