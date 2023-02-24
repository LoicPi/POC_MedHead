package com.medhead.msbedavaibility.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Service
public class BedAvaibilityService {

    /**
     * Function to generate a random number for bed avaibility reservation.
     * @param hospitalId
     * @return String reservation number
     */
    public String savedBedAvaibility(Integer hospitalId) {

        Random rand = new Random();
        
        Integer reservation = rand.nextInt(100000000);

        String numberBedAvaibility = String.valueOf(reservation);

        log.debug("The number of bed reservation is " + numberBedAvaibility + "for hospital number " + hospitalId);

        return numberBedAvaibility;
    }
    
}
