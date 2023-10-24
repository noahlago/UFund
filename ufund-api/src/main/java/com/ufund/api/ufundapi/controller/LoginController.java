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

import com.ufund.api.ufundapi.model.LoginInfo;
import com.ufund.api.ufundapi.persistence.Login;

@RestController
@RequestMapping("login")
public class LoginController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private Login login;

    public LoginController(Login login){
        this.login = login;
    }

    @PostMapping("")
    public ResponseEntity<LoginInfo> login(@RequestBody LoginInfo info){
        LOG.info("POST /login" + info);

        try{
            login.login(info);
            return new ResponseEntity<LoginInfo>(info, HttpStatus.OK);
        }catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> logout(@RequestHeader(value="username") String username, @RequestHeader(value="token") String token){
        LOG.info("POST /logout" + username + token);

        try{
            login.logout(username);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
