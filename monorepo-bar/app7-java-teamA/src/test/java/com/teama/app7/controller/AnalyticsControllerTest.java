package com.teama.app7.controller;

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
public class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetServiceInfo() throws Exception {
        mockMvc.perform(get("/api/analytics/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("app7-java-teamA"))
                .andExpect(jsonPath("$.team").value("TeamA"))
                .andExpect(jsonPath("$.type").value("resource-intensive-analytics"))
                .andExpect(jsonPath("$.pipeline").value("custom"));
    }

    @Test
    public void testHealth() throws Exception {
        mockMvc.perform(get("/api/analytics/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("healthy"));
    }

    @Test
    public void testProcessAnalytics() throws Exception {
        String request = "{\"dataSize\": 100, \"operation\": \"process\"}";

        mockMvc.perform(post("/api/analytics/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.dataSize").value(100))
                .andExpect(jsonPath("$.processingTimeMs").exists());
    }

    @Test
    public void testAggregateData() throws Exception {
        String request = "{\"dataSize\": 50, \"operation\": \"aggregate\"}";

        mockMvc.perform(post("/api/analytics/aggregate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.totalRecords").value(50));
    }

    @Test
    public void testComputeIntensive() throws Exception {
        mockMvc.perform(get("/api/analytics/compute/1000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.iterations").value(1000))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.duration_ms").exists());
    }
}
