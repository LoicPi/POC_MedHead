package com.medhead.msemergency.repository;

import net.minidev.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
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
        String getDistance = baseApiUrl + "?point=" + emergencyLatitude + "," + emergencyLongitude + "&point=" + hospitalLatitude + "," + hospitalLongitude + "&locale=en&instructions=false";

        RestTemplate restTemplate = new RestTemplate();

        JSONObject response = restTemplate.getForObject(getDistance, JSONObject.class );

        Double distanceDouble = JsonPath.read(response, "$['paths'][0]['distance']");

        Integer distance = distanceDouble.intValue();

		log.debug("Distance between hospital and emergency is " + distance);

        return distance;
    }
}
