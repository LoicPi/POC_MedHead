package com.medhead.msemergency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.medhead.msemergency.model.Emergency;
import com.medhead.msemergency.model.NearestHospitalReservation;
import com.medhead.msemergency.service.EmergencyService;

@RestController
public class EmergencyController {

    @Autowired
    private EmergencyService emergencyService;

    /**
     * Create a new reservation for bed hospital
     * @param emergency An object emergency
     * @return The NearestHospitalReservation objet containing the nearest hospital and the reservation
     */
    @GetMapping("/emergency")
    public NearestHospitalReservation getNearestHospitalReservation(@RequestBody Emergency emergency) {
        return emergencyService.getNearestHospitalReservation(emergency);
    }
}
