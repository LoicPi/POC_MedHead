package com.medhead.mshospital.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medhead.mshospital.model.Hospital;
import com.medhead.mshospital.repository.HospitalRepository;

import lombok.Data;

@Data
@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    /**
     * Function to get list of hospitals with beds available for the specified speciality.
     * @param specialityRequest The speciality requested.
     * @return The list of hospitals with beds available for the specified speciality.
     */
    public ArrayList<Hospital> getAvailableHospitalsWithSpeciality(String specialityRequest) {
        
        ArrayList<Hospital> hospitals = hospitalRepository.getAvailableHospitalsWithSpeciality(specialityRequest);

        return hospitals;
    }

}
