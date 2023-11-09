package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.AuthUtils;
import com.ufund.api.ufundapi.model.LoginInfo;
import com.ufund.api.ufundapi.persistence.Login;

@RestController

/**
 * REST Request Mapping for all user authentication functionality
 * Includes: logging in, validating session keys, registering new user accounts
 */
@RequestMapping("/auth/")
public class LoginController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    /**
     * Instance of Login class to handle file reading and storing, and creation/validation of session keys
     */
    private Login login;

    public LoginController(Login login){
        this.login = login;
    }

    /**
     * REST Mapping for login post requests
     * @param info JSON object containing properties for username, password, and session key
     * Attempts to log in the user using the given LoginInfo instance
     * If username and password are invalid, a token is not created, and a 403 status is sent
     * If login is successfuul, the LoginInfo instance is updated with the session key, and sent along with a 200 status
     */
    @PostMapping("login")
    public ResponseEntity<LoginInfo> login(@RequestBody LoginInfo info){
        LOG.info("POST /auth/login" + info);

        try{
            String token = login.login(info);
            if(token.length() == 0){
                return new ResponseEntity<LoginInfo>(info, HttpStatus.FORBIDDEN);
            }else{
                return new ResponseEntity<LoginInfo>(info, HttpStatus.OK);
            }
        }catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * REST Mapping for logout post requests
     * @param username String containing the username of the user attempting to log out
     * @param token String containing the session key of the user attempting to log out
     * Attempts to log out the user using their session key as validation
     * If the given username is not logged in, a 400 status is sent
     * If the session key is invalid, a 403 status is sent
     * If the user is successfully logged out, a 200 status is sent
     */
    @PostMapping("logout")
    public ResponseEntity<Object> logout(@RequestHeader(value="username") String username, @RequestHeader(value="token") String token){
        LOG.info("POST /auth/logout" + username + token);

        if(AuthUtils.isLoggedIn(token) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try{
            boolean result = login.logout(username);
            if(result == true){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * REST Mapping for register post requests
     * @param info JSON object containing properties for username, password, and session key
     * Attempts to register a new user using the given LoginInfo instance
     * If username is invalid (duplicate), a token is not created, and a 403 status is sent
     * If registration is successfuul, the LoginInfo instance is updated with the session key, and sent along with a 200 status
     */
    @PostMapping("register")
    public ResponseEntity<LoginInfo> register(@RequestBody LoginInfo info) throws IOException{
        LOG.info("POST /auth/register" + info);

        try{
            String token = login.register(info);
            if(token.length() == 0){
                return new ResponseEntity<LoginInfo>(info, HttpStatus.CONFLICT);
            }else{
                return new ResponseEntity<LoginInfo>(info, HttpStatus.OK);
            }
        }catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
