package com.eadvocate.service;

import com.eadvocate.persistence.repo.RoleRepository;
import com.eadvocate.persistence.repo.StatusRepository;
import com.eadvocate.persistence.repo.UserRepository;
import com.eadvocate.rest.dto.RoleDto;
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
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public List<StatusDto> getAllStatuses() {

//        Status status1 = statusRepository.getByName("Active");
//        List<Role> roles =roleRepository.findAll();
//
//        User user = new User();
//        user.setStatus(status1);
//        user.setRoles(new HashSet<>(roles));
//        user.setEmail("q@w.com");
//        user.setPassword("$2a$10$rtYIahq9Ga2wZODMj2hGUeZCX4rNXOfcR46hJaXhvZ9KP8f7SnRTS");
//        user.setName("Peroo");
//        user.setSurname("perovski");
//        user.setAccountGroupLevel((short)1);
//        userRepository.save(user);

        return statusRepository.findAll().stream()
                .map(status -> conversionUtil.convertObjectTo(status, StatusDto.class))
                .collect(Collectors.toList());
    }

    public List<RoleDto> getAllRoles(){
        return roleRepository.findAll().stream()
                .map(role -> conversionUtil.convertObjectTo(role, RoleDto.class))
                .collect(Collectors.toList());
    }

}
