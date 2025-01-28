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

    // GET /api/pets
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

    //     POST /api/pets
    @Test
    public void testAddPet_HappyPath() throws Exception {
        Mockito.when(petService.createPet(any(Pet.class))).thenReturn(pet1);

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pet1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Dog"))
                .andExpect(jsonPath("$.breed").value("a"))
                .andExpect(jsonPath("$.age").value(7));
    }

    // PATCH /api/pets/{id}
//    @Test
//    public void testUpdatePetDetails_HappyPath() throws Exception {
//        Mockito.when(petService.updatePet(1L, null))
//                .thenReturn(new Pet("Dog", "a", 7L));
//
//        mockMvc.perform(patch("/api/pets/1")
//                        .param("breed", "c"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Pet details updated successfully."));
//    }

//    @Test
//    public void testUpdatePetDetails_UnhappyPath() throws Exception {
//        Mockito.when(petService.updatePet(10L, null))
//                .thenReturn(null);
//
//        mockMvc.perform(patch("/api/pets/10")
//                        .param("breed", "d"))
//                .andExpect(status().isNotFound());
//    }
}