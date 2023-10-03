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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.InventoryDAO;


@RestController
@RequestMapping("inventory")
public class InventoryController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName()); 
    private InventoryDAO inventoryDAO;

    InventoryController(InventoryDAO inventoryDAO){
        this.inventoryDAO = inventoryDAO;
    }

    @GetMapping("")
    public ResponseEntity<Collection<Need>> getNeed() {
        LOG.info("GET /inventory");
        try{
            Collection<Need> needs = inventoryDAO.getAllNeeds();
            return new ResponseEntity<Collection<Need>>(needs,HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("")
    public ResponseEntity<Need> createNeed(@RequestBody Need need) {
        LOG.info("POST /inventory " + need);

        try{
            Need newNeed = inventoryDAO.newNeed(need);
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
        LOG.info("DELETE /inventory/" + name);

        try {
            Need need = inventoryDAO.getNeed(name);
            if(need != null){
                inventoryDAO.deleteNeed(name);
                return new ResponseEntity<Need>(need,HttpStatus.OK);
            }else{                
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{name}")
    public ResponseEntity<Need> getNeed(@PathVariable String name) {
        LOG.info("GET /inventory/" + name);
        try {
            Need need = inventoryDAO.getNeed(name);
            if (need != null)
                return new ResponseEntity<Need>(need,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Collection<Need>> searchNeeds(@RequestParam String name) {
        LOG.info("GET /inventory/?name="+name);

        try{
            Collection<Need> needsArray = inventoryDAO.searchNeed(name);
            return new ResponseEntity<Collection<Need>>(needsArray,HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @PutMapping("")
    public ResponseEntity<Need> updateNeed(@RequestBody Need need) {
        LOG.info("PUT /inventory " + need);
        try{
            Need newNeed = inventoryDAO.updateNeed(need);
            if (newNeed != null)
                return new ResponseEntity<Need>(newNeed,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }





    
}
