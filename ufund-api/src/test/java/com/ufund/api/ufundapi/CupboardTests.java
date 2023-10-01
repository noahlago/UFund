package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufund.Cupboard;
import com.ufund.Need;

@SpringBootTest
public class CupboardTests {
    @Test
    void newNeedTest(){
        Cupboard cupboard = new Cupboard("test");

        ResponseEntity<Need> created = cupboard.newNeed("test1", 0, 0, "type");
        ResponseEntity<Need> conflict = cupboard.newNeed("test1", 0, 0, "pls conflict");

        assertEquals(created.getStatusCode(), HttpStatus.CREATED);
        assertEquals(conflict.getStatusCode(), HttpStatus.CONFLICT);
    }    
    
    @Test
    void getNeedTest(){
        Cupboard cupboard = new Cupboard("test");

        cupboard.newNeed("test1", 0, 0, "type");

        ResponseEntity<Need> response = cupboard.getNeed("test1");

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void updateNeedTest(){
        Cupboard cupboard = new Cupboard("test");

        cupboard.newNeed("test1", 0, 0, "type");

        ResponseEntity<Need> response = cupboard.updateNeed("test1", 10, 0, "update??????");

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void deleteNeedTest(){
        Cupboard cupboard = new Cupboard("test");

        cupboard.newNeed("test1", 0, 0, "type");

        ResponseEntity<Need> response = cupboard.deleteNeed("test1");
        ResponseEntity<Need> conflict = cupboard.deleteNeed("test999999");

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(conflict.getStatusCode(), HttpStatus.CONFLICT);
    }
}
