package com.teamb.app6.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LogsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetLogs() throws Exception {
        // Make some requests to generate logs
        mockMvc.perform(get("/api/products"));
        mockMvc.perform(get("/api/products/health"));

        // Check logs endpoint
        mockMvc.perform(get("/api/logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("app6-java-teamB"))
                .andExpect(jsonPath("$.total_requests").isNumber())
                .andExpect(jsonPath("$.logs").isArray());
    }

    @Test
    public void testGetLogsWithLimit() throws Exception {
        // Generate multiple requests
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(get("/api/products"));
        }

        // Check logs with limit
        mockMvc.perform(get("/api/logs?limit=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.logs").isArray());
    }
}
