package com.url.config.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;


/**
 * Class that is used as entry point for unauthorized requests.
 */
@Component
@Log4j2
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    /**
     * Override method from AuthenticationEntryPoint used for handling.
     *
     * @param request       HttpServletRequest
     * @param response      HttpServletResponse
     * @param authException AuthenticationException
     * @throws IOException IOException
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        log.error("Unauthorized request received " + request.getRequestURL().toString());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}