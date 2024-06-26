package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.AuthUtils;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.FundingBasket;

@RestController
@RequestMapping("basket")
public class FundingBasketController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName()); 
    private FundingBasket fundingBasket;

    public FundingBasketController(FundingBasket fundingBasket){
        this.fundingBasket = fundingBasket;
    }

    @GetMapping("")
    public ResponseEntity<Collection<Need>> getNeed(@RequestHeader String username, @RequestHeader String token) {
        LOG.info("GET /basket");

        if(AuthUtils.isAdmin(username) == true){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(AuthUtils.isLoggedIn(token) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
      
        try{
            Collection<Need> needs = fundingBasket.getNeeds(username);
            return new ResponseEntity<Collection<Need>>(needs,HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("")
    public ResponseEntity<Need> createNeed(@RequestBody Need need, @RequestHeader String username, @RequestHeader String token) {
        LOG.info("POST /basket " + need);

        if(AuthUtils.isAdmin(username) == true){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(AuthUtils.isLoggedIn(token) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            Need newNeed = fundingBasket.addNeed(need,username);
            if (newNeed != null)
                return new ResponseEntity<Need>(newNeed,HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Need> deleteNeed(@PathVariable String name, @RequestHeader String username, @RequestHeader String token) {
        LOG.info("DELETE /basket/" + name);

        if(AuthUtils.isAdmin(username) == true){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(AuthUtils.isLoggedIn(token) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Need need = fundingBasket.getNeed(name, username);
            if(need != null){
                fundingBasket.deleteNeed(need,username);
                return new ResponseEntity<Need>(need,HttpStatus.OK);
            }else{                
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestHeader String username, @RequestHeader String token){

        if(AuthUtils.isAdmin(username) == true){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(AuthUtils.isLoggedIn(token) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        try{
            fundingBasket.checkout(username);
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/match")
    public ResponseEntity<String> switchMatching(@RequestHeader String username, @RequestHeader String token){
        LOG.info("POST /basket/match");
        if(AuthUtils.isAdmin(username) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(AuthUtils.isLoggedIn(token) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        fundingBasket.switchMatching();
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/match")
    public ResponseEntity<Boolean> getMatching(@RequestHeader String username, @RequestHeader String token){
        LOG.info("POST /basket/match");
        if(AuthUtils.isLoggedIn(token) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean matching = fundingBasket.getMatching();

        return new ResponseEntity<Boolean>(matching, HttpStatus.OK);
    }
}
