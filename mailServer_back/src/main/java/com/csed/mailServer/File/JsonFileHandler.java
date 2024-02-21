package com.csed.mailServer.File;
import com.csed.mailServer.User.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonFileHandler implements FileHandlerStrategy {


    private static JsonFileHandler jsonFileHandler;
    private JsonFileHandler(){}

    public static synchronized JsonFileHandler getInstance() {
        if (jsonFileHandler == null) {
            jsonFileHandler = new JsonFileHandler();
        }
        return jsonFileHandler;
    }
    @Override
    public <T> List<T> readData(String email, String type, Class<T[]> clazz) {
        File file = new File(STR."database/\{email}/\{type}.json");
        List<T> data = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            T[] dataArray = mapper.readValue(file, clazz);
            data.addAll(Arrays.asList(dataArray));
            return data;
        } catch (Exception e) {
            return null;
        }
    }
    public List<User> readUsers() {
        List<User> users = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        File file = new File("database/users.json");
        try {
            User[] userArray = mapper.readValue(file, User[].class);
            users.addAll(Arrays.asList(userArray));
            return users ;
        } catch (Exception e) {
            return null;
        }
    }
    public <T> boolean writeData(String email, String type, List<T> data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            File file = new File(STR."database/\{email}/\{type}.json");
            mapper.writeValue(file, data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean writeUsers(List<User> users) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("database/users.json");
            mapper.writeValue(file, users);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
