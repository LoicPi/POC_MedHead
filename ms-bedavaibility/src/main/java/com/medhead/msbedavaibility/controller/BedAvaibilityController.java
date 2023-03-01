package com.medhead.msbedavaibility.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.medhead.msbedavaibility.service.BedAvaibilityService;

@RestController
public class BedAvaibilityController {
    
    @Autowired
    private BedAvaibilityService bedAvaibilityService;

    @PostMapping("/savedBedAvaibility/{hospitalId}")
    public String savedBedAvaibility(@PathVariable(value="hospitalId") Integer hospitalId) {
        return bedAvaibilityService.savedBedAvaibility(hospitalId);
    }
}
