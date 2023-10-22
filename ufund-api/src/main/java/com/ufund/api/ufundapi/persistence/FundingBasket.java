package com.ufund.api.ufundapi.persistence;

import java.util.HashMap;

import com.ufund.api.ufundapi.model.Need;

public class FundingBasket {

    private HashMap<String,Need> needs;

    public FundingBasket(){
        this.needs = new HashMap<>();
    }

    public boolean addNeed(Need need){
        if(needs.containsKey(need.getName())){
            return false;
        }
        else{
            needs.put(need.getName(), need);
            return true;
        }
    }

    public boolean deleteNeed(Need need){
        if(needs.containsKey(need.getName())){
            needs.remove(need.getName());
            return true;
        }
        else{
            needs.put(need.getName(), need);
            return false;
        }
    }

    public HashMap<String,Need> getNeeds(){
        return this.needs;
    }



}
