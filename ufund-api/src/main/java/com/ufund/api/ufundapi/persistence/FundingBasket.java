package com.ufund.api.ufundapi.persistence;

import java.util.HashMap;

import com.ufund.api.ufundapi.model.Need;

public class FundingBasket {

    private HashMap<String,Need> needs;

    public FundingBasket(){
        this.needs = new HashMap<>();
    }

    public void addNeed(Need need){
        needs.put(need.getName(), need);
    }



}
