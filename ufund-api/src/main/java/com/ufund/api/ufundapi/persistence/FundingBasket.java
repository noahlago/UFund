package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;

@Component
public class FundingBasket {
    private HashMap<String,ArrayList<Need>> users;
    private HashMap<String,Need> inventoryNeeds;
    private String filename;
    private String inventoryFile;
    private ObjectMapper objectMapper;
    private boolean matching;

    public FundingBasket(@Value("${basket.file}") String filename, @Value("${needs.file}") String filename2, ObjectMapper objectMapper){
        this.filename = filename;
        this.inventoryFile = filename2;
        this.objectMapper = objectMapper;
        this.users = new HashMap<>();
        this.inventoryNeeds = new HashMap<>();
        this.matching = false;

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


        Need[] needArray = getNeedsArray(null);

        objectMapper.writeValue(new File(inventoryFile),needArray);
        
        return true;
    }

    private void load() throws IOException{
        if(filename == null || inventoryFile == null){
            return;
        }

        this.users = objectMapper.readValue(new File(filename),new TypeReference<HashMap<String, ArrayList<Need>>>(){});

        Need[] needArray = objectMapper.readValue(new File(inventoryFile),Need[].class);


        for (Need need : needArray) {
            inventoryNeeds.put(need.getName(), need);
        }
    }

        private Need[] getNeedsArray(String containsText) { // if containsText == null, no filter
            ArrayList<Need> needArrayList = new ArrayList<>();
    
            for (Need need : inventoryNeeds.values()) {
                if (containsText == null || need.getName().contains(containsText)) {
                    needArrayList.add(need);
                }
            }
    
            Need[] needArray = new Need[needArrayList.size()];
            needArrayList.toArray(needArray);
            return needArray;
        }


    


    public Need addNeed(Need need, String username) throws IOException{
        if(users.containsKey(username)){
            if(users.get(username).contains(need)){
                for (Need need2: users.get(username)){
                    if(need2.equals(need)){
                        int quantity = need2.increaseQuantity(1);
                        save();
                        return need2;           
                    }
                }
                return null;
            }
            else{
                Need newNeed = new Need(need.getName(), need.getCost(), 1, need.getType());
                users.get(username).add(newNeed);
                save();
                return need;
            }
        }
        else{
            users.put(username, new ArrayList<>());  
            Need newNeed = new Need(need.getName(), need.getCost(), 1, need.getType());
            users.get(username).add(newNeed);
            save();
            return need;
        }
    }


    public Need deleteNeed(Need need, String username) throws IOException{
        if(users.get(username).contains(need)){
            for (Need need2: users.get(username)){
                if(need2.equals(need)){
                    int quantity = need2.decreaseQuantity(1);
                    if(quantity<= 0){
                        users.get(username).remove(need);
                        save();
                        return need2;
                    }
                }
            }
            return null;
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
            }
        }
        return null;
    }

    public Collection<Need> getNeeds(String username) throws IOException{
        return this.users.get(username);
    }

    public void checkout(String username) throws IOException{
        
        for (Need need: users.get(username)){
            int quantity = need.getQuantity();
            if(matching){
                quantity *= 2;
            }
            Need invNeed = inventoryNeeds.get(need.getName());
            if(invNeed != null){
                int newQuantity = invNeed.decreaseQuantity(quantity);
                if(newQuantity <= 0){
                    inventoryNeeds.remove(need.getName());
                }
                else{
                Need newNeed = new Need(need.getName(), need.getCost(), newQuantity, need.getType());
                inventoryNeeds.put(need.getName(), newNeed);
                }   
            }
        }
        users.remove(username);
        save();
    }

    public void switchMatching(){
        this.matching = !(matching);
    }

    public boolean getMatching(){
        return this.matching;
    }

}
