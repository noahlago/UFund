/*
 * This class represents a cupboard for an organization containing the needs that they have
 * The needs are stored within a HashMap, which can be added to, removed from, viewed, and updated
 * @author SWEN-261 Section 03 Team 4D
 */

package com.ufund;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Cupboard {
    private HashMap<String,Need> needs;
    private String orgName;

    Cupboard(String orgName){
        this.needs = new HashMap<>();
        this.orgName = orgName;
    }

    public HashMap<String,Need> getNeeds(){
        return this.needs;
    }

    public String getName(){
        return this.orgName;
    }

    /*
     * Returns the need in the cupboard with the given name
     * If no such need exists, returns CONFLICT status
     * Else returns OK status
     */
    public ResponseEntity<Need> getNeed(String name){
        if (this.needs.containsKey(name)){
           return new ResponseEntity<Need>(this.needs.get(name),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /*
     * Creates a new need with the given name, cost, quantity, and type
     * Puts new need in cupboard HashMap, with name as key
     * Returns OK status if need with same name exists
     * Returns OK status otherwise
     */
    public ResponseEntity<Need> updateNeed(String name,int cost, int quantity, String type){
        if(this.needs.containsKey(name)){
            Need newNeed = new Need(name, cost, quantity, type);
            this.needs.put(name, newNeed);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Need>(HttpStatus.OK); 
        }
    }

    /*
     * Creates a new need with the given name, cost, quantity, and type
     * Adds new need to cupboard HashMap
     * Returns CREATED status
     */
    public ResponseEntity<Need> newNeed(String name, int cost, int quantity, String type){
        if(needs.containsKey(name)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }else{
            Need newNeed = new Need(name, cost, quantity, type);
            this.needs.put(name, newNeed);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /* 
     * Grants the user access to all of the needs of the organization
     * returns OK status
     */
    public ResponseEntity<Collection<Need>> getAllNeeds() {
        Collection<Need> all = this.needs.values();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    public ResponseEntity<> deleteNeed(String name) {
        if (!this.needs.containsKey(name)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else {
            this.needs.remove(name);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
