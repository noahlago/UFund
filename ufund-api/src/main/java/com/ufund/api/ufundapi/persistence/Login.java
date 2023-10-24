package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.LoginInfo;

@Component
public class Login {
    private HashMap<String, String> loggedIn;
    private String filename;

    public Login(@Value("${keys.file}") String filename, ObjectMapper objectMapper){
        this.filename = filename;
        this.loggedIn = new HashMap<>();
    }

    public String getKey(String name) throws IOException{
        if(filename == null){
            return null;
        }else{
            return loggedIn.get(name);
        }
    }

    public String login(LoginInfo info) throws IOException{
        System.out.println("logging in");
        Random random = new Random();

        String token = "";

        for(int i = 0; i < 16; i++){
            String alphanumeric = "abcdefghijklmnopqrstuvwxyz0123456789";
            int randInt = random.nextInt(0, alphanumeric.length() - 1);
            char randChar = alphanumeric.charAt(randInt);
            token += randChar;
        }

        this.loggedIn.put(info.getUsername(), token);
        info.setToken(token);
        return token;
    }

    public boolean isLoggedIn(String name){
        return loggedIn.containsKey(name);
    }

}
