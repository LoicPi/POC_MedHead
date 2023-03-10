package com.medhead.msemergency.repository;

import com.medhead.msemergency.CustomProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class AuthorizationProxy {

    @Autowired
    private CustomProperties customProperties;

    public Boolean isTokenavailable(String jwtToken) {

        String baseApiUrl = customProperties.getApiUrlAuthorization();
        String isTokenavailable = baseApiUrl + "/getTokenvalidated/" + jwtToken;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(
                isTokenavailable,
                HttpMethod.POST,
                request,
                Boolean.class
        );

        log.debug("Saved BedAvaibility call " + response.getStatusCode().toString());

        return response.getBody();
    }

}
