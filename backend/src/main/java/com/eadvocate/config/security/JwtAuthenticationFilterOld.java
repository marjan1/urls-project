package com.eadvocate.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import static com.eadvocate.util.Constants.AUTHORIZATION_HEADER;
import static com.eadvocate.util.Constants.TOKEN_PREFIX;


public class JwtAuthenticationFilterOld { //extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenProvider jwtTokenUtil;

    //@Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(AUTHORIZATION_HEADER);
        String username = null;
        String test = null;
        if ("POST".equalsIgnoreCase(req.getMethod())) {
            test = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Map<String, String> mapp = req.getReader().lines().collect(Collectors.toMap(String::new,String::new));
            String eveUSername = mapp.get("username");

        }
        System.out.println("Test = " + test);
        System.out.println("Test = " + test);
        System.out.println("Test = " + test);
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
          //      logger.error("an error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
          //      logger.warn("the token is expired and not valid anymore", e);
            } catch (SignatureException e) {
          //      logger.error("Authentication Failed. Username or Password not valid.");
            }
        } else {
         //   logger.warn("couldn't find bearer string, will ignore the header");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                //UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
           //     logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }
}