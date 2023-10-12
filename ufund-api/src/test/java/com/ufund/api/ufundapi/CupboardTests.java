package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.controller.InventoryController;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.InventoryFIleDao;

@SpringBootTest
public class CupboardTests {
    private ObjectMapper mapper = new ObjectMapper();
    @Test
    void newNeedTest(){
        InventoryFIleDao inventory = new InventoryFIleDao("file",mapper);
        Need need = new Need("need", 0, 0, null);
        

        assertEquals(inventory.newNeed(need).getName(),need.getName());
        
    }    
    
    @Test
    void getNeedTest(){
        InventoryFIleDao cupboard = new InventoryFIleDao("file",mapper);

        Need need = new Need("test1", 0, 0, null);

        cupboard.newNeed(need);

        Need need2 = cupboard.getNeed("test1");

        assertEquals("test1",need2.getName());
    }

    @Test
    void updateNeedTest(){
        InventoryFIleDao cupboard = new InventoryFIleDao("file",mapper);

        Need need = new Need("test1", 0, 0, null);

        cupboard.newNeed(need);

        Need newNeed = new Need("test1", 0, 0, "update??????");

        Need need2 = cupboard.updateNeed(newNeed);

        assertEquals(need2.getType(), "update??????");
    }

    @Test
    void deleteNeedTest(){
        InventoryFIleDao cupboard = new InventoryFIleDao("file",mapper);

        Need need = new Need("test1", 0, 0, null);

        cupboard.newNeed(need);

        boolean bool1= cupboard.deleteNeed("test1");
        boolean bool2= cupboard.deleteNeed("test999999");

        assertEquals(bool1,true);
        assertEquals(bool2,false);
    }

    @Test
    void testController(){
        InventoryFIleDao inventoryFIleDao = new InventoryFIleDao(null, mapper);
        InventoryController controller = new InventoryController(inventoryFIleDao);
        controller.createNeed(new Need("bob", 0, 0, "bill"));
        assertNotNull(inventoryFIleDao.getNeeds());
    }
}
