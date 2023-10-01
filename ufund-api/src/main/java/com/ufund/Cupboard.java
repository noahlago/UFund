package com.ufund;

import java.util.HashMap;

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
}
