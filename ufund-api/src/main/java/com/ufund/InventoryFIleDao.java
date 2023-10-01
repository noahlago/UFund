/*
 * This class represents a cupboard for an organization containing the needs that they have
 * The needs are stored within a HashMap, which can be added to, removed from, viewed, and updated
 * @author SWEN-261 Section 03 Team 4D
 */

package com.ufund;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;


public class InventoryFIleDao implements InventoryDAO{
    private HashMap<String,Need> needs;
    private String orgName;

    public InventoryFIleDao(String orgName){
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
    public Need updateNeed(String name,int cost, int quantity, String type){
        if(this.needs.containsKey(name)){
            Need newNeed = new Need(name, cost, quantity, type);
            this.needs.put(name, newNeed);
            return newNeed;
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
    public boolean newNeed(String name, int cost, int quantity, String type){
        if(needs.containsKey(name)){
            return false;
        }else{
            Need newNeed = new Need(name, cost, quantity, type);
            this.needs.put(name, newNeed);
            return true;
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

    public Need searchNeed(String search) {
        Set<String> keys = this.needs.keySet();
        for (String key: keys) {
            if (key.contains(search)){
                return this.needs.get(key);
            }
        }
        return null;
    }
}
