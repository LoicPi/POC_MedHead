package com.medhead.mshospital.service;

import java.util.ArrayList;
import java.util.List;

import com.medhead.mshospital.model.Speciality;
import com.medhead.mshospital.repository.BedAvailableProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medhead.mshospital.model.Hospital;
import com.medhead.mshospital.repository.HospitalRepository;

import lombok.Data;

@Slf4j
@Data
@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private BedAvailableProxy bedAvailableProxy;

    /**
     * Function to get list of hospitals with beds available for the specified speciality.
     * @param specialityRequest The speciality requested.
     * @return The list of hospitals with beds available for the specified speciality.
     */
    public List<Hospital> getAvailableHospitalsWithSpeciality(String specialityRequest) {
        
        List<Hospital> hospitals = hospitalRepository.getHospitals();

        log.debug("We found " + hospitals.size() + " hôpitaux.");

        List<Hospital> hospitalsWithSpeciality = getHospitalsWithSpeciality(hospitals, specialityRequest);

        log.debug("We found " + hospitalsWithSpeciality.size() + " hospital(s) with the speciality " + specialityRequest );

        List<Hospital> availableHospitalsWithSpeciality = getBedAvailableForListOfHospitals(hospitalsWithSpeciality);

        log.debug("We found " + availableHospitalsWithSpeciality.size() + " hospital(s) with the speciality request and beds available.");

        return availableHospitalsWithSpeciality;
    }

    /**
     * Function to get hospitals by json file and return a list of hospitals with beds avaibilties for the speciality requested.
     * @param specialityRequest The speciality requested.
     * @return The list of hospitals with beds available for the speciality requested.
     */
    private List<Hospital> getHospitalsWithSpeciality(List<Hospital> hospitals, String specialityRequest) {

        List<Hospital> hospitalsWithSpeciality = new ArrayList<Hospital>();

        for(Hospital hospital : hospitals) {
            for(Speciality speciality : hospital.getSpecialties()) {
                if (specialityRequest.equals(speciality.getName())) {
                    hospitalsWithSpeciality.add(hospital);
                }
            }
        }

        return hospitalsWithSpeciality;
    }

    /**
     * Function to get the bed available for each hospital object of list
     * @param hospitalsWithSpeciality List of hospital
     * @return List of hospital with the bed available parameter set.
     */
    private List<Hospital> getBedAvailableForListOfHospitals(List<Hospital> hospitalsWithSpeciality) {

        List<Hospital> availableHospitalsWithSpeciality = new ArrayList<Hospital>();

        for (Hospital hospital : hospitalsWithSpeciality) {
            Integer bedAvailable = bedAvailableProxy.getBedAvailableByHospitalId(hospital.getId());
            log.debug("HospitalId n°" + hospital.getId() + " have " + bedAvailable + " bed(s) available.");
            if (bedAvailable != 0) {
                hospital.setBedAvailable(bedAvailable);
                availableHospitalsWithSpeciality.add(hospital);
            }
        }

        return availableHospitalsWithSpeciality;
    }

}
