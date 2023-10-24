package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginInfo {
    static final String STRING_FORMAT = "Key [username=%s, password=%s, token=%s]";
    @JsonProperty("username") private String username;
    @JsonProperty("password") private int password;
    @JsonProperty("token") private String token;

    public LoginInfo(@JsonProperty("username") String username, @JsonProperty("password") String password){
        this.username = username;
        this.password = password.hashCode();
        this.token = "";
    }

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

}
