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
public class CacheControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetCacheStats() throws Exception {
        mockMvc.perform(get("/api/cache/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calculations").exists())
                .andExpect(jsonPath("$.dataProcessing").exists());
    }

    @Test
    public void testClearAllCaches() throws Exception {
        mockMvc.perform(delete("/api/cache/clear"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("All caches cleared"));
    }

    @Test
    public void testClearSpecificCache() throws Exception {
        mockMvc.perform(delete("/api/cache/clear/calculations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Cache 'calculations' cleared"));
    }

    @Test
    public void testClearUnknownCache() throws Exception {
        mockMvc.perform(delete("/api/cache/clear/unknown"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Unknown cache: unknown"));
    }
}
