package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;

@Component
public class FundingBasket {

    private HashMap<String,Need> needs;
    private String filename;

    public FundingBasket(@Value("${needs.file}") String filename, ObjectMapper objectMapper){
        this.filename = filename;
        this.needs = new HashMap<>();
    }

    public Need addNeed(Need need) throws IOException{
        if(needs.containsKey(need.getName())){
            return need;
        }
        else{
            needs.put(need.getName(), need);
            return null;
        }
    }

    public Need deleteNeed(Need need) throws IOException{
        if(needs.containsKey(need.getName())){
            needs.remove(need.getName());
            return need;
        }
        else{
            needs.put(need.getName(), need);
            return null;
        }
    }

    public Need getNeed(String name) throws IOException{
        return needs.get(name);
    }

    public Collection<Need> getNeeds() throws IOException{
        return this.needs.values();
    }



}
