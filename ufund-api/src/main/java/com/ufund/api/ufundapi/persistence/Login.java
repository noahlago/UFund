package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Login {
    static final String STRING_FORMAT = "Key [username=%s, token=%s]";
    @JsonProperty("token") private String token;
    private boolean isAdmin;
    private String filename;
    private ObjectMapper objectMapper;
    @JsonProperty("username") private String username;

    public Login(@Value("&{basket.file}") String filename, ObjectMapper objectMapper){
        this.filename = filename;
        this.token = null;
        this.username = null;
        this.isAdmin = false;
        this.objectMapper = objectMapper;
    }

    private boolean save() throws IOException{
        if(filename == null){
            return false;
        }else{
            objectMapper.writeValue(new File(filename), this);
            return true;
        }
    }

    public String getKey() throws IOException{
        if(filename == null){
            return null;
        }else{
            this.token = objectMapper.readValue(new File(filename), String.class);
            return token;
        }
    }

    public String login(String username, String password) throws IOException{
        Random random = new Random();
        this.username = username;
        if(username == "admin"){
            this.isAdmin = true;
        }

        for(int i = 0; i < 16; i++){
            int randInt = random.nextInt(0,127);
            char randChar = (char)(randInt);
            this.token += randChar;
        }

        save();
        return token;
    }

    public boolean isAdmin(){
        return this.isAdmin;
    }

    public boolean isLoggedIn(){
        return this.token != null;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, username, token);
    }   

}
