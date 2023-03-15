package com.medhead.mshospital.repository;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

import com.medhead.mshospital.CustomProperties;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.medhead.mshospital.model.Speciality;

@Slf4j
@Repository
public class SpecialityRepository {


    @Autowired
    private CustomProperties customProperties;

    /**
     * Function to return a list of random specialties
     * @return random list of specialties
     */
    public List<Speciality> givenListOfRandomArrayOfSpeciality() {

        Random rand = new Random();

        List<Speciality> givenList = getAllSpecialties();

        List<Speciality> specialitiesList = new ArrayList<Speciality>();

        int numberOfElements = rand.nextInt(3)+1;

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(givenList.size());
            Speciality randomSpeciality = givenList.get(randomIndex);
            specialitiesList.add(randomSpeciality);
        }

        return specialitiesList;
    }

    /**
     * Function to get all the specialties by json file
     * @return the list of all specialties
    */
    private List<Speciality> getAllSpecialties() {

        List<Speciality> specialties = new ArrayList<Speciality>();

		JSONParser jsonParser = new JSONParser();

        try {
            String currentPath = new File(".").getAbsolutePath();

            if (currentPath.contains("/ms-hospital")) {
                currentPath = currentPath.replaceFirst("/ms-hospital", "");
            }

            Object obj = jsonParser.parse(new FileReader(currentPath + customProperties.getPathToSpecialtiesJsonFile()));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray specialtiesJson = (JSONArray)jsonObject.get("Specialties");

            Iterator<Object> iterator = specialtiesJson.iterator();

            while (iterator.hasNext()) {

                JSONObject specialityJson = (JSONObject)iterator.next();

				Speciality speciality = new Speciality();

                speciality.setId((Integer)Integer.valueOf((String)specialityJson.get("Id")));
                
                speciality.setName((String)specialityJson.get("Name"));

			    specialties.add(speciality);
            
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }

        return specialties;
    }



}
