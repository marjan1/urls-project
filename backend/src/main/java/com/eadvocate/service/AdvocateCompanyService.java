package com.eadvocate.service;

import com.eadvocate.persistence.model.AdvocateCompany;
import com.eadvocate.persistence.model.Status;
import com.eadvocate.persistence.model.User;
import com.eadvocate.persistence.repo.AdvocateCompanyRepository;
import com.eadvocate.persistence.repo.StatusRepository;
import com.eadvocate.persistence.repo.UserRepository;
import com.eadvocate.rest.dto.AdvocateCompanyDto;
import com.eadvocate.rest.dto.UserDto;
import com.eadvocate.util.ConversionUtil;
import com.eadvocate.validation.exception.CompanyNameExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private UserRepository userRepository;

    private ConversionUtil conversionUtil;

    private BCryptPasswordEncoder bcryptEncoder;


    public AdvocateCompanyDto addCompanyWithAdmin(AdvocateCompanyDto advocateCompanyDto,
                                                  UserDto userDto) {
        log.info("Adding new advocate company with name {}", advocateCompanyDto.getName());
        AdvocateCompany advocateCompany = advocateCompanyRepository
                .save(conversionUtil.convertObjectTo(advocateCompanyDto, AdvocateCompany.class));
        userDto.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        User user = conversionUtil.convertObjectTo(userDto, User.class);
        user.setAdvocateCompany(advocateCompany);
        userRepository.save(user);
        return conversionUtil.convertObjectTo(advocateCompany, AdvocateCompanyDto.class);
    }

    /**
     * Method for adding new Advocate company.
     *
     * @param advocateCompanyDto AdvocateCompanyDto
     * @return AdvocateCompanyDto added object
     */
    public AdvocateCompanyDto add(AdvocateCompanyDto advocateCompanyDto) {
        if (checkCompanyNameExistence(advocateCompanyDto.getName(), advocateCompanyDto.getId())) {
            log.info("Company with name {} already exist ", advocateCompanyDto.getName());
            throw new CompanyNameExistsException();
        }
        log.info("Adding new advocate company with name {}", advocateCompanyDto.getName());
        AdvocateCompany advocateCompany = advocateCompanyRepository
                .save(conversionUtil.convertObjectTo(advocateCompanyDto, AdvocateCompany.class));
        return conversionUtil.convertObjectTo(advocateCompany, AdvocateCompanyDto.class);
    }

    private boolean checkCompanyNameExistence(String name, long id) {
        log.info("Check existence of company name {}", name);
        if (advocateCompanyRepository.getByName(name).isPresent()) {
            if (advocateCompanyRepository.getByName(name).get().getId() != id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method that returns a Page with Advocate companies.
     *
     * @param pageNumber number of the page
     * @param size       of the page
     * @return the page
     */
    public Page<AdvocateCompanyDto> getPage(int pageNumber, int size, String sortOrder, String filter) {
        log.info("Get page of advocate companies with page number {} and size {}", pageNumber, size);
        Sort sort = sortOrder.equals("asc") ? Sort.by(Sort.Order.asc("name")) : Sort.by(Sort.Order.desc("name"));

        Pageable page = PageRequest.of(pageNumber, size, sort);

        Page<AdvocateCompany> result = advocateCompanyRepository.findAll(page);

        List<AdvocateCompanyDto> dtos = result.stream()
                .map(advocateCompany -> conversionUtil.convertObjectTo(advocateCompany, AdvocateCompanyDto.class))
                .collect(Collectors.toList());

        Page<AdvocateCompanyDto> userDtoPage = new PageImpl<>(dtos, page, result.getTotalElements());

        return userDtoPage;
    }

    /**
     * Method for activation of Advocate company if is not already activated.
     *
     * @param advocateCompanyDto AdvocateCompanyDto
     * @return AdvocateCompanyDto
     */
    public AdvocateCompanyDto activateAdvocateCompany(AdvocateCompanyDto advocateCompanyDto) {
        if (!advocateCompanyDto.getStatus().getName().equals("Active")) {
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
     *
     * @param advocateCompanyDto AdvocateCompanyDto
     * @return AdvocateCompanyDto
     */
    public AdvocateCompanyDto deactivateAdvocateCompany(AdvocateCompanyDto advocateCompanyDto) {
        if (!advocateCompanyDto.getStatus().getName().equals("Deleted")) {
            AdvocateCompany advocateCompany = conversionUtil.convertObjectTo(advocateCompanyDto, AdvocateCompany.class);
            Status deleteStatus = statusRepository.getByName("Deleted");
            advocateCompany.setStatus(deleteStatus);
            advocateCompany = advocateCompanyRepository.save(advocateCompany);
            return conversionUtil.convertObjectTo(advocateCompany, AdvocateCompanyDto.class);
        }
        return advocateCompanyDto;
    }


}
