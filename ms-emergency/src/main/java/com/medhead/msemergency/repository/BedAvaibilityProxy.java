package com.medhead.msemergency.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.medhead.msemergency.CustomProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BedAvaibilityProxy {
    
    @Autowired
    private CustomProperties customProperties;

	public String savedBedAvaibility(Integer nearestHospitalId) {

		String baseApiUrl = customProperties.getApiUrlBedAvaibility();
		String savedBedAvaibility = baseApiUrl + "/savedBedAvaibility";

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Integer> request = new HttpEntity<Integer>(nearestHospitalId);
		ResponseEntity<String> response = restTemplate.exchange(
				savedBedAvaibility, 
				HttpMethod.POST, 
				request,
				String.class
			);
		
		log.debug("Saved BedAvaibility call " + response.getStatusCode().toString());
		
		return response.getBody();
	}

}
