package com.eadvocate.service;

import com.eadvocate.persistence.model.AdvocateCompany;
import com.eadvocate.persistence.model.ClientPerson;
import com.eadvocate.persistence.repo.ClientPersonRepository;
import com.eadvocate.rest.dto.AdvocateCompanyDto;
import com.eadvocate.rest.dto.ClientPersonDto;
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
public class ClientPersonService {

    private ClientPersonRepository clientPersonRepository;
    private ConversionUtil conversionUtil;

    /**
     * Method for adding client person to the system.
     *
     * @param clientPersonDto ClientPersonDto
     * @return added ClientPersonDto
     */
    public ClientPersonDto add(ClientPersonDto clientPersonDto) {
        log.info("Adding new client person with name {}", clientPersonDto.getName());
        ClientPerson savedClientPerson = this.clientPersonRepository
                .save(conversionUtil.convertObjectTo(clientPersonDto, ClientPerson.class));
        return conversionUtil.convertObjectTo(savedClientPerson, ClientPersonDto.class);
    }

    /**
     * Method for getting page of client persons for advocate company.
     *
     * @param advocateCompanyDto AdvocateCompanyDto
     * @param pageNumber         int
     * @param size               int
     * @return Page
     */
    public Page<ClientPersonDto> getPage(AdvocateCompanyDto advocateCompanyDto, int pageNumber, int size) {
        log.info("Get page of client person with page number {} and size {}", pageNumber, size);
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("name"));

        AdvocateCompany advocateCompany = conversionUtil.convertObjectTo(advocateCompanyDto, AdvocateCompany.class);

        List<ClientPersonDto> dtos = clientPersonRepository.findAllByAdvocateCompany(advocateCompany, page).stream()
                .map(clientPerson -> conversionUtil.convertObjectTo(clientPerson, ClientPersonDto.class))
                .collect(Collectors.toList());

        Page<ClientPersonDto> clientPersonDtos = new PageImpl<>(dtos, page, size);
        return clientPersonDtos;
    }
}
