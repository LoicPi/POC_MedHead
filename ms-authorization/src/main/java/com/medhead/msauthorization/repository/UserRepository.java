package com.medhead.msauthorization.repository;

import com.medhead.msauthorization.CustomProperties;
import com.medhead.msauthorization.model.UserDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.ws.rs.NotFoundException;
import java.io.FileReader;
import java.io.File;
import java.util.Iterator;

@Repository
public class UserRepository {

    @Autowired
    private CustomProperties customProperties;

    public UserDAO findByUsername(String username) {

        UserDAO user = new UserDAO();

        JSONParser jsonParser = new JSONParser();

        try {
            String currentPath = new File(".").getAbsolutePath();

            if (currentPath.contains("/ms-authorization")) {
                currentPath = currentPath.replaceFirst("/ms-authorization", "");
            }

            Object obj = jsonParser.parse(new FileReader(currentPath + customProperties.getPathToUsersJsonFile()));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray usersJson = (JSONArray)jsonObject.get("Users");

            Iterator<Object> iterator = usersJson.iterator();

            while (iterator.hasNext()) {
                JSONObject userJson = (JSONObject)iterator.next();

                if (userJson.get("username").equals(username)) {
                    user.setUsername((String) userJson.get("username"));
                    user.setPassword((String) userJson.get("password"));
                } else {
                    throw new NotFoundException("User not exist");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
