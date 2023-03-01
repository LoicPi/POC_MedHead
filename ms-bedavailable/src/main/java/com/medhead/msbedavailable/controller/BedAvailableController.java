package com.medhead.msbedavailable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.medhead.msbedavailable.service.BedAvailableService;

@RestController
public class BedAvailableController {
    
    @Autowired
    private BedAvailableService bedAvailableService;

    /**
     * Function Post to add a bed available
     * @param hospitalId the id of the hospital concern by the bed available
     * @return the number of reservation for the bed available
     */
    @PostMapping("/savedBedAvailable/{hospitalId}")
    public String savedBedAvailable(@PathVariable(value="hospitalId") Integer hospitalId) {
        return bedAvailableService.savedBedAvailable(hospitalId);
    }
}
