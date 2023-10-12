package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Need;

public class JackNeedTests {

    @Test
    void TestToString(){
        Need need = new Need("Repairs", 4, 4, "Cost");
        String toString = need.toString();
        assertEquals(toString, "Need [name=Repairs, cost=4, quantity= 4, type=Cost]");
    }

    @Test
    void TestGetName(){
        Need need = new Need("Repairs", 4, 4, "Cost");
        String got = need.getName();
        assertEquals(got,"Repairs");
    }

    @Test
    void TestGetCost(){
        Need need = new Need("Repairs", 4, 4, "Cost");
        int got = need.getCost();
        assertEquals(got,4);
    }

    @Test
    void TestGetQuantity(){
        Need need = new Need("Repairs", 4, 4, "Cost");
        int got = need.getQuantity();
        assertEquals(got,4);
    }

    @Test
    void TestGetType(){
        Need need = new Need("Repairs", 4, 4, "Cost");
        String got = need.getType();
        assertEquals(got,"Cost");
    }

    
}
