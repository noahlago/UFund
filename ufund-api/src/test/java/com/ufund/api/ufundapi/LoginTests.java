package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.LoginInfo;
import com.ufund.api.ufundapi.persistence.Login;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTests {
    private Login login;
    private ObjectMapper objectMapper;
    private String testFilename;

    @BeforeEach
    void setUp() {
        this.testFilename = "test-keys.json";
        this.objectMapper = new ObjectMapper();
        this.login = new Login(testFilename, objectMapper);
    }

    @Test
    void testLoginSuccess() throws IOException {
        LoginInfo testInfo = new LoginInfo("testUser", "password");
        createTestJsonFile(testFilename, new LoginInfo[]{testInfo});
        String token = login.login(testInfo);
        assertNotNull(token);
        assertTrue(login.isLoggedIn(testInfo.getUsername()));
    }


    @Test
    void testLogoutSuccess() throws IOException {
        LoginInfo testInfo = new LoginInfo("testUser", "password");
        createTestJsonFile(testFilename, new LoginInfo[]{testInfo});
        login.login(testInfo);
        boolean result = login.logout(testInfo.getUsername());
        assertTrue(result);
        assertFalse(login.isLoggedIn(testInfo.getUsername()));
    }
    private void createTestJsonFile(String filename, LoginInfo[] data) throws IOException {
        objectMapper.writeValue(new File(filename), data);
    }
}
