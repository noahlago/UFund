package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.LoginInfo;

@Component
public class Login {
    private String token;
    private boolean isAdmin;
    private String username;
    private String filename;
    private ObjectMapper objectMapper;

    public Login(@Value("${keys.file}") String filename, ObjectMapper objectMapper){
        this.filename = filename;
        this.token = "";
        this.username = null;
        this.isAdmin = false;
        this.objectMapper = objectMapper;
    }

    private boolean save(LoginInfo info) throws IOException{
        LoginInfo[] data = new LoginInfo[1];
        data[0] = info;
        if(filename == null){
            return false;
        }else{
            objectMapper.writeValue(new File(filename), data);
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

    public String login(LoginInfo info) throws IOException{
        System.out.println("logging in");
        this.username = info.getUsername();
        Random random = new Random();
        if(username == "admin"){
            this.isAdmin = true;
        }

        for(int i = 0; i < 16; i++){
            String alphanumeric = "abcdefghijklmnopqrstuvwxyz0123456789";
            int randInt = random.nextInt(0, alphanumeric.length() - 1);
            char randChar = alphanumeric.charAt(randInt);
            this.token += randChar;
        }

        save(info);
        return token;
    }

    public boolean isAdmin(){
        return this.isAdmin;
    }

    public boolean isLoggedIn(){
        return this.token != null;
    }

    public String getUsername(){
        return this.username;
    }
}
