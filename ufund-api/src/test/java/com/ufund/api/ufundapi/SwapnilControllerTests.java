package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.controller.InventoryController;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.InventoryFileDAO;


@SpringBootTest
public class SwapnilControllerTests {
	private ObjectMapper mapper = new ObjectMapper();

	Need[] needs = {
		new Need("test", 20, 2, "quality"),
		new Need("tester", 9, 3, "quality"),	
		new Need("unit", 15, 1, "base")
	};
	InventoryFileDAO fileDao;
	InventoryController controller;
	
	@BeforeEach
	void setUp() {
		fileDao = new InventoryFileDAO(null, mapper);
		controller = new InventoryController(fileDao);
		for (Need need : needs) {
			controller.createNeed(need);
		}
	}
	
	@Test
	void searchSuccessTest() {
		ResponseEntity<Collection<Need>> response = controller.searchNeeds("test");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Need[] expected = {needs[0], needs[1]};
		Need[] result = response.getBody().toArray(new Need[response.getBody().size()]);
		assertArrayEquals(expected, result);
	}

	@Test
	void searchEmptyTest() {
		ResponseEntity<Collection<Need>> response = controller.searchNeeds("badvalue");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Need[] expected = new Need[0];
		Need[] result = response.getBody().toArray(new Need[response.getBody().size()]);
		assertArrayEquals(expected, result);
	}

	@Test
	void searchAltSuccessTest() {
		ResponseEntity<Collection<Need>> response = controller.searchNeeds("unit");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Need[] expected = {needs[2]};
		Need[] result = response.getBody().toArray(new Need[response.getBody().size()]);
		assertArrayEquals(expected, result);
	}

	@Test
	void updateSuccessTest() {
		Need expected = new Need("unit", 20, 2, "base");
		ResponseEntity<Need> response = controller.updateNeed(expected);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expected, response.getBody());
	}

	@Test
	void updateFailureTest() {
		Need expected = new Need("badtype", 20, 2, "base");
		ResponseEntity<Need> response = controller.updateNeed(expected);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void updateAltSuccessTest() {
		Need expected = new Need("test", 20, 2, "base");
		ResponseEntity<Need> response = controller.updateNeed(expected);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expected, response.getBody());
	}
	
} 

