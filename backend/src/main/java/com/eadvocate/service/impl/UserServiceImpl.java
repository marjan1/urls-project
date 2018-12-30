package com.eadvocate.service.impl;


import com.eadvocate.persistence.dao.UserRepository;
import com.eadvocate.persistence.model.User;
import com.eadvocate.rest.dto.UserDto;
import com.eadvocate.service.UserService;
import com.eadvocate.util.ConversionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class for implementing UserDetailsService from Spring security and
 * user service interface for user related operations.
 */
@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class UserServiceImpl implements UserDetailsService, UserService {


    private BCryptPasswordEncoder bcryptEncoder;

    private UserRepository userRepository;

    private ConversionUtil conversionUtil;

    /**
     * Method for searching user by username for database and if found populate UserDetails
     * from Spring security and return them. Method is used for authentication and authorization processes.
     *
     * @param username - String with username data.
     * @return - UserDetails from spring security.
     * @throws UsernameNotFoundException - exception thrown when user is not found by the given username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load user by username = {}", username);
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }


    public List<UserDto> findAll() {
       return userRepository.findAll().stream().map(user -> conversionUtil.convertToDto(user))
                .collect(Collectors.toList());
    }

    /**
     * Delete user by id
     *
     * @param id long
     */
    @Override
    public void delete(long id) {
        log.info("Delete user with id = {}", id);
        userRepository.deleteById(id);
    }

    /**
     * Find user by username , converto to userDto and return.
     *
     * @param username - String
     * @return UserDto
     */
    @Override
    public UserDto findOne(String username) {
        log.info("Get user by username = {}", username);
        return conversionUtil.convertToDto(userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    log.warn("User with username = {} not found", username);
                    throw new UsernameNotFoundException("User not found by username");
                }));

    }

    /**
     * Find user by id, converto to userDto and return.
     *
     * @param id Long
     * @return UserDto
     */
    @Override
    public UserDto findById(Long id) {

        return conversionUtil.convertToDto(userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User with id = {} not found", id);
                    throw new UsernameNotFoundException("User not found by id");
                }));

    }

    /**
     * Add new user to database.
     *
     * @param userDto UserDto
     * @return UserDto
     */
    @Override
    public UserDto addNewUser(UserDto userDto) {
        log.info("Add new user with {}", userDto);
        userDto.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        User user = conversionUtil.convertToEntity(userDto);
        return conversionUtil.convertToDto(this.userRepository.save(user));

    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPrivileges().forEach(privilege -> authorities.add(new SimpleGrantedAuthority(privilege.getName())));
        });
        return authorities;
    }
}
