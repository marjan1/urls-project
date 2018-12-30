package com.eadvocate.config.security;

import com.eadvocate.service.impl.UserServiceImpl;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.eadvocate.util.Constants.*;

/**
 * This clasa extends BasicAuthenticationFilter from Spring security in order to override
 * the filtering process and check if the received request have appropriate Jwt token.
 * If Jwt token is present id parse it in order to take appropriate data and continue
 * withe authorization process.
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserServiceImpl userServiceImpl;

    /**
     * Class constructor for supplying needed dependencies.
     * @param authenticationManager - AuthenticationManager from Spring security
     * @param userServiceImpl - UserServiceImpl - custom implementation of UserDetailService interface.
     */
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserServiceImpl userServiceImpl) {
        super(authenticationManager);
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * Check if the received request have appropriate Jwt token.
     *  If Jwt token is present id parse it in order to take appropriate data and continue
     *  withe authorization process.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     * @param chain - FilterChain
     * @throws IOException - IOException
     * @throws ServletException - ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (token == null) return null;
        String username = Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
        UserDetails userDetails = userServiceImpl.loadUserByUsername(username);
        return username != null ?
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) : null;
    }
}