package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.InventoryFileDAO;

public class MichaelInventoryileDAOTests {
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetNeeds() throws IOException{
        InventoryFileDAO need = new InventoryFileDAO(null, mapper);
        Need need1 = new Need("car battery", 100, 1, "edible");
        Need need2 = new Need("peanut butter", 15, 3, "lubricant");
        need.newNeed(need1);
        need.newNeed(need2);
        assertEquals(2, need.getNeeds().size());
    }

    @Test
    public void testNewNeed() throws IOException{
        InventoryFileDAO dao = new InventoryFileDAO(null, mapper);
        Need need = new Need("like 100 rubber mallets", 1234, 100, "ammunition");
        Need addedNeed = dao.newNeed(need);
        assertEquals(need.getName(), addedNeed.getName());
        assertEquals(need.getCost(), addedNeed.getCost());
        assertEquals(need.getQuantity(), addedNeed.getQuantity());
        assertEquals(need.getType(), addedNeed.getType());
    }

        @Test
        public void testDeleteNeed() throws IOException{
        InventoryFileDAO need = new InventoryFileDAO(null, mapper);
        Need need1 = new Need("illegal pringles", 123456789, 1, "contraband");
        need.newNeed(need1);
        assertTrue(need.deleteNeed(need1.getName()));
        assertNull(need.getNeed(need1.getName()));
    }

    @Test
    public void testSearchNeed() throws IOException{
        InventoryFileDAO need = new InventoryFileDAO(null, mapper);
        Need need1 = new Need("a full sized freight train", 420, 1, "public transportation");
        Need need2 = new Need("a smaller than average freight train", 419, 1, "public transportation");
        need.newNeed(need1);
        need.newNeed(need2);
        assertEquals(1, need.searchNeed("full sized").size());
        assertEquals(2, need.searchNeed("freight train").size());
        assertEquals(0, need.searchNeed("a 5 game win streak").size());
    }
}
