package com.medhead.mshospital.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.medhead.mshospital.service.HospitalService;
import com.medhead.mshospital.model.Hospital;

@RestController
public class HospitalController {

    private final Logger logger = LoggerFactory.getLogger(HospitalController.class);

    @Autowired
    private HospitalService hospitalService;

    /**
     * Function to get list of hospitals with beds available for the specified speciality.
     * @param specialityRequest The speciality requested.
     * @return list of hospitals with beds available for the specified speciality.
     */
    @GetMapping("/availablehospitalswithspecialist/{specialityName}")
    public List<Hospital> getAvailableHospitalsWithSpeciality(@PathVariable(value="specialityName") String specialityRequest) {
        logger.info("Demande de la liste des hôpitaux pour la spécialité " + specialityRequest);

        List<Hospital> hospitalswithSpecialityRequestAndBedAvailable = hospitalService.getAvailableHospitalsWithSpeciality(specialityRequest);

        logger.info("On a retrouvé " + hospitalswithSpecialityRequestAndBedAvailable.size() + " hopital/hopitaux avec la spécialité " + specialityRequest + " et ayant des lits disponibles.");

        return hospitalswithSpecialityRequestAndBedAvailable;
    }

}
