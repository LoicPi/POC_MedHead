package com.medhead.msemergency.repository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.medhead.msemergency.CustomProperties;
import com.medhead.msemergency.model.Hospital;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HospitalProxy {
    
    @Autowired
    private CustomProperties customProperties;

    public ArrayList<Hospital> getAvaibleHospitalsBySpecialist(String specialist) {
        String baseApiUrl = customProperties.getApiUrlHospital();
		String getHospitalssWithSpecialistUrl = baseApiUrl + "/hospitalswithspecialist/" + specialist;

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ArrayList<Hospital>> response = restTemplate.exchange(
				getHospitalssWithSpecialistUrl, 
				HttpMethod.GET, 
				null,
				new ParameterizedTypeReference<ArrayList<Hospital>>() {}
			);
		
		log.debug("Get HospitalsWithSpecialist call " + response.getStatusCode().toString());
		
		return response.getBody();
	}

}
