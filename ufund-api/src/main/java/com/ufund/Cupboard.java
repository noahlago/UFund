package com.ufund;

import java.util.HashMap;

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

    public ResponseEntity<Need> getNeed(String name){
        if (this.needs.containsKey(name)){
           return new ResponseEntity<Need>(this.needs.get(name),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

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
}
