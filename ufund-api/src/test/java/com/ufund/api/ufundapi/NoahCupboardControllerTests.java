package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.controller.InventoryController;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.InventoryFileDAO;

@SpringBootTest
public class NoahCupboardControllerTests {
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void successfulDeleteTest(){
        InventoryFileDAO fileDAO = new InventoryFileDAO(null, mapper);
        InventoryController controller = new InventoryController(fileDAO);

        controller.createNeed(new Need("new", 20, 2, "quality"));

        assertEquals(controller.deleteNeed("new").getStatusCode(), HttpStatus.OK);
    }

    @Test
    void failedDeleteTest(){
        InventoryFileDAO fileDAO = new InventoryFileDAO(null, mapper);
        InventoryController controller = new InventoryController(fileDAO);
        
        controller.createNeed(new Need("new", 20, 2, "quality"));

        assertEquals(controller.deleteNeed("uh oh").getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void successfulGetTest(){
        InventoryFileDAO fileDAO = new InventoryFileDAO(null, mapper);
        InventoryController controller = new InventoryController(fileDAO);
        
        controller.createNeed(new Need("new", 20, 2, "quality"));

        assertEquals(controller.getNeed("new").getStatusCode(), HttpStatus.OK);
    }

    @Test
    void failedGetTest(){
        InventoryFileDAO fileDAO = new InventoryFileDAO(null, mapper);
        InventoryController controller = new InventoryController(fileDAO);
        
        controller.createNeed(new Need("new", 20, 2, "quality"));

        assertEquals(controller.getNeed("uh oh").getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
