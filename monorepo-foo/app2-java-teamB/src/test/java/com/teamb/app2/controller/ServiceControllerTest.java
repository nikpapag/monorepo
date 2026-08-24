package com.teamb.app2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetServiceInfo() throws Exception {
        mockMvc.perform(get("/api/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("app2-java-teamB"))
                .andExpect(jsonPath("$.team").value("TeamB"))
                .andExpect(jsonPath("$.type").value("lightweight"));
    }

    @Test
    public void testHealth() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("healthy"));
    }

    @Test
    public void testProcessData() throws Exception {
        String requestBody = "{\"input\": \"hello world\"}";

        mockMvc.perform(post("/api/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.processed").value("HELLO WORLD"));
    }

    @Test
    public void testCalculate() throws Exception {
        mockMvc.perform(get("/api/calculate")
                .param("a", "5")
                .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum").value(8))
                .andExpect(jsonPath("$.product").value(15))
                .andExpect(jsonPath("$.difference").value(2));
    }

    @Test
    public void testCalculateDefaults() throws Exception {
        mockMvc.perform(get("/api/calculate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum").value(30))
                .andExpect(jsonPath("$.a").value(10))
                .andExpect(jsonPath("$.b").value(20));
    }
}
