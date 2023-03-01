package com.medhead.mshospital.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.medhead.mshospital.service.HospitalService;
import com.medhead.mshospital.model.Hospital;

@RestController
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    /**
     * Function to get list of hospitals with beds available for the specified speciality.
     * @param specialityRequest The speciality requested.
     * @return list of hospitals with beds available for the specified speciality.
     */
    @GetMapping("/availablehospitalswithspecialist/{specialityName}")
    public ArrayList<Hospital> getAvailableHospitalsWithSpeciality(@PathVariable(value="specialityName") String specialityRequest) {
        
        return hospitalService.getAvailableHospitalsWithSpeciality(specialityRequest);
    }

}
