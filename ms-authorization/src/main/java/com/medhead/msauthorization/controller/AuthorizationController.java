package com.medhead.msauthorization.controller;

import com.medhead.msauthorization.configuration.JwtTokenUtil;
import com.medhead.msauthorization.model.UserDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthorizationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @PostMapping (value = "/login")
    public ResponseEntity<String> login (@RequestBody UserDAO userDAO) {

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDAO.getUsername(), userDAO.getPassword()));

            User authenticatedUser = (User) authenticate.getPrincipal();

            String token = jwtTokenUtil.generateToken(authenticatedUser);

            logger.info("Token is : " + token);

            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(authenticatedUser.getUsername() + " successfully authenticated");
        } catch (BadCredentialsException ex) {

            logger.info("User is : " + userDAO.getPassword());

            logger.info("User is : " + userDAO.getUsername());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/getTokenvalidated/{token}")
    public Boolean getTokenValidated (@PathVariable(value="token") String token) {
        Boolean isTokenValidated =jwtTokenUtil.validateToken(token);
        return isTokenValidated;
    }
}
