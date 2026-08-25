package com.teamb.app2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MetricsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetMetrics() throws Exception {
        // Make some requests to generate metrics
        mockMvc.perform(get("/api/"));
        mockMvc.perform(get("/api/calculate?a=5&b=3"));

        // Check metrics endpoint
        mockMvc.perform(get("/api/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("app2-java-teamB"))
                .andExpect(jsonPath("$.metrics.requests").exists())
                .andExpect(jsonPath("$.metrics.requests.total").isNumber())
                .andExpect(jsonPath("$.metrics.uptime").exists())
                .andExpect(jsonPath("$.metrics.uptime.uptime_seconds").isNumber());
    }

    @Test
    public void testMetricsIncrement() throws Exception {
        // Check initial metrics
        mockMvc.perform(get("/api/metrics"))
                .andExpect(status().isOk());

        // Make a calculate request
        mockMvc.perform(get("/api/calculate?a=10&b=20"))
                .andExpect(status().isOk());

        // Verify metrics incremented
        mockMvc.perform(get("/api/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metrics.requests.calculate").isNumber());
    }
}
