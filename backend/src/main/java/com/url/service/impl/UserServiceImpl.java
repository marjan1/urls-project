package com.url.service.impl;


import com.url.persistence.model.User;
import com.url.persistence.repo.UserRepository;
import com.url.rest.dto.UserDto;
import com.url.service.UserService;
import com.url.util.ConversionUtil;
import com.url.validation.exception.EmailExistsException;
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
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class UserServiceImpl implements UserDetailsService, UserService {


    private BCryptPasswordEncoder bcryptEncoder;

    private UserRepository userRepository;

    private ConversionUtil conversionUtil;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load user by username = {}", username);
        User user = getUserByEmail(username);

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }








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

    @Override
    public UserDto findOne(String username) throws UsernameNotFoundException {
        log.info("Get user by username = {}", username);
        return conversionUtil.convertObjectTo(userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username")), UserDto.class);

    }


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



    private UserDto saveOrUpdateUser(UserDto userDto) {
        User user = conversionUtil.convertObjectTo(userDto, User.class);
        User save1 = this.userRepository.save(user);
        UserDto userDto1 = conversionUtil.convertObjectTo(save1, UserDto.class);
        return userDto1;
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        return authorities;
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
    }

}
