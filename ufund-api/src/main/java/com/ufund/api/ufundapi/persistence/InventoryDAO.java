package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.util.Collection;

import com.ufund.api.ufundapi.model.Need;

public interface InventoryDAO {


    public Need getNeed(String name) throws IOException;

    public Need updateNeed(Need need) throws IOException;

    public Need newNeed(Need need) throws IOException;

    public Collection<Need> getAllNeeds() throws IOException;

    public boolean deleteNeed(String name) throws IOException;

    public Collection<Need> searchNeed(String search) throws IOException;


    
    

}
