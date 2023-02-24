package com.medhead.msbedavaibility.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.medhead.msbedavaibility.service.BedAvaibilityService;

@RestController
public class BedAvaibilityController {
    
    @Autowired
    private BedAvaibilityService bedAvaibilityService;

    @PostMapping("/savedBedAvaibility")
    public String savedBedAvaibility(@RequestBody Integer hospitalId) {
        return bedAvaibilityService.savedBedAvaibility(hospitalId);
    }
}
