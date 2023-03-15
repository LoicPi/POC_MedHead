package com.medhead.msemergency.repository;

import java.util.List;

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

    public List<Hospital> getAvailableHospitalsBySpecialist(String speciality) {
        String baseApiUrl = customProperties.getApiUrlHospital();
		String getHospitalsWithSpecialistUrl = baseApiUrl + "/availablehospitalswithspecialist/" + speciality;

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Hospital>> response = restTemplate.exchange(
				getHospitalsWithSpecialistUrl,
				HttpMethod.GET, 
				null,
				new ParameterizedTypeReference<List<Hospital>>() {}
			);
		
		log.info("Get HospitalsWithSpecialist call " + response.getStatusCode().toString());
		
		return response.getBody();
	}

}
