package com.medhead.msemergency.configuration;

import com.medhead.msemergency.repository.AuthorizationProxy;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private AuthorizationProxy authorizationProxy;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        log.info("Enter doFilterInternal JwtRequestFilter");

        final String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION.toLowerCase());

        log.debug("requestTokenHeader : " + requestTokenHeader);

        // Get authorization header and validate
        if (requestTokenHeader == null || requestTokenHeader.isEmpty() || !requestTokenHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = requestTokenHeader.split(" ")[1].trim();

        log.debug("Token received by filter is : " + token);

        // Get if token is validated
        Boolean isTokenavailable = authorizationProxy.isTokenavailable(token);

        if (!isTokenavailable) {
            chain.doFilter(request, response);
            return;
        }

        String username = extractUserFromToken(token);

        UserDetails userDetails = new User(username, "", new ArrayList<>());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails == null ? List.of() : userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private String extractUserFromToken(String token) {
        String[] chunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        JSONParser jsonParser = new JSONParser();

        String username = new String();

        try {
            JSONObject jsonPayload = (JSONObject) jsonParser.parse(payload);

            username = (String) jsonPayload.get("sub");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return username;
    }

}
