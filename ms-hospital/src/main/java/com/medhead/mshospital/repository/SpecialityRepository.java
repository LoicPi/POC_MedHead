package com.medhead.mshospital.repository;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

import org.json.simple.*;
import org.json.simple.parser.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import com.medhead.mshospital.model.Speciality;

@Repository
public class SpecialityRepository {

    @Value("${pathToSpecialtiesJsonFile}")
    private Resource resourceSpecialtiesJsonFile;

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
            File fileSpecialtiesJson = resourceSpecialtiesJsonFile.getFile();

            Object obj = jsonParser.parse(new FileReader(fileSpecialtiesJson));

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
