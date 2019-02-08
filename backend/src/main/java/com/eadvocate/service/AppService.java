package com.eadvocate.service;

import com.eadvocate.persistence.repo.StatusRepository;
import com.eadvocate.rest.dto.StatusDto;
import com.eadvocate.util.ConversionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class with business logic implementations related to Court.
 */
@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class AppService {

    private StatusRepository statusRepository;
    private ConversionUtil conversionUtil;

    public List<StatusDto> getAllStatuses() {

        return statusRepository.findAll().stream()
                .map(status -> conversionUtil.convertObjectTo(status, StatusDto.class))
                .collect(Collectors.toList());
    }

}
