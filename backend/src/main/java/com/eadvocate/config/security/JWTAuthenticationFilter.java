package com.eadvocate.config.security;

import com.eadvocate.rest.dto.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.eadvocate.util.Constants.*;

/**
 * JWTAuthenticationFilter that implements authentication process when request on "/login"
 * url is received. The class extents UsernamePasswordAuthenticationFilter from Spring Security
 * and overrides the attempt authentication and success authentication.
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private ObjectMapper objectMapper;

    /**
     * Constructor for initializing the class.
     *
     * @param authenticationManager - from Spring Security used for authentication purposes
     * @param objectMapper          - for transferring data from HttpServletRequest to LoginUser object.
     */
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }

    /**
     * When reguest is send on "/login" for authentication this method is called to get
     * data from the request and call authentication manager to attempt authentication.
     *
     * @param request  - HttpServletRequest
     * @param response 0 HttpServletResponse
     * @return - Spring security Authentication
     * @throws AuthenticationException - Spring security exception
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginUser loginUser = this.objectMapper.readValue(request.getInputStream(), LoginUser.class);
            return this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * After successful authentication Jwt token need to be created and set to response
     * in order to be used in future requests.
     *
     * @param request    HttpServletRequest
     * @param response   HttpServletResponse
     * @param chain      FilterChain
     * @param authResult Authentication - result form authentication, from which username is taken
     *                   and used in Jwt token creation.
     * @throws IOException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {

        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();
        Collection<GrantedAuthority> grantedAuthorities = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getAuthorities();
        Map<String,Object> grantedAuthorityMap = new HashMap<>();
        grantedAuthorityMap.put("roles", grantedAuthorities);
                //grantedAuthorities.stream().forEach()

//        .collect(
//                Collectors.toMap("dd", GrantedAuthority::getAuthority));
        String token = Jwts
                .builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .addClaims(grantedAuthorityMap)
                .compact();
        String bearerToken = TOKEN_PREFIX + token;
        response.getWriter().write(bearerToken);
        response.addHeader(AUTHORIZATION_HEADER, bearerToken);
    }
}