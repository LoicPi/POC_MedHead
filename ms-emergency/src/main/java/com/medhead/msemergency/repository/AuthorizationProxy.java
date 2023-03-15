package com.medhead.msemergency.repository;

import com.medhead.msemergency.CustomProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
        String isTokenavailable = baseApiUrl + "/getTokenvalidated";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(jwtToken);
        ResponseEntity<Boolean> response = restTemplate.exchange(
                isTokenavailable,
                HttpMethod.POST,
                request,
                Boolean.class
        );

        log.info("Saved BedAvaibility call " + response.getStatusCode().toString());

        return response.getBody();
    }

}
