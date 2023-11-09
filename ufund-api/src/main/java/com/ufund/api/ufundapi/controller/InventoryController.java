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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.AuthUtils;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.InventoryDAO;

/*
 * This class acts as the REST API Controller for the InventoryFileDAO class. 
 * Includes mapping for all necessary API calls, Get, Post, Put, and Delete.
 * @author swen-261 Section 03 Team 4d
 */
@RestController
@RequestMapping("inventory")
public class InventoryController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName()); 
    private InventoryDAO inventoryDAO;

    public InventoryController(InventoryDAO inventoryDAO){
        this.inventoryDAO = inventoryDAO;
    }

    /*
     * Gets all needs currently in the cupboard and returns as response entity with a Need Collection.
     */
    @GetMapping("")
    public ResponseEntity<Collection<Need>> getNeed(@RequestHeader String username, @RequestHeader String token) {
        LOG.info("GET /inventory");


        if(AuthUtils.isLoggedIn(token) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            Collection<Need> needs = inventoryDAO.getAllNeeds();
            return new ResponseEntity<Collection<Need>>(needs,HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /*
     * Creates and adds a new need to the cupboard, as long as no need already exists with the same name. 
     * If a new need is successfully created and added, that need is returned in the Response Entity, else returns null. 
     */
    @PostMapping("")
    public ResponseEntity<Need> createNeed(@RequestBody Need need, @RequestHeader String username, @RequestHeader String token) {
        LOG.info("POST /inventory " + need);

        if(AuthUtils.isAdmin(username) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(AuthUtils.isLoggedIn(token) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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

    /*
     * Deletes the need with the specified name from the Cupboard, as long as a need matching the given name exists. 
     * If a matching need is successfully found and deleted, that need is returned, else null is returned. 
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Need> deleteNeed(@PathVariable String name, @RequestHeader String username, @RequestHeader String token) {
        LOG.info("DELETE /inventory/" + name);

        if(AuthUtils.isAdmin(username) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(AuthUtils.isLoggedIn(token) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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

    /*
     * Gets the need from the Cupboard with the inputted name. 
     * If a need is found with a matching name, it is returned as a Response Entity, otherwise null is returned. 
     */
    @GetMapping("/{name}")
    public ResponseEntity<Need> getNeed(@PathVariable String name, @RequestHeader String username, @RequestHeader String token) {
        LOG.info("GET /inventory/" + name);

  
        if(AuthUtils.isLoggedIn(token) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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

    /*
     * Searches for needs with names that contain the given String value. 
     * If any matching needs are found, a Collection of them is returned as a Response Entity, else null is returned. 
     */
    @GetMapping("/")
    public ResponseEntity<Collection<Need>> searchNeeds(@RequestParam String name, @RequestHeader String username, @RequestHeader String token) {
        LOG.info("GET /inventory/?name="+name);

        if(AuthUtils.isLoggedIn(token) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            Collection<Need> needsArray = inventoryDAO.searchNeed(name);
            return new ResponseEntity<Collection<Need>>(needsArray,HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /*
     * Updates the specified need. Takes an already made need, and replaces the need currently in cupboard with the new, updated version. 
     * If the need is successfully updated (a need with a matching name already existed), it is returned as a Response Entity, else null is returned. 
     */
    @PutMapping("")
    public ResponseEntity<Need> updateNeed(@RequestBody Need need, @RequestHeader String username, @RequestHeader String token) {
        LOG.info("PUT /inventory " + need);
        
        if(AuthUtils.isAdmin(username) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(AuthUtils.isLoggedIn(token) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
