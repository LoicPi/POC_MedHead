package com.medhead.mshospital.repository;

import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.*;
import org.json.simple.parser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.medhead.mshospital.model.Hospital;
import com.medhead.mshospital.model.Speciality;

@Repository
public class HospitalRepository {

    @Autowired
    private SpecialityRepository specialityRepository;
    
    /**
     * Function to get hospitals by json file and return a list of hospitals with beds avaibilties for the speciality requested.
     * @param specialityRequest The speciality requested.
     * @return The list of hospitals with beds avaibilties for the speciality requested.
     */
    public ArrayList<Hospital> getAvailableHospitalsWithSpeciality(String specialityRequest) {

        ArrayList<Hospital> availableHospitalsWithSpeciality = new ArrayList<Hospital>();

        ArrayList<Hospital> hospitals = getHospitals();
        
        for(Hospital hospital : hospitals) {
            for(Speciality speciality : hospital.getSpecialties()) {
                if (specialityRequest.equals(speciality.getName())) {
                    availableHospitalsWithSpeciality.add(hospital);
                }
            }
        }

        return availableHospitalsWithSpeciality;
    }

    /**
     * Function to get the list of hospitals by json file.
     * Select the hospital with beds available.
     * @return The list of hospitals with beds avaibilties.
     */
    public ArrayList<Hospital> getHospitals() {

        ArrayList<Hospital> hospitals = new ArrayList<Hospital>();

		JSONParser jsonParser = new JSONParser();

        try {
            Object obj = jsonParser.parse(new FileReader("ms-hospital/src/main/resources/data/Hospitals.json"));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray hospitalsJson = (JSONArray)jsonObject.get("Hospitals");

            Iterator<Object> iterator = hospitalsJson.iterator();

            while (iterator.hasNext()) {
                JSONObject hospitalJson = (JSONObject)iterator.next();

                Integer bedAvailable = Integer.valueOf((String)hospitalJson.get("BedAvailable"));

                if (bedAvailable != 0) {

				    Hospital hospital = new Hospital();

				    hospital.setId((Integer)Integer.valueOf((String)hospitalJson.get("Id")));
				    hospital.setName((String)hospitalJson.get("Name"));
				    hospital.setAddress((String)hospitalJson.get("Address"));
				    hospital.setLatitude((String)hospitalJson.get("Latitude"));
				    hospital.setLongitude((String)hospitalJson.get("Longitude"));
				    hospital.setBedAvailable((Integer)Integer.valueOf((String)hospitalJson.get("bedAvailable")));
                    ArrayList<Speciality> specialtiesList = specialityRepository.givenListOfRandomArrayOfSpeciality();
                    hospital.setSpecialties(specialtiesList);
				    hospitals.add(hospital);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hospitals;
    }

    

}
