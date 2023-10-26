package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.LoginInfo;

public class LoginInfoTests {
    private LoginInfo loginInfo;

    @BeforeEach
    public void setUp() {
        loginInfo = new LoginInfo("testUser", "testPassword");
    }

    @Test
    public void testGetUsername() {
        assertEquals("testUser", loginInfo.getUsername());
    }

}