package com.eadvocate.config.security;

import com.eadvocate.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Main configuration class for configuring security.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Creation of AuthenticationManager in order to be able to be used as dependency in
     * the project.
     * @return - AuthenticationManager from Spring security.
     * @throws Exception - Basic Java Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Setting custom implementation for user search (from db) in authentication and
     * authorization process.
     * @param auth - AuthenticationManagerBuilder from Spring Security.
     * @throws Exception - Basic Java Exception
     */
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("mar@d.com").password("jan").roles("ADVOCATE_COMPANY_ADMINISTRATOR");
        auth.userDetailsService(userServiceImpl)
                .passwordEncoder(encoder());
    }

    /**
     * Main security configuration for http requests.
     * @param http - HttpSecurity from Spring security.
     * @throws Exception - Basic Java Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/login","/api/signup*", "/h2-console/**",
                        "/h2-console*", "/api/app/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), objectMapper))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userServiceImpl));

        http.headers().frameOptions().disable();
    }

    /**
     * Creation of BCryptPasswordEncoder bean in order to be used as dependency
     * in the project.
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

//    public static void main(String[] args) {
//        BCryptPasswordEncoder bCryptPasswordEncoder =  new BCryptPasswordEncoder();
//
//       String encoded =  bCryptPasswordEncoder.encode("1");
//        System.out.println("encoded "+encoded);
//    }


}
