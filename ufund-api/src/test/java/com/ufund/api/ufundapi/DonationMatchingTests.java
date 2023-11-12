package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.persistence.FundingBasket;

public class DonationMatchingTests {
    private ObjectMapper mapper = new ObjectMapper();
    private FundingBasket basket;
    private static final String TEST_FILENAME = "testbasket.json";
    private static final String TEST_FILENAME2 = "testinventory.json";


    @BeforeEach
    void setUp() {
        File file = new File(TEST_FILENAME);
        if (file.exists() && file.isFile()) {
            file.delete();
        }

        basket = new FundingBasket(TEST_FILENAME,TEST_FILENAME2, mapper);
    }

    @Test
    void activateMatchingTest(){
        basket.switchMatching();

        assertEquals(basket.getMatching(), true);
    }

    @Test
    void macthingInitializationTest(){
        assertEquals(basket.getMatching(), false);
    }

    void deactivateMatchingTest(){
        basket.switchMatching();
        basket.switchMatching();

        assertEquals(basket.getMatching(), false);
    }
}
