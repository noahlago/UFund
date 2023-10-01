package com.ufund;

import java.util.LinkedList;
import java.util.List;

public class Cupboard {
    private List<Need> needs;
    private String orgName;

    Cupboard(String orgName){
        this.needs = new LinkedList<>();
        this.orgName = orgName;
    }

    public List<Need> getNeeds(){
        return this.needs;
    }

    public String getName(){
        return this.orgName;
    }
}
