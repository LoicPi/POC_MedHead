package com.medhead.msemergency.service;

import java.util.Collections;
import java.util.List;

import com.medhead.msemergency.dto.NearestHospitalReservationTransform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medhead.msemergency.model.Emergency;
import com.medhead.msemergency.model.Hospital;
import com.medhead.msemergency.model.NearestHospitalReservation;
import com.medhead.msemergency.repository.BedAvailableProxy;
import com.medhead.msemergency.repository.EmergencyProxy;
import com.medhead.msemergency.repository.HospitalProxy;

import lombok.Data;

@Slf4j
@Data
@Service
public class EmergencyService {
    @Autowired
    private HospitalProxy hospitalProxy;

    @Autowired
    private BedAvailableProxy bedAvailableProxy;

    @Autowired
    private EmergencyProxy emergencyProxy;

    @Autowired
    private NearestHospitalReservationTransform nearestHospitalReservationTransform;
    

    /**
     * Function to get the nearest hospital and the reservation to a given emergency
     * @param emergency The emergency to get the nearest hospital and reservation
     * @return A nearestHospitalReservation object
     */
    public NearestHospitalReservation getNearestHospitalReservation(Emergency emergency) {
        
        List<Hospital> hospitals = hospitalProxy.getAvailableHospitalsBySpecialist(emergency.getSpeciality());

        Hospital nearestHospital = getNearestHospital(emergency, hospitals);

        log.info("The nearest hospital is : " + nearestHospital.getName());

        String reservation = bedAvailableProxy.savedBedAvailable(nearestHospital.getId());

        log.info("The reservation number is : " + reservation);

        NearestHospitalReservation nearestHospitalReservation = nearestHospitalReservationTransform.transformNearestHospitalToNearestHospitalReservation(nearestHospital, reservation);

        return nearestHospitalReservation;
    }

    /**
     * Function to create a NearestHospitalReservation
     * @param emergency The emergency object
     * @param hospitals The list of hospitals
     * @return The nearest hospital of the emergency
     */
    private Hospital getNearestHospital(Emergency emergency, List<Hospital> hospitals) {

        for (Hospital hospital : hospitals) {
            Integer distance = emergencyProxy.getDistanceBetweenHospitalAndEmergency(emergency.getLatitude(), emergency.getLongitude(), hospital.getLatitude(), hospital.getLongitude());
            hospital.setDistance(distance);
        }

        Collections.sort(hospitals, (Hospital hospital1, Hospital hospital2) -> hospital1.getDistance().compareTo(hospital2.getDistance()));

        Hospital nearestHospital = hospitals.get(0);

        return nearestHospital;
    }
}
