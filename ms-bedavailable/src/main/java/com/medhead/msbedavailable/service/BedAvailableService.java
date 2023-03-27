package com.medhead.msbedavailable.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Service
public class BedAvailableService {

    private Random rand = new Random();

    /**
     * Function to generate a random number for bed available reservation.
     * @param hospitalId the id of hospital request
     * @return String reservation number
     */
    public String savedBedAvailable(Integer hospitalId) {

        String numberBedAvailable = String.format("%09d", rand.nextInt(1000000000));

        log.info("The number of bed reservation is " + numberBedAvailable + " for hospital number " + hospitalId);

        return numberBedAvailable;
    }

    /**
     * Function to return a random number for bed available in hospital request
     * @param hospitalId the id of hospital request
     * @return Integer number of bed available
     */
    public Integer getBedAvailableByHospitalId(Integer hospitalId) {

        Integer bedAvailableForHospital = rand.nextInt(6);

        log.info("The number of bed available is " + bedAvailableForHospital + "for hospital number " + hospitalId);

        return bedAvailableForHospital;
    }
    
}
