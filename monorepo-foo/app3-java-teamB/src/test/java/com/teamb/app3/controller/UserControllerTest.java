package com.teamb.app3.controller;

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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].username").value("admin"));
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.email").value("admin@example.com"));
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateUser() throws Exception {
        String newUser = "{\"username\":\"newuser\",\"email\":\"new@example.com\",\"fullName\":\"New User\"}";

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testUpdateUser() throws Exception {
        String updatedUser = "{\"username\":\"admin_updated\",\"email\":\"admin_new@example.com\",\"fullName\":\"Admin Updated\"}";

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin_updated"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testHealth() throws Exception {
        mockMvc.perform(get("/api/users/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("healthy"));
    }
}
