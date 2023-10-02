package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.InventoryDAO;
import com.ufund.api.ufundapi.persistence.InventoryFIleDao;

@SpringBootTest
public class CupboardTests {
    private ObjectMapper mapper = new ObjectMapper();
    @Test
    void newNeedTest(){
        InventoryFIleDao inventory = new InventoryFIleDao("file",mapper);
        

        assertEquals(inventory.newNeed("need", 0, 0, null),true);
        
    }    
    
    @Test
    void getNeedTest(){
        InventoryFIleDao cupboard = new InventoryFIleDao("file",mapper);

        cupboard.newNeed("test1", 0, 0, "type");

        Need need = cupboard.getNeed("test1");

        assertEquals("test1",need.getName());
    }

    @Test
    void updateNeedTest(){
        InventoryFIleDao cupboard = new InventoryFIleDao("file",mapper);

        cupboard.newNeed("test1", 0, 0, "type");

        Need need = cupboard.updateNeed("test1", 10, 0, "update??????");

        assertEquals(need.getType(), "update??????");
    }

    @Test
    void deleteNeedTest(){
        InventoryFIleDao cupboard = new InventoryFIleDao("file",mapper);

        cupboard.newNeed("test1", 0, 0, "type");

        boolean bool1= cupboard.deleteNeed("test1");
        boolean bool2= cupboard.deleteNeed("test999999");

        assertEquals(bool1,true);
        assertEquals(bool2,false);
    }
}
