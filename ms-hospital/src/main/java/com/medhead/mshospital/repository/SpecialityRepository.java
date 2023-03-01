package com.medhead.mshospital.repository;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

import org.json.simple.*;
import org.json.simple.parser.*;
import org.springframework.stereotype.Repository;

import com.medhead.mshospital.model.Speciality;

@Repository
public class SpecialityRepository {

    /**
     * Function to get all the specialties by json file
     * @return the list of all specialties
    */
    public ArrayList<Speciality> getAllSpecialties() {

        ArrayList<Speciality> specialties = new ArrayList<Speciality>();

		JSONParser jsonParser = new JSONParser();

        try {
            Object obj = jsonParser.parse(new FileReader("ms-hospital/src/main/resources/data/Specialties.json"));

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

    /**
     * Function to return a list of random specialties
     * @return random list of specialties
     */
    public ArrayList<Speciality>givenListOfRandomArrayOfSpeciality() {
        
        Random rand = new Random();
        
        ArrayList<Speciality> givenList = getAllSpecialties();

        ArrayList<Speciality> specialitiesList = new ArrayList<Speciality>();
    
        int numberOfElements = rand.nextInt(3)+1;
    
        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(givenList.size());
            Speciality randomSpeciality = givenList.get(randomIndex);
            specialitiesList.add(randomSpeciality);
        }

        return specialitiesList;
    }
    
}
