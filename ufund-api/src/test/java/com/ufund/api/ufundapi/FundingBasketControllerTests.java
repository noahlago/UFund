package com.ufund.api.ufundapi;

import com.ufund.api.ufundapi.controller.FundingBasketController;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.FundingBasket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FundingBasketControllerTests {

    private FundingBasketController fundingBasketController;
    private FundingBasket fundingBasket;
    private final String validUsername = "user";
    private final String validToken = "token";
    private final String invalidUsername = "admin";

    @BeforeEach
    public void setUp() {
        fundingBasket = mock(FundingBasket.class);
        fundingBasketController = new FundingBasketController(fundingBasket);
    }

        private Collection<Need> createSampleNeeds() {
        List<Need> needs = new ArrayList<>();
        needs.add(new Need("Need1", 10, 5, "Type1"));
        needs.add(new Need("Need2", 20, 3, "Type2"));
        needs.add(new Need("Need3", 5, 2, "Type3"));
        return needs;
    }

    @Test
    public void testGetNeedValidUserAndToken() throws IOException {
        Collection<Need> expectedNeeds = createSampleNeeds();
        when(fundingBasket.getNeeds(validUsername)).thenReturn(expectedNeeds);

        ResponseEntity<Collection<Need>> response = fundingBasketController.getNeed(validUsername, validToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedNeeds, response.getBody());
    }

    @Test
    public void testGetNeedInvalidUser() {
        String invalidUsername = "admin";
        String validToken = "validToken";
        ResponseEntity<Collection<Need>> response = fundingBasketController.getNeed(invalidUsername, validToken);
    
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetNeedInvalidToken() {
        String invalidToken = "";
        ResponseEntity<Collection<Need>> response = fundingBasketController.getNeed(validUsername, invalidToken);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetNeedIOException() throws IOException {
        when(fundingBasket.getNeeds(validUsername)).thenThrow(new IOException());
        ResponseEntity<Collection<Need>> response = fundingBasketController.getNeed(validUsername, validToken);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteNeedIOException() throws IOException {
        String needName = "NeedToDelete";
        when(fundingBasket.getNeed(needName, validUsername)).thenThrow(new IOException());
        ResponseEntity<Need> response = fundingBasketController.deleteNeed(needName, validUsername, validToken);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    
    @Test
    public void testCreateNeedValidUserAndToken() throws IOException {
        Need newNeed = new Need("New Need", 10, 5, "Type");
        when(fundingBasket.addNeed(newNeed, validUsername)).thenReturn(newNeed);

        ResponseEntity<Need> response = fundingBasketController.createNeed(newNeed, validUsername, validToken);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newNeed, response.getBody());
    }

    @Test
    public void testCreateNeedIOException() throws IOException {
        Need newNeed = new Need("New Need", 10, 5, "Type");
        when(fundingBasket.addNeed(newNeed, validUsername)).thenThrow(new IOException());
        ResponseEntity<Need> response = fundingBasketController.createNeed(newNeed, validUsername, validToken);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateNeedInvalidUser() {
        Need newNeed = new Need("New Need", 10, 5, "Type");
        ResponseEntity<Need> response = fundingBasketController.createNeed(newNeed, "admin", validToken);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateNeedInvalidToken() {
        Need newNeed = new Need("New Need", 10, 5, "Type");
        ResponseEntity<Need> response = fundingBasketController.createNeed(newNeed, validUsername, "");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteNeedValidUserAndToken() throws IOException {
        String needName = "NeedToDelete";
        Need need = new Need(needName, 15, 3, "Type");
        when(fundingBasket.getNeed(needName, validUsername)).thenReturn(need);

        ResponseEntity<Need> response = fundingBasketController.deleteNeed(needName, validUsername, validToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
        verify(fundingBasket, times(1)).deleteNeed(need, validUsername);
    }

    @Test
    public void testDeleteNeedInvalidUser() throws IOException{
        String needName = "NeedToDelete";
        ResponseEntity<Need> response = fundingBasketController.deleteNeed(needName, invalidUsername, validToken);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(fundingBasket, never()).deleteNeed(any(Need.class), any(String.class));
    }

    @Test
    public void testDeleteNeedInvalidToken() throws IOException{
        String needName = "NeedToDelete";
        ResponseEntity<Need> response = fundingBasketController.deleteNeed(needName, validUsername, "");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(fundingBasket, never()).deleteNeed(any(Need.class), any(String.class));
    }

    @Test
    public void testDeleteNeedNeedNotFound() throws IOException {
        String needName = "NonExistentNeed";
        when(fundingBasket.getNeed(needName, needName)).thenReturn(null);

        ResponseEntity<Need> response = fundingBasketController.deleteNeed(needName, validUsername, validToken);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(fundingBasket, never()).deleteNeed(any(Need.class), any(String.class));
    }


    @Test
    public void testCheckoutValidUserAndToken() throws IOException {
        ResponseEntity<String> response = fundingBasketController.checkout(validUsername, validToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals("checked out", response.getBody());
        verify(fundingBasket, times(1)).checkout(validUsername);
    }

    @Test
    public void testCheckoutInvalidUser() throws IOException{
        ResponseEntity<String> response = fundingBasketController.checkout(invalidUsername, validToken);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(fundingBasket, never()).checkout(invalidUsername);
    }

    @Test
    public void testCheckoutInvalidToken() throws IOException{
        ResponseEntity<String> response = fundingBasketController.checkout(validUsername, "");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(fundingBasket, never()).checkout("");
    } 
    
    @Test 
    public void testGetMatchingValid() throws IOException{
        ResponseEntity<Boolean> responseEntity = fundingBasketController.getMatching(validUsername, validToken);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test 
    public void testGetMatchingInvalidToken() throws IOException{
        ResponseEntity<Boolean> responseEntity = fundingBasketController.getMatching(validUsername, "");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test 
    public void testSwitchMatchingValid() throws IOException{
        ResponseEntity<String> responseEntity = fundingBasketController.switchMatching("admin", validToken);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test 
    public void testSwitchMatchingInvalidUser() throws IOException{
        ResponseEntity<String> responseEntity = fundingBasketController.switchMatching("user", validToken);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    
    @Test 
    public void testSwitchMatchingInvalidToken() throws IOException{
        ResponseEntity<String> responseEntity = fundingBasketController.switchMatching("admin", "");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }



}
