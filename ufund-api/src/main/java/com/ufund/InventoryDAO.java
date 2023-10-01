package com.ufund;

import java.util.Collection;

public interface InventoryDAO {


    public Need getNeed(String name);

    public Need updateNeed(String name,int cost, int quantity, String type);

    public boolean newNeed(String name, int cost, int quantity, String type);

    public Collection<Need> getAllNeeds();

    public boolean deleteNeed(String name);

    public Need searchNeed(String search);


    
    

}
