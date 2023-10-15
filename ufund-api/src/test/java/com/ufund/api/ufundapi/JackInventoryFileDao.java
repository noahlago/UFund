package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.InventoryFIleDao;

public class JackInventoryFileDao {
    ObjectMapper mapper = new ObjectMapper();


    @Test
    void testDeletePresent(){
        InventoryFIleDao cupboard = new InventoryFIleDao(null, mapper);
        cupboard.newNeed(new Need("need1", 0, 0, "type1"));

        assertEquals(cupboard.deleteNeed("need1"), true);
    }

    @Test
    void testDeleteNotPresent(){
        InventoryFIleDao cupboard = new InventoryFIleDao(null, mapper);
   

        assertEquals(cupboard.deleteNeed("need1"), false);
    }

    @Test
    void testUpdateWorks(){
        InventoryFIleDao cupboard = new InventoryFIleDao(null, mapper);

        cupboard.newNeed(new Need("need1", 0, 0, "type1"));

        cupboard.updateNeed(new Need("need1", 2, 4, "type2"));

        Need testNeed = cupboard.getNeed("need1");
        
        assertEquals(testNeed.getType(),"type2");

    }

    @Test
    void testUpdateNotPresent(){
        InventoryFIleDao cupboard = new InventoryFIleDao(null, mapper);


        Need testNeed = cupboard.updateNeed(new Need("need1", 2, 4, "type2"));

        assertNull(testNeed);

    }






}
