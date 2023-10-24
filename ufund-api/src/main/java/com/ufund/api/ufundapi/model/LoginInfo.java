package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginInfo {
    static final String STRING_FORMAT = "Key [username=%s, password=%s]";
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;

    public LoginInfo(@JsonProperty("username") String username, @JsonProperty("password") String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, username, password);
    }   

}
