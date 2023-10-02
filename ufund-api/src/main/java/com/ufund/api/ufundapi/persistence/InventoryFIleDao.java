/*
 * This class represents a cupboard for an organization containing the needs that they have
 * The needs are stored within a HashMap, which can be added to, removed from, viewed, and updated
 * @author SWEN-261 Section 03 Team 4D
 */

package com.ufund.api.ufundapi.persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;

@Component
public class InventoryFIleDao implements InventoryDAO{
    private static final Logger LOG = Logger.getLogger(InventoryFIleDao.class.getName());
    private HashMap<String,Need> needs;
    private String orgName;

    private ObjectMapper objectMapper; 
    private String filename; 


    public InventoryFIleDao(@Value("${needs.file}") String filename, ObjectMapper objectMapper){
        this.filename = filename;
        this.needs = new HashMap<>();
        this.orgName = orgName;
        this.objectMapper = objectMapper;
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
    public Need getNeed(String name){
        if (this.needs.containsKey(name)){
           return this.needs.get(name);
        }
        else{
            return null;
        }
    }

    /*
     * Creates a new need with the given name, cost, quantity, and type
     * Puts new need in cupboard HashMap, with name as key
     * Returns OK status if need with same name exists
     * Returns OK status otherwise
     */
    public Need updateNeed(Need need){
        if(this.needs.containsKey(need.getName())){
            this.needs.put(need.getName(),need);
            return need;
        }
        else{
            return null; 
        }
    }

    /*
     * Creates a new need with the given name, cost, quantity, and type
     * Adds new need to cupboard HashMap
     * Returns CREATED status
     */
    public Need newNeed(Need need){
        if(needs.containsKey(need.getName())){
            return null;
        }else{
            Need newNeed = new Need(need.getName(),need.getCost(),need.getQuantity(),need.getType());
            this.needs.put(newNeed.getName(), newNeed);
            return newNeed;
        }
    }

    /* 
     * Grants the user access to all of the needs of the organization
     * returns OK status
     */
    public Collection<Need> getAllNeeds() {
        Collection<Need> all = this.needs.values();
        return all;
    }

    public boolean deleteNeed(String name) {
        if (!this.needs.containsKey(name)){
            return false;
        }
        else {
            this.needs.remove(name);
            return true;
        }
    }

    public Collection<Need> searchNeed(String search) {
        LinkedList<Need> needsList = new LinkedList<>();
        Set<String> keys = this.needs.keySet();
        for (String key: keys) {
            if (key.contains(search)){
                needsList.add(needs.get(key));
            }
        }
        return needsList;
    }
}
