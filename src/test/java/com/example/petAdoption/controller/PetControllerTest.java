package com.example.petAdoption.controller;

import com.example.petAdoption.model.Pet;
import com.example.petAdoption.service.JWTService;
import com.example.petAdoption.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value =PetController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PetService petService;

    @MockitoBean
    private JWTService jwtService;

    @BeforeEach
    public void setupMocks() {
        Mockito.when(jwtService.validateToken(Mockito.anyString(), Mockito.any(UserDetails.class)))
                .thenReturn(true);
    }

    @Autowired
    private ObjectMapper objectMapper; // For JSON serialization

    private Pet pet1;
    private Pet pet2;

    @BeforeEach
    public void setup() {
        pet1 = new Pet("Dog", "a", 7L);
        pet2 = new Pet("Cat", "b", 10L);
    }

    // GET /api/tables
    @Test
    public void testGetTables_HappyPath() throws Exception {
        Mockito.when(petService.getAllPets()).thenReturn(Arrays.asList(pet1, pet2));

        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("Dog"))
                .andExpect(jsonPath("$[0].breed").value("a"))
                .andExpect(jsonPath("$[0].age").value("7"))
                .andExpect(jsonPath("$[1].type").value("Cat"))
                .andExpect(jsonPath("$[1].breed").value("b"))
                .andExpect(jsonPath("$[1].age").value("10"));
    }

    // PATCH /api/tables/{id}
//    @Test
//    public void testUpdateTableAvailability_HappyPath() throws Exception {
//        Mockito.when(tableService.updateTableAvailability(1L, false))
//                .thenReturn(Optional.of(new TableEntity(1L, "Table 1", 4, false)));
//
//        mockMvc.perform(patch("/api/tables/1")
//                        .param("isAvailable", "false"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Table availability updated successfully."));
//    }

//    @Test
//    public void testUpdateTableAvailability_UnhappyPath() throws Exception {
//        Mockito.when(tableService.updateTableAvailability(999L, false)).thenReturn(Optional.empty());
//
//        mockMvc.perform(patch("/api/tables/999")
//                        .param("isAvailable", "false"))
//                .andExpect(status().isNotFound());
//    }

    // POST /api/tables
//    @Test
//    public void testAddTable_HappyPath() throws Exception {
//        Mockito.when(tableService.addTable(any(TableEntity.class))).thenReturn(table1);
//
//        mockMvc.perform(post("/api/tables")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(table1)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("Table 1"))
//                .andExpect(jsonPath("$.capacity").value(4));
//    }
}