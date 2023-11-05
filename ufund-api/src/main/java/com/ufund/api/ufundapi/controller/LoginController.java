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
@RequestMapping("/auth/")
public class LoginController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private Login login;

    public LoginController(Login login){
        this.login = login;
    }

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

    @PostMapping("register")
    public ResponseEntity<LoginInfo> register(@RequestHeader LoginInfo info) throws IOException{
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
