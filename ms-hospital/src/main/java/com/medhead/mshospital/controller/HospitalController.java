package com.medhead.mshospital.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.medhead.mshospital.service.HospitalService;
import com.medhead.mshospital.model.Hospital;

@Slf4j
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
    public List<Hospital> getAvailableHospitalsWithSpeciality(@PathVariable(value="specialityName") String specialityRequest) {
        log.info("Request hospitals with the speciality name : " + specialityRequest);

        List<Hospital> hospitalswithSpecialityRequestAndBedAvailable = hospitalService.getAvailableHospitalsWithSpeciality(specialityRequest);

        log.info("We found " + hospitalswithSpecialityRequestAndBedAvailable.size() + " hospital(s) with the speciality " + specialityRequest + " and having beds available.");

        return hospitalswithSpecialityRequestAndBedAvailable;
    }

}
