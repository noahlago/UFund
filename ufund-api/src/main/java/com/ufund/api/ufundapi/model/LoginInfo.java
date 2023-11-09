package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginInfo {
    /**
     * Determind the String format for sending LoginInfo instances in requests to the server or to the client
     */
    static final String STRING_FORMAT = "Key [username=%s, password=%s, token=%s]";
    /**
     * Stores the username of the account being represented by this LoginInfo instance
     */
    @JsonProperty("username") private String username;
    /**
     * Stores the password of the account being represented by this LoginInfo instance
     */
    @JsonProperty("password") private int password;
    /**
     * Stores the token of the account being represented by this LoginInfo instance, if not logged in, token is an empty string
     */
    @JsonProperty("token") private String token;

    /**
     * @param username username credential represented as a String
     * @param password password credential represented as a String
     * Upon instantiation, password is automatically hashed, to avoid security risks from directly storing passwords
     * Token is instantiated as an empty String, to show that the user is not logged in
     */
    public LoginInfo(@JsonProperty("username") String username, @JsonProperty("password") String password){
        this.username = username;
        this.password = password.hashCode();
        this.token = "";
    }

    public LoginInfo(@JsonProperty("username") String username, @JsonProperty("password") int password, @JsonProperty("token") String token){
        this.username = username;
        this.password = password;
        this.token = token;
    }
    /**
     * @param token represents a session key
     * If the user has been logged out, the session key is reset to en empty String
     * If the user has been logged in, the session key is set to a 16 digit alphanumerical String
     */
    public void setToken(String token){
        this.token = token;
    }

    public String getUsername(){
        return this.username;
    }

    public int getPassword(){
        return this.password;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, username, password, token);
    }   

    // public static void main(String[] args) {
    //     String password = "some_password";
    //     System.out.println(password.hashCode());
    // }

}
