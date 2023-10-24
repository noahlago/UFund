package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<String> login(@RequestBody LoginInfo info){
        LOG.info("POST /login" + info);

        try{
            String token = login.login(info);
            return new ResponseEntity<String>(token, HttpStatus.OK);
        }catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
