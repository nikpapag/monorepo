package com.teamb.app6.controller;

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
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Laptop"));
    }

    @Test
    public void testGetProductById() throws Exception {
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.category").value("Electronics"));
    }

    @Test
    public void testGetProductByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateProduct() throws Exception {
        String newProduct = "{\"name\":\"Keyboard\",\"description\":\"Mechanical keyboard\",\"price\":99.99,\"category\":\"Electronics\"}";

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newProduct))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Keyboard"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        String updatedProduct = "{\"name\":\"Laptop Pro\",\"description\":\"Ultra high-performance laptop\",\"price\":1999.99,\"category\":\"Electronics\"}";

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedProduct))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop Pro"));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/products/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetProductsByCategory() throws Exception {
        mockMvc.perform(get("/api/products/category/Electronics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].category").value("Electronics"));
    }

    @Test
    public void testHealth() throws Exception {
        mockMvc.perform(get("/api/products/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("healthy"));
    }
}
