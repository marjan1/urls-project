package com.eadvocate.service.impl;


import com.eadvocate.persistence.dao.UserRepository;
import com.eadvocate.persistence.model.User;
import com.eadvocate.rest.dto.UserDto;
import com.eadvocate.service.UserService;
import com.eadvocate.util.ConversionUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {


    private BCryptPasswordEncoder bcryptEncoder;

    private UserRepository userRepository;

    private ConversionUtil conversionUtil;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPrivileges().forEach(privilege -> authorities.add(new SimpleGrantedAuthority(privilege.getName())));
        });
        return authorities;
    }

    public List<UserDto> findAll() {
        List<User> list = new ArrayList<>();

        return userRepository.findAll().stream().map(user -> conversionUtil.convertToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto findOne(String username) {

        return conversionUtil.convertToDto(userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username")));

    }

    @Override
    public UserDto findById(Long id) {

        return conversionUtil.convertToDto(userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by id")));

    }

    @Override
    public UserDto save(UserDto user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        return conversionUtil.convertToDto(userRepository.save(newUser));
    }

    @Override
    public UserDto addNewUser(UserDto userDto) {
        userDto.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        User user = conversionUtil.convertToEntity(userDto);
        return conversionUtil.convertToDto(this.userRepository.save(user));

    }
}
