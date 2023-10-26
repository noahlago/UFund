/*
 * This class represents a cupboard for an organization containing the needs that they have
 * The needs are stored within a HashMap, which can be added to, removed from, viewed, and updated
 * @author SWEN-261 Section 03 Team 4D
 */

package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;

@Component
public class InventoryFileDAO implements InventoryDAO{
    private HashMap<String,Need> needs;
    private String orgName;
    private String filename;
    private ObjectMapper objectMapper;




    public InventoryFileDAO(@Value("${needs.file}") String filename, ObjectMapper objectMapper){
        this.filename = filename;
        this.needs = new HashMap<>();
        this.objectMapper = objectMapper;
        try{
            load();
        }
        catch(IOException ioException){}
        
    }

    private boolean save() throws IOException {
        if(filename == null){
            return false;
        }
        Need[] needArray = getNeedsArray(null);

        objectMapper.writeValue(new File(filename),needArray);
        return true;
    }

    private void load() throws IOException{
        if(filename == null){
            return;
        }

        Need[] needArray = objectMapper.readValue(new File(filename),Need[].class);


        for (Need need : needArray) {
            needs.put(need.getName(), need);
        }
    }

    private Need[] getNeedsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Need> needArrayList = new ArrayList<>();

        for (Need hero : needs.values()) {
            if (containsText == null || hero.getName().contains(containsText)) {
                needArrayList.add(hero);
            }
        }

        Need[] needArray = new Need[needArrayList.size()];
        needArrayList.toArray(needArray);
        return needArray;
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
    public Need getNeed(String name) throws IOException{
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
    public Need updateNeed(Need need) throws IOException{
        if(this.needs.containsKey(need.getName())){
            this.needs.put(need.getName(),need);
            save();
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
    public Need newNeed(Need need) throws IOException{
        if(needs.containsKey(need.getName())){
            return null;
        }else{
            Need newNeed = new Need(need.getName(),need.getCost(),need.getQuantity(),need.getType());
            this.needs.put(newNeed.getName(), newNeed);
            save();
            return newNeed;
        }
    }

    /* 
     * Grants the user access to all of the needs of the organization
     * returns OK status
     */
    public Collection<Need> getAllNeeds() throws IOException {
        Collection<Need> all = this.needs.values();
        return all;
    }

    /* 
     * Deletes an existing need from the inventory
     * On success returns OK
     * On fail return CONFLICT    
     */
    public boolean deleteNeed(String name) throws IOException{
        if (!this.needs.containsKey(name)){
            return false;
        }
        else {
            this.needs.remove(name);
            save();
            return true;
        }
    }

    /*
     * Gets Collection of Needs that contain search value
     */
    public Collection<Need> searchNeed(String search) throws IOException {
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
