package com.medhead.msemergency.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;
import com.medhead.msemergency.CustomProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmergencyProxy {
    
    @Autowired
    private CustomProperties customProperties;

    public Integer getDistanceBetweenHospitalAndEmergency(String emergencyLatitude, String emergencyLongitude, String hospitalLatitude, String hospitalLongitude) {

        String baseApiUrl = customProperties.getApiUrlDistance();
        String getDistance = baseApiUrl + "?point=" + emergencyLatitude + "%2C" + emergencyLongitude + "&point=" + hospitalLatitude + "%2C" + hospitalLongitude + "&locale=en&instructions=false";

        RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(
                getDistance, 
				HttpMethod.GET, 
				null,
				new ParameterizedTypeReference<Object>() {}
			);
		
        log.debug("Get distance between hospital and emergency call " + response.getStatusCode().toString());

        Integer distance = JsonPath.read(response, "$['paths'][0]['distance']");
        
		log.debug("Distance between hospital and emergency is " + distance);

        return distance;
    }
}
