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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Collection<Need>> getNeed() {
        LOG.info("GET /basket");
        try{
            Collection<Need> needs = fundingBasket.getNeeds();
            return new ResponseEntity<Collection<Need>>(needs,HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("")
    public ResponseEntity<Need> createNeed(@RequestBody Need need) {
        LOG.info("POST /basket " + need);

        try{
            Need newNeed = fundingBasket.addNeed(need);
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
    public ResponseEntity<Need> deleteNeed(@PathVariable String name) {
        LOG.info("DELETE /basket/" + name);

        try {
            Need need = fundingBasket.getNeed(name);
            if(need != null){
                fundingBasket.deleteNeed(need);
                return new ResponseEntity<Need>(need,HttpStatus.OK);
            }else{                
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    
}
