package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.controller.InventoryController;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.InventoryFileDAO;


@SpringBootTest
public class InventoryControllerTests {
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
			controller.createNeed(need,"admin","aaa");
		}
	}
	
	@Test
	void getNeedSuccessTest() {
		ResponseEntity<Collection<Need>> response = controller.getNeed("admin", "aaa");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Collection<Need> result = response.getBody();
		for (Need expectedNeed : needs) {
			assertTrue(result.contains(expectedNeed));
		}
	}

	@Test
	void createNeedSuccessTest() {
		Need newNeed = new Need("newNeed", 10, 1, "quality");
		ResponseEntity<Need> response = controller.createNeed(newNeed,"admin","aaa");
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(newNeed, response.getBody());
	}

	@Test
	void createNeedAlreadyExistsTest() {
		Need existingNeed = needs[0];
		ResponseEntity<Need> response = controller.createNeed(existingNeed,"admin","aaa");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void deleteNeedSuccessTest() {
		String name = "test";
		ResponseEntity<Need> response = controller.deleteNeed(name,"admin","aaa");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(needs[0], response.getBody());
	}

	@Test
	void deleteNeedNotFoundTest() {
		String name = "badname";
		ResponseEntity<Need> response = controller.deleteNeed(name,"admin","aaa");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void getNeedByNameSuccessTest() {
		String name = "test";
		ResponseEntity<Need> response = controller.getNeed(name,"admin","aaa");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(needs[0], response.getBody());
	}

	@Test
	void getNeedByNameNotFoundTest() {
		String name = "badname";
		ResponseEntity<Need> response = controller.getNeed(name,"admin","aaa");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void searchNeedsSuccessTest() {
		String name = "test";
		ResponseEntity<Collection<Need>> response = controller.searchNeeds(name,"admin","aaa");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Need[] expected = {needs[0], needs[1]};
		Need[] result = response.getBody().toArray(new Need[response.getBody().size()]);
		assertArrayEquals(expected, result);
	}

	@Test
	void searchNeedsEmptyTest() {
		String name = "badvalue";
		ResponseEntity<Collection<Need>> response = controller.searchNeeds(name,"admin","aaa");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Need[] expected = new Need[0];
		Need[] result = response.getBody().toArray(new Need[response.getBody().size()]);
		assertArrayEquals(expected, result);
	}

	@Test
	void updateNeedSuccessTest() {
		Need updatedNeed = new Need("test", 20, 2, "base");
		ResponseEntity<Need> response = controller.updateNeed(updatedNeed,"admin","aaa");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedNeed, response.getBody());
	}

	@Test
	void updateNeedNotFoundTest() {
		Need updatedNeed = new Need("badtype", 20, 2, "base");
		ResponseEntity<Need> response = controller.updateNeed(updatedNeed,"admin","aaa");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void updateNeedAlreadyExistsTest() {
		Need updatedNeed = new Need("unit", 20, 2, "base");
		ResponseEntity<Need> response = controller.updateNeed(updatedNeed,"admin","aaa");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedNeed, response.getBody());
	}
	

    @Test
    void getNeedIOExceptionTest() {
   
        InventoryFileDAO faultyFileDao = new InventoryFileDAOFaulty();
        InventoryController faultyController = new InventoryController(faultyFileDao);

        ResponseEntity<Collection<Need>> response = faultyController.getNeed("admin", "aaa");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void createNeedIOExceptionTest() {
      
        InventoryFileDAO faultyFileDao = new InventoryFileDAOFaulty();
        InventoryController faultyController = new InventoryController(faultyFileDao);

        Need newNeed = new Need("newNeed", 10, 1, "quality");
        ResponseEntity<Need> response = faultyController.createNeed(newNeed, "admin", "aaa");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void deleteNeedIOExceptionTest() {
      
        InventoryFileDAO faultyFileDao = new InventoryFileDAOFaulty();
        InventoryController faultyController = new InventoryController(faultyFileDao);

        String name = "test";
        ResponseEntity<Need> response = faultyController.deleteNeed(name, "admin", "aaa");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getNeedByNameIOExceptionTest() {
      
        InventoryFileDAO faultyFileDao = new InventoryFileDAOFaulty();
        InventoryController faultyController = new InventoryController(faultyFileDao);

        String name = "test";
        ResponseEntity<Need> response = faultyController.getNeed(name, "admin", "aaa");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void searchNeedsIOExceptionTest() {
      
        InventoryFileDAO faultyFileDao = new InventoryFileDAOFaulty();
        InventoryController faultyController = new InventoryController(faultyFileDao);

        String name = "test";
        ResponseEntity<Collection<Need>> response = faultyController.searchNeeds(name, "admin", "aaa");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void updateNeedIOExceptionTest() {
      
        InventoryFileDAO faultyFileDao = new InventoryFileDAOFaulty();
        InventoryController faultyController = new InventoryController(faultyFileDao);

        Need updatedNeed = new Need("test", 20, 2, "base");
        ResponseEntity<Need> response = faultyController.updateNeed(updatedNeed, "admin", "aaa");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    
    class InventoryFileDAOFaulty extends InventoryFileDAO {
        InventoryFileDAOFaulty() {
            super(null, mapper);
        }

        @Override
        public Collection<Need> getAllNeeds() throws IOException {
            throw new IOException("Simulated IOException");
        }

        @Override
        public Need newNeed(Need need) throws IOException {
            throw new IOException("Simulated IOException");
        }

        @Override
        public Need getNeed(String name) throws IOException {
            throw new IOException("Simulated IOException");
        }

        @Override
        public boolean deleteNeed(String name) throws IOException {
            throw new IOException("Simulated IOException");
        }

        @Override
        public Collection<Need> searchNeed(String name) throws IOException {
            throw new IOException("Simulated IOException");
        }

        @Override
        public Need updateNeed(Need need) throws IOException {
            throw new IOException("Simulated IOException");
        }
    }
}
