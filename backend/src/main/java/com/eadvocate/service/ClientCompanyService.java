package com.eadvocate.service;

import com.eadvocate.persistence.model.AdvocateCompany;
import com.eadvocate.persistence.model.ClientCompany;
import com.eadvocate.persistence.repo.ClientCompanyRepository;
import com.eadvocate.rest.dto.AdvocateCompanyDto;
import com.eadvocate.rest.dto.ClientCompanyDto;
import com.eadvocate.util.ConversionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class with business logic implementations related to ClientCompany.
 */
@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class ClientCompanyService {

    private ClientCompanyRepository clientCompanyRepository;
    private ConversionUtil conversionUtil;

    /**
     * Method for adding client company to the system.
     *
     * @param clientCompanyDto ClientCompanyDto
     * @return added ClientCompanyDto
     */
    public ClientCompanyDto add(ClientCompanyDto clientCompanyDto) {
        log.info("Adding new client company with name {}", clientCompanyDto.getName());
        ClientCompany savedClientCompany = this.clientCompanyRepository
                .save(conversionUtil.convertObjectTo(clientCompanyDto, ClientCompany.class));
        return conversionUtil.convertObjectTo(savedClientCompany, ClientCompanyDto.class);
    }

    /**
     * Method for getting page of client companies for advocate company.
     *
     * @param advocateCompanyDto AdvocateCompanyDto
     * @param pageNumber         int
     * @param size               int
     * @return Page
     */
    public Page<ClientCompanyDto> getPage(AdvocateCompanyDto advocateCompanyDto, int pageNumber, int size) {
        log.info("Get page of client companies with page number {} and size {}", pageNumber, size);
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("name"));

        AdvocateCompany advocateCompany = conversionUtil.convertObjectTo(advocateCompanyDto, AdvocateCompany.class);

        List<ClientCompanyDto> dtos = clientCompanyRepository.findAllByAdvocateCompany(advocateCompany, page).stream()
                .map(clientCompany -> conversionUtil.convertObjectTo(clientCompany, ClientCompanyDto.class))
                .collect(Collectors.toList());

        Page<ClientCompanyDto> clientCompanyDtoPage = new PageImpl<>(dtos, page, size);
        return clientCompanyDtoPage;
    }
}
