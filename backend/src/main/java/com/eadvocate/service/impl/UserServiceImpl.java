package com.eadvocate.service.impl;


import com.eadvocate.persistence.model.Role;
import com.eadvocate.persistence.model.Status;
import com.eadvocate.persistence.model.User;
import com.eadvocate.persistence.repo.RoleRepository;
import com.eadvocate.persistence.repo.StatusRepository;
import com.eadvocate.persistence.repo.UserRepository;
import com.eadvocate.rest.dto.CUserDto;
import com.eadvocate.rest.dto.UserDto;
import com.eadvocate.service.UserService;
import com.eadvocate.util.ConversionUtil;
import com.eadvocate.validation.exception.EmailExistsException;
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

    private RoleRepository roleRepository;

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
    public Page<CUserDto> findAllAdmins(int pageNumber, int size, String sortOrder, String filter) {
        log.info("Get page of users with page number {} and size {}", pageNumber, size);

        Sort sort = sortOrder.equals("asc") ? Sort.by(Sort.Order.asc("name")) :
                Sort.by(Sort.Order.desc("name"));

        Pageable page = PageRequest.of(pageNumber, size, sort);

        Role companyAdminRole = roleRepository.getByName("ROLE_ADVOCATE_COMPANY_ADMINISTRATOR");

//        ArrayList<Role> roles = new ArrayList<>();
//        roles.add(companyAdminRole);
        Page<User> result = userRepository.findAllByRoles(page, companyAdminRole);


        List<CUserDto> dtos = result.stream()
                .map(user -> conversionUtil.convertObjectTo(user, CUserDto.class))
                .collect(Collectors.toList());

        Page<CUserDto> userDtoPage = new PageImpl<>(dtos, page, result.getTotalElements());
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
        log.info("Check existence of email {}", email);
        return userRepository.findByEmail(email).isPresent();

    }

    @Override
    public boolean checkEmailExistenceForUser(Long id, String email) {
        log.info("Check existence of email {}", email);
        return userRepository.findByEmailAndIdNot(email,id).isPresent();
    }

    public List<UserDto> findAllAdmins() {
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
        if (checkEmailExistence(userDto.getEmail())) {
            log.info("Email {} already exist for sign up new user ", userDto.getEmail());
            throw new EmailExistsException();
        }
        userDto.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        return saveOrUpdateUser(userDto);

    }

    public CUserDto updateUser(CUserDto userDto) {
        log.info("Edit user with {}", userDto);
        if (userDto.getId() == null) {
            throw new RuntimeException("User ID is not present");
        }
        if (checkEmailExistenceForUser(userDto.getId(), userDto.getEmail())) {
            log.info("Email {} already exist for  user ", userDto.getEmail());
            throw new EmailExistsException();
        }
        User user = conversionUtil.convertObjectTo(userDto, User.class);
        User userFromSession = this.userRepository.getOne(userDto.getId());
        user.setPassword(userFromSession.getPassword());
        User save1 = this.userRepository.save(user);
        CUserDto userDto1 = conversionUtil.convertObjectTo(save1, CUserDto.class);
        return userDto1;
    }

    private UserDto saveOrUpdateUser(UserDto userDto) {
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
