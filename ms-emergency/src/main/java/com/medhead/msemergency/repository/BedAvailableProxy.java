package com.medhead.msemergency.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.medhead.msemergency.CustomProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BedAvailableProxy {
    
    @Autowired
    private CustomProperties customProperties;

	public String savedBedAvailable(Integer nearestHospitalId) {

		String baseApiUrl = customProperties.getApiUrlBedAvailable();
		String savedBedAvailable = baseApiUrl + "/savedBedAvailable/" + nearestHospitalId;

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(
				savedBedAvailable,
				HttpMethod.POST,
				null,
				String.class
			);
		
		log.debug("Saved BedAvaibility call " + response.getStatusCode().toString());
		
		return response.getBody();
	}

}
