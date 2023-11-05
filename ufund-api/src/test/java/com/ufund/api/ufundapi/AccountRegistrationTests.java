package com.ufund.api.ufundapi;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.LoginInfo;
import com.ufund.api.ufundapi.persistence.Login;

@SpringBootTest
public class AccountRegistrationTests {
    private Login login;
    private ObjectMapper objectMapper;
    private String testFilename;

    @BeforeEach
    void setUp() throws StreamReadException, DatabindException, IOException {
        this.testFilename = "test-keys.json";
        this.objectMapper = new ObjectMapper();
        this.login = new Login(testFilename, objectMapper);
    }

    @Test
    void successful_registration_test() throws IOException{
        LoginInfo info = new LoginInfo("idk", "new");
        String token = login.register(info);

        assertNotEquals("", token);
    }

    @Test
    void failed_registration_test() throws IOException{
        LoginInfo info = new LoginInfo("new", "new");
        login.register(info);

        info = new LoginInfo("new", "new");

        String token = login.register(info);

        assertEquals("", token);
    }
}
