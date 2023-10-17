package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.InventoryFileDAO;

public class JackInventoryFileDAO {
    ObjectMapper mapper = new ObjectMapper();


    @Test
    void testDeletePresent(){
        InventoryFileDAO cupboard = new InventoryFileDAO(null, mapper);
        cupboard.newNeed(new Need("need1", 0, 0, "type1"));

        assertEquals(cupboard.deleteNeed("need1"), true);
    }

    @Test
    void testDeleteNotPresent(){
        InventoryFileDAO cupboard = new InventoryFileDAO(null, mapper);
   

        assertEquals(cupboard.deleteNeed("need1"), false);
    }

    @Test
    void testUpdateWorks(){
        InventoryFileDAO cupboard = new InventoryFileDAO(null, mapper);

        cupboard.newNeed(new Need("need1", 0, 0, "type1"));

        cupboard.updateNeed(new Need("need1", 2, 4, "type2"));

        Need testNeed = cupboard.getNeed("need1");
        
        assertEquals(testNeed.getType(),"type2");

    }

    @Test
    void testUpdateNotPresent(){
        InventoryFileDAO cupboard = new InventoryFileDAO(null, mapper);


        Need testNeed = cupboard.updateNeed(new Need("need1", 2, 4, "type2"));

        assertNull(testNeed);

    }






}
