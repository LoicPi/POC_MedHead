package com.medhead.mshospital.repository;

import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.*;
import org.json.simple.parser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import com.medhead.mshospital.model.Hospital;
import com.medhead.mshospital.model.Speciality;

@Repository
public class HospitalRepository {

    @Autowired
    private SpecialityRepository specialityRepository;

    @Value("${pathToHospitalJsonFile}")
    private Resource resourceHospitalJsonFile;

    /**
     * Function to get the list of hospitals by json file.
     * Select the hospital with beds available.
     * @return The list of hospitals with beds available.
     */
    public List<Hospital> getHospitals() {

        List<Hospital> hospitals = new ArrayList<Hospital>();

		JSONParser jsonParser = new JSONParser();

        try {
            File fileHospitalJson = resourceHospitalJsonFile.getFile();

            Object obj = jsonParser.parse(new FileReader(fileHospitalJson));

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
