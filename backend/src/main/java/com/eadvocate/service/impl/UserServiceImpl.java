package com.eadvocate.service.impl;


import com.eadvocate.persistence.model.Status;
import com.eadvocate.persistence.model.User;
import com.eadvocate.persistence.repo.StatusRepository;
import com.eadvocate.persistence.repo.UserRepository;
import com.eadvocate.rest.dto.UserDto;
import com.eadvocate.service.UserService;
import com.eadvocate.util.ConversionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
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

    private StatusRepository statusRepository;

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
        User user = getUserByEmail(username);

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    /**
     * Method for getting user by email and if exist change his status to Deleted, save and return
     *
     * @param email String param
     * @return saved user with Deleted status
     */
    @Override
    public UserDto deleteUser(String email) {
        log.info("Delete user by email = {}", email);
        User user = getUserByEmail(email);
        Status deleteStatus = statusRepository.getByName("Deleted");
        user.setStatus(deleteStatus);
        User savedUser = userRepository.save(user);

        return conversionUtil.convertObjectTo(savedUser, UserDto.class);
    }

    /**
     * Method for getting user by email and if exist change his status to Active , save and return
     *
     * @param email String param
     * @return saved user with Active status
     */
    @Override
    public UserDto activateUser(String email) {
        log.info("Activate user by email = {}", email);
        User user = getUserByEmail(email);
        Status activeStatus = statusRepository.getByName("Active");
        user.setStatus(activeStatus);
        User savedUser = userRepository.save(user);

        return conversionUtil.convertObjectTo(savedUser, UserDto.class);
    }

    /**
     * Get all users for appropriate page
     *
     * @param pageNumber number of the page
     * @param size       size of the page
     * @return page with the users
     */
    @Override
    public Page<UserDto> findAll(int pageNumber, int size) {
        log.info("Get page of users with page number {} and size {}", pageNumber, size);
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("name"));

        List<UserDto> dtos = userRepository.findAll(page).stream().map(user -> conversionUtil.convertObjectTo(user, UserDto.class))
                .collect(Collectors.toList());

        Page<UserDto> userDtoPage = new PageImpl<>(dtos, page, size);
        return userDtoPage;
    }


    /**
     * Check is email exist in the system(db) and return true or false appropriate.
     *
     * @param email String param
     * @return true or false are appropriate response
     */
    @Override
    public boolean checkEmailExistence(String email) {
    log.info("Check existence of email {}",email);
        if (userRepository.findByEmail(email).isPresent()) {
            return true;
        }
        return false;
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(user -> conversionUtil.convertObjectTo(user, UserDto.class))
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
    public UserDto findOne(String username) throws UsernameNotFoundException {
        log.info("Get user by username = {}", username);
        return conversionUtil.convertObjectTo(userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username")), UserDto.class);

    }

    /**
     * Find user by id, converto to userDto and return.
     *
     * @param id Long
     * @return UserDto
     */
    @Override
    public UserDto findById(Long id) throws UsernameNotFoundException {

        return conversionUtil.convertObjectTo(userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by id")), UserDto.class);

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
        User user = conversionUtil.convertObjectTo(userDto, User.class);
        User save1 = this.userRepository.save(user);
        UserDto userDto1 = conversionUtil.convertObjectTo(save1, UserDto.class);
        return userDto1;

    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPrivileges().forEach(privilege -> authorities.add(new SimpleGrantedAuthority(privilege.getName())));
        });
        return authorities;
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
    }

}
