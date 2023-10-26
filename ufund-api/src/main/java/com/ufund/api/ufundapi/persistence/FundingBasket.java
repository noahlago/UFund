package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;

@Component
public class FundingBasket {
    private HashMap<String,ArrayList<Need>> users;
    private String filename;
    private ObjectMapper objectMapper;

    public FundingBasket(@Value("${basket.file}") String filename, ObjectMapper objectMapper){
        this.filename = filename;
        this.objectMapper = objectMapper;
        this.users = new HashMap<>();

        try{
            load();
        }
        catch(IOException ioException){}
    }

    private boolean save() throws IOException {
        if(filename == null){
            return false;
        }
        objectMapper.writeValue(new File(filename),users);
        return true;
    }

    private void load() throws IOException{
        if(filename == null){
            return;
        }

        this.users = objectMapper.readValue(new File(filename),HashMap.class);


    }

    // private Need[] getNeedsArray(String containsText) { // if containsText == null, no filter
    //     ArrayList<Need> needArrayList = new ArrayList<>();

    //     for (Need need : needs.values()) {
    //         if (containsText == null || need.getName().contains(containsText)) {
    //             needArrayList.add(need);
    //         }
    //     }

    //     Need[] needArray = new Need[needArrayList.size()];
    //     needArrayList.toArray(needArray);
    //     return needArray;
    // }

    public Need addNeed(Need need, String username) throws IOException{
        if(users.containsKey(username)){
            if(users.get(username).contains(need)){
                return null;
            }
            else{
                users.get(username).add(need);
                save();
                return need;
            }
        }
        else{
            users.put(username, new ArrayList<>());
            users.get(username).add(need);
            save();
            return need;
        }
    }

    public Need deleteNeed(Need need, String username) throws IOException{
        if(users.get(username).contains(need)){
            users.get(username).remove(need);
            save();
            return need;
        }
        else{
            return null;
        }
    }

    public Need getNeed(String name,String username) throws IOException{
        if(users.containsKey(username)){
            for(Need need : users.get(username)){
                if(need.getName().equals(name)){
                    return need;
                }
                return null;
            }
        }
        return null;
    }

    public Collection<Need> getNeeds(String username) throws IOException{
        return this.users.get(username);

    }

    public void checkout(String username) throws IOException{
        users.remove(username);
        save();
    }



}
