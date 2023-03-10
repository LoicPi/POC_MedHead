package com.medhead.msemergency.dto;

import com.medhead.msemergency.model.Hospital;
import com.medhead.msemergency.model.NearestHospitalReservation;
import org.springframework.stereotype.Component;

@Component
public class NearestHospitalReservationTransform {

    /**
     * Function to transform a Hospital Object to a NearestHospitalReservation Object
     * @param hospital the hospital to transform
     * @param reservation the reservation for a bedavaibility in the hospital
     * @return nearestHospitalReservation object
     */
    public NearestHospitalReservation transformNearestHospitalToNearestHospitalReservation(Hospital hospital, String reservation) {

        NearestHospitalReservation nearestHospitalReservation = new NearestHospitalReservation();

        nearestHospitalReservation.setName(hospital.getName());
        nearestHospitalReservation.setLatitude(hospital.getLatitude());
        nearestHospitalReservation.setLongitude(hospital.getLongitude());
        nearestHospitalReservation.setReservation(reservation);

        return nearestHospitalReservation;
    }
}
