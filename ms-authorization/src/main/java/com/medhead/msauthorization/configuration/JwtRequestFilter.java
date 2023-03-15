package com.medhead.msauthorization.configuration;

import com.medhead.msauthorization.service.JwtUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        log.info("Enter doFilterInternal JwtRequestFilter");

        final String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION.toLowerCase());

        // Get authorization header and validate
        if (requestTokenHeader == null || requestTokenHeader.isEmpty() || !requestTokenHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = requestTokenHeader.split(" ")[1].trim();

        log.info("Token received by filter is : " + token);

        if (!jwtTokenUtil.validateToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails == null ? List.of() : userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}
