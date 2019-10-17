package com.eadvocate.config.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for mappers used in the project.
 */
@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.typeMap(User.class, UserDto.class).addMapping(User::getRoles, UserDto::setRoleDtos);
//        modelMapper.typeMap(UserDto.class, User.class).addMapping(UserDto::getRoleDtos, User::setRoles);
//        modelMapper.typeMap(User.class, UserDto.class).addMapping(User::getStatus, UserDto::setStatusDto);
//        modelMapper.typeMap(UserDto.class, User.class).addMapping(UserDto::getStatusDto, User::setStatus);
        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper(){ return new ObjectMapper(); }
}
