package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.LoginInfo;

@Component
public class Login {
    /**
     * Stores authentication info for all currently logged in users, with usernames as keys and session keys as values
     */
    private HashMap<String, String> loggedIn;
    /**
     * Stores account info for all users, with usernames as keys and (hashed) passwords as values
     */
    private HashMap<String, LoginInfo> accounts;
    /**
     * Stores the filename where all authentication info is stored (keys.json)
     */
    private String filename;
    /**
     * Used to write to and read from keys.json, converting between HashMaps and Json objects
     */
    private ObjectMapper objectMapper;

    /**
     * @param filename keys.json by default
     * @param objectMapper
     * @throws StreamReadException if objectMapper fails to parse file
     * @throws DatabindException if data fails to be converted to a HashMap
     * @throws IOException if any issues arise with filename
     */
    public Login(@Value("${keys.file}") String filename, ObjectMapper objectMapper) throws StreamReadException, DatabindException, IOException{
        this.filename = filename;
        this.loggedIn = new HashMap<>();

        this.objectMapper = objectMapper;

        LoginInfo[] infos = objectMapper.readValue(new File(filename), LoginInfo[].class);

        accounts = new HashMap<String,LoginInfo>();

        for(int i = 0; i < infos.length; i++){
            LoginInfo user = infos[i];
            accounts.put(user.getUsername(), user);
        }
    }

    /**
     * @param username
     * @return the key associated with the given name, if the user is logged in
     * @throws IOException
     */
    public String getKey(String username) throws IOException{
        if(filename == null){
            return null;
        }else{
            return loggedIn.get(username);
        }
    }

    /**
     * Attempts to log in using the credentials provided by the LoginInfo instance
     * Reads from the keys.json file and compares the entered usernname and password to the credential sets in the file
     * If the provided credentials match any in the file, the user is logged in and a session key is created and returned
     * the loggedIn HashMap is also updated accordingly, with the username and newly created session key 
     * 
     * @param info contains the username and password entered by the user attempting to log in
     * @return 16 digit alphanumerical session key if the user is successfully logged in, empty String if login is unsuccessful
     * @throws IOException
     */
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

    /**
     * Logs out the user, using their username, by removing them from the HashMap of logged in users
     * @param username of the user logging out
     * @return true if the user was successfully logged out
     * @throws IOException
     */
    public boolean logout(String username) throws IOException{
        if(loggedIn.containsKey(username)){
            this.loggedIn.remove(username);
            return true;
        }
        else{
            return false;
        }
        
    }

    /**
     * Attempts to create a new user account using the provided credentials
     * Does not allow creation of duplicate usernames
     * @param info contains the username and password entered by the user attempting to log in
     * @return a session key if the account is successfully created, empty string otherwise
     * @throws IOException
     */
    public String register(LoginInfo info) throws IOException{
        if(filename == null || accounts.containsKey(info.getUsername())){
            return "";
        }else{
            for(LoginInfo account : accounts.values()){
                account.revertPassword();
            }
            accounts.put(info.getUsername(), info);
            objectMapper.writeValue(new File(filename), accounts.values());

            return login(info);
        }
    }

    /**
     * @param username 
     * @param token
     * @return true if the given username and token are a value set in the LoggedIn HashMap, false otherwise
     */
    public boolean authenticate(String username, String token){
        return loggedIn.get(username).equals(token);
    }

    /**
     * @param name
     * @return true of the given username is found in the HashMap of users that are currently logged in
     */
    public boolean isLoggedIn(String name){
        return loggedIn.containsKey(name);
    }

}
