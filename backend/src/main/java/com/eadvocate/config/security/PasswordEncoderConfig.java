package com.eadvocate.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    /**
     * Creation of BCryptPasswordEncoder bean in order to be used as dependency
     * in the project.
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
