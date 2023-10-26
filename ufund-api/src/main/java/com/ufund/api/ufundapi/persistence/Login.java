package com.ufund.api.ufundapi.persistence;

import java.io.File;
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
    private ObjectMapper objectMapper;

    public Login(@Value("${keys.file}") String filename, ObjectMapper objectMapper){
        this.filename = filename;
        this.loggedIn = new HashMap<>();
        this.objectMapper = objectMapper;
    }

    public String getKey(String name) throws IOException{
        if(filename == null){
            return null;
        }else{
            return loggedIn.get(name);
        }
    }

    public String login(LoginInfo info) throws IOException{
        Random random = new Random();

        String token = "";

        LoginInfo[] infos = objectMapper.readValue(new File(filename), LoginInfo[].class);

        HashMap<String,Integer> users = new HashMap<String,Integer>();

        for(int i = 0; i < infos.length; i++){
            LoginInfo user = infos[i];
            users.put(user.getUsername(), user.getPassword());
        }

        Integer expected = users.get(info.getUsername());
        String password = "" + info.getPassword();
        Integer actual = password.hashCode();
        System.out.println(expected);
        System.out.println(actual.hashCode());

        if(expected == actual.hashCode()){
            for(int i = 0; i < 16; i++){
                String alphanumeric = "abcdefghijklmnopqrstuvwxyz0123456789";
                int randInt = random.nextInt(alphanumeric.length() - 1);
                char randChar = alphanumeric.charAt(randInt);
                token += randChar;
            }  
            this.loggedIn.put(info.getUsername(), token);
        }
        
        info.setToken(token);
        return token;
    }

    public boolean logout(String username) throws IOException{
        if(loggedIn.containsKey(username)){
            this.loggedIn.remove(username);
            return true;
        }
        else{
            return false;
        }
        
    }

    public boolean authenticate(String username, String token){
        return loggedIn.get(username).equals(token);
    }

    public boolean isLoggedIn(String name){
        return loggedIn.containsKey(name);
    }

}
