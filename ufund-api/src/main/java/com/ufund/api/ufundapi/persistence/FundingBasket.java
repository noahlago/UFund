package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    private ObjectMapper objectMapper;

    public FundingBasket(@Value("${basket.file}") String filename, ObjectMapper objectMapper){
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

        for (Need need : needs.values()) {
            if (containsText == null || need.getName().contains(containsText)) {
                needArrayList.add(need);
            }
        }

        Need[] needArray = new Need[needArrayList.size()];
        needArrayList.toArray(needArray);
        return needArray;
    }

    public Need addNeed(Need need) throws IOException{
        if(needs.containsKey(need.getName())){
            return null;
        }
        else{
            needs.put(need.getName(), need);
            save();
            return need;
        }
    }

    public Need deleteNeed(Need need) throws IOException{
        if(needs.containsKey(need.getName())){
            needs.remove(need.getName());
            save();
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

    public void checkout() throws IOException{
        this.needs = new HashMap<>();
        save();
    }



}
