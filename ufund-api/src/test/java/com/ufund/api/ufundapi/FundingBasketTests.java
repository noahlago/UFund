package com.ufund.api.ufundapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.FundingBasket;
import com.ufund.api.ufundapi.persistence.InventoryFileDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FundingBasketTests {
    private InventoryFileDAO fileDAO;
    private ObjectMapper mapper = new ObjectMapper();
    private FundingBasket basket;
    private static final String TEST_FILENAME = "testbasket.json";
    private static final String TEST_FILENAME2 = "testinventory.json";
    private static final String TEST_USERNAME = "testUser";

    @BeforeEach
    void setUp() throws IOException{
        File file = new File(TEST_FILENAME);
        if (file.exists() && file.isFile()) {
            file.delete();
        }

        
        fileDAO = new InventoryFileDAO(TEST_FILENAME2, mapper);
        Need newNeed = new Need("name", 10000, 100000, "type-2"); 
        fileDAO.newNeed(newNeed);
        basket = new FundingBasket(TEST_FILENAME,TEST_FILENAME2, mapper);
    }

    @Test
    void addNeedSuccessTest() throws IOException {
        Need need = new Need("testNeed", 10, 2, "quality");
        Need addedNeed = basket.addNeed(need, TEST_USERNAME);
        assertNotNull(addedNeed);
        assertEquals(need, addedNeed);
        Collection<Need> userNeeds = basket.getNeeds(TEST_USERNAME);
        assertTrue(userNeeds.contains(need));
    }

    @Test
    void addNeedAlreadyExistsTest() throws IOException {
        Need need = new Need("testNeed", 10, 2, "quality");
        basket.addNeed(need, TEST_USERNAME);
        Need addedNeed = basket.addNeed(need, TEST_USERNAME);
        assertEquals(addedNeed.getQuantity(),2); 
    }

    @Test
    void deleteNeedSuccessTest() throws IOException {
        Need need = new Need("testNeed", 10, 2, "quality");
        basket.addNeed(need, TEST_USERNAME);
        Need deletedNeed = basket.deleteNeed(need, TEST_USERNAME);
        assertNotNull(deletedNeed);
        assertEquals(need, deletedNeed);
        Collection<Need> userNeeds = basket.getNeeds(TEST_USERNAME);
        assertFalse(userNeeds.contains(need));
    }

    @Test
    void getNeedSuccessTest() throws IOException {
        Need need = new Need("testNeed", 10, 2, "quality");
        basket.addNeed(need, TEST_USERNAME);
        Need retrievedNeed = basket.getNeed(need.getName(), TEST_USERNAME);
        assertNotNull(retrievedNeed);
        assertEquals(need, retrievedNeed);
    }

    @Test
    void getNeedNotFoundTest() throws IOException {
        Need need = new Need("testNeed", 10, 2, "quality");
        Need retrievedNeed = basket.getNeed(need.getName(), TEST_USERNAME);
        assertNull(retrievedNeed);
    }

    @Test
    void getNeedsSuccessTest() throws IOException {
        Need need1 = new Need("testNeed1", 10, 2, "quality");
        Need need2 = new Need("testNeed2", 15, 3, "base");
        basket.addNeed(need1, TEST_USERNAME);
        basket.addNeed(need2, TEST_USERNAME);
        Collection<Need> userNeeds = basket.getNeeds(TEST_USERNAME);
        assertNotNull(userNeeds);
        assertEquals(2, userNeeds.size());
        assertTrue(userNeeds.contains(need1));
        assertTrue(userNeeds.contains(need2));
    }


    @Test
    void saveAndLoadTest() throws IOException {
        Need need1 = new Need("testNeed1", 10, 2, "quality");
        Need need2 = new Need("testNeed2", 15, 3, "base");
        basket.addNeed(need1, TEST_USERNAME);
        basket.addNeed(need2, TEST_USERNAME);
        basket = new FundingBasket(TEST_FILENAME, TEST_FILENAME2,mapper);
        Collection<Need> userNeeds = basket.getNeeds(TEST_USERNAME);
        assertNotNull(userNeeds);
        assertEquals(2, userNeeds.size());
        assertTrue(userNeeds.contains(need1));
        assertTrue(userNeeds.contains(need2));
    }

    @Test
    void testCheckout() throws IOException{
        Need need1 = new Need("testNeed1", 10, 2, "quality");
        basket.addNeed(need1, TEST_USERNAME);
        basket.checkout(TEST_USERNAME);
        assertEquals(basket.getNeeds(TEST_USERNAME), null);
    }

    @Test
    void testCheckoutQuantity1() throws IOException{
        Need need1 = new Need("testNeed7", 10, 1, "quality");
        basket.addNeed(need1, TEST_USERNAME);
        basket.checkout(TEST_USERNAME);
        assertEquals(basket.getNeeds(TEST_USERNAME), null);
    }

    @Test
    void testCheckoutWithFile() throws IOException{
        Need newNeed = new Need("name", 10000, 1, "type-2"); 
        basket.addNeed(newNeed, "billy");
        basket.checkout("billy");
        assertEquals(null, basket.getNeeds("billy")); 
    }
}
