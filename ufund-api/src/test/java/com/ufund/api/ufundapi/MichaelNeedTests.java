package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Need;

public class MichaelNeedTests {
    
    @Test
    public void testEquals() {
        Need need1 = new Need("42 super mightys from mighty taco", 126, 42, "afternoon snack");
        Need need2 = new Need("42 super mightys from mighty taco", 126, 42, "afternoon snack");
        assertTrue(need1.equals(need2));
    }
    
    @Test
    public void testNotEquals() {
        Need need1 = new Need("a kilogram of steel", 20, 1, "heavy");
        Need need2 = new Need("a kilogram of feathers", 50, 1, "heavy?");
        assertFalse(need1.equals(need2));
    }
    
    @Test
    public void testGetters() {
        Need need = new Need("my sanity back", 999999999, 1, "mythical");
        assertEquals("my sanity back", need.getName());
        assertEquals(999999999, need.getCost());
        assertEquals(1, need.getQuantity());
        assertEquals("mythical", need.getType());
    }
}