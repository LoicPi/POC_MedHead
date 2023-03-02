package com.medhead.mshospital.repository;

import com.medhead.mshospital.CustomProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class BedAvailableProxy {

    @Autowired
    private CustomProperties customProperties;

    public Integer getBedAvailableByHospitalId(Integer hospitalId) {

        String baseApiUrl = customProperties.getApiUrlBedAvailable();
        String savedBedAvailable = baseApiUrl + "/getBedAvailableByHospitalId";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Integer> request = new HttpEntity<Integer>(hospitalId);
        ResponseEntity<Integer> response = restTemplate.exchange(
                savedBedAvailable,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Integer>() {}
        );

        log.debug("Saved BedAvaibility call " + response.getStatusCode().toString());

        return response.getBody();
    }
}
