package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.InventoryFileDAO;

@SpringBootTest
public class NoahCupboardDAOTests {
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void newSuccessTest() throws IOException{
        InventoryFileDAO cupboard = new InventoryFileDAO("needs.json", mapper);
        Need newNeed = new Need("desperately needed", 999, 10, "good");

        assertNotNull(cupboard.newNeed(newNeed));
    }

    @Test
    void newFailedTest() throws IOException{
        InventoryFileDAO cupboard = new InventoryFileDAO("needs.json", mapper);
        Need newNeed = new Need("desperately needed", 999, 10, "good");

        cupboard.newNeed(newNeed);
        assertNull(cupboard.newNeed(newNeed));
    }

    @Test
    void getAllEmptyTest() throws IOException{
        InventoryFileDAO cupboard = new InventoryFileDAO("needs.json", mapper);

        assertEquals(cupboard.getAllNeeds().size(), 0);
    }

    @Test
    void getAllOneTest() throws IOException{
        InventoryFileDAO cupboard = new InventoryFileDAO("needs.json", mapper);
        Need need1 = new Need("desperately needed", 999, 10, "good");
        Need need2 = new Need("other", 50, 50, "miscellaneous");

        cupboard.newNeed(need2);
        cupboard.newNeed(need1);

        assertEquals(cupboard.getAllNeeds().size(), 2);
    }
}
