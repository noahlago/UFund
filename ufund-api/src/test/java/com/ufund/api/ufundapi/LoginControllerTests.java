package com.ufund.api.ufundapi;

import com.ufund.api.ufundapi.model.LoginInfo;
import com.ufund.api.ufundapi.controller.LoginController;
import com.ufund.api.ufundapi.persistence.Login;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginControllerTests {

    private LoginController loginController;
    private Login login;
    private final String validUsername = "user";
    private final String validToken = "token";
    private final String invalidUsername = "admin";
    private final String invalidToken = "";

    @BeforeEach
    public void setUp() {
        login = mock(Login.class);
        loginController = new LoginController(login);
    }

    @Test
    public void testLoginSuccessful() throws IOException {
        LoginInfo loginInfo = new LoginInfo("username", "password");
        when(login.login(loginInfo)).thenReturn(validToken);

        ResponseEntity<LoginInfo> response = loginController.login(loginInfo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginInfo, response.getBody());
    }

    @Test
    public void testLoginFailed() throws IOException {
        LoginInfo loginInfo = new LoginInfo("username", "password");
        when(login.login(loginInfo)).thenReturn("");

        ResponseEntity<LoginInfo> response = loginController.login(loginInfo);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(loginInfo, response.getBody());
    }

    @Test
    public void testLoginInternalError() throws IOException {
        LoginInfo loginInfo = new LoginInfo("username", "password");
        when(login.login(loginInfo)).thenThrow(new IOException());

        ResponseEntity<LoginInfo> response = loginController.login(loginInfo);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testLogoutSuccessful() throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("username", validUsername);
        headers.put("token", validToken);

        when(login.logout(validUsername)).thenReturn(true);

        ResponseEntity<Object> response = loginController.logout(validUsername, validToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testLogoutUnauthenticated() {
        Map<String, String> headers = new HashMap<>();
        headers.put("username", validUsername);
        headers.put("token", invalidToken);

        ResponseEntity<Object> response = loginController.logout(validUsername, invalidToken);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testLogoutFailed() throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("username", invalidUsername);
        headers.put("token", validToken);

        when(login.logout(invalidUsername)).thenReturn(false);

        ResponseEntity<Object> response = loginController.logout(invalidUsername, validToken);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testLogoutInternalError() throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("username", validUsername);
        headers.put("token", validToken);

        when(login.logout(validUsername)).thenThrow(new IOException());

        ResponseEntity<Object> response = loginController.logout(validUsername, validToken);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
}
