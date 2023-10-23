package com.ufund.api.ufundapi;

import com.ufund.api.ufundapi.model.Need;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class SwapnilNeedTests {

	@Test
	void equalsCloneTest() {
		Need need = new Need("Fork", 1, 2, "Utensils");
		assertEquals(need, need);
	}

	@Test
	void equalsEquivalentTest() {
		Need need1 = new Need("Fork", 1, 2, "Utensils");
		Need need2 = new Need("Fork", 1, 2, "Utensils");
		assertEquals(need1, need2);
	}

	@Test
	void equalsNotEquivalentTest() {
		Need need1 = new Need("Fork", 1, 2, "Utensils");
		Need need2 = new Need("Pan", 3, 4, "Cookware");
		assertNotEquals(need1, need2);
	}

	@Test
	void equalsNotEquivalentTypeTest() {
		Need need = new Need("Fork", 1, 2, "Utensils");
		Object obj = new Object();
		assertNotEquals(need, obj);
	}
}