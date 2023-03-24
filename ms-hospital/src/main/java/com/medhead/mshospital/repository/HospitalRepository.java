package com.medhead.mshospital.repository;

import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.medhead.mshospital.CustomProperties;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.medhead.mshospital.model.Hospital;
import com.medhead.mshospital.model.Speciality;

@Slf4j
@Repository
public class HospitalRepository {

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private CustomProperties customProperties;

    /**
     * Function to get the list of hospitals by json file.
     * Select the hospital with beds available.
     * @return The list of hospitals with beds available.
     */
    @Cacheable("hospitals")
    public List<Hospital> getHospitals() {

        List<Hospital> hospitals = new ArrayList<Hospital>();

		JSONParser jsonParser = new JSONParser();

        try {
            String currentPath = new File("").getAbsolutePath();

            if (currentPath.contains("/ms-hospital")) {
                currentPath = currentPath.replaceFirst("/ms-hospital", "");
            }

            Object obj = jsonParser.parse(new FileReader(currentPath + customProperties.getPathToHospitalsJsonFile()));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray hospitalsJson = (JSONArray)jsonObject.get("Hospitals");

            Iterator<Object> iterator = hospitalsJson.iterator();

            while (iterator.hasNext()) {
                JSONObject hospitalJson = (JSONObject)iterator.next();

                Hospital hospital = new Hospital();

                hospital.setId((Integer)Integer.valueOf((String)hospitalJson.get("Id")));
				hospital.setName((String)hospitalJson.get("Name"));
				hospital.setAddress((String)hospitalJson.get("Address"));
				hospital.setLatitude((String)hospitalJson.get("Latitude"));
				hospital.setLongitude((String)hospitalJson.get("Longitude"));
                List<Speciality> specialtiesList = specialityRepository.givenListOfRandomArrayOfSpeciality();
                hospital.setSpecialties(specialtiesList);
				hospitals.add(hospital);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hospitals;
    }

    

}
