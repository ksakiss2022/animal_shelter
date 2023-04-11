package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Shelter;
import com.example.animal_shelter.animal_shelter.repository.ShelterRepository;
import com.example.animal_shelter.animal_shelter.service.ShelterService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShelterController.class)
class ShelterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShelterRepository shelterRepository;

    @SpyBean
    private ShelterService shelterService;

    @InjectMocks
    private ShelterController shelterController;

    @Test
    void createShelter() throws Exception {

        final String name = "Приют 123";
        final String information = "Тестовый приют для собак";
        final String schedule = "Расписание тестового приюта - с 10 до 18";
        final String address = "Адрес тестового приюта - д.Простоквашино";
        final String safetyRecommendations = "ТБ - осторожность и аккуратность";
        final Long id = null;

        JSONObject shelterObject = new JSONObject();
        shelterObject.put("name",name);
        shelterObject.put("information",information);
        shelterObject.put("schedule",schedule);
        shelterObject.put("address",address);
        shelterObject.put("safetyRecommendations",safetyRecommendations);
        shelterObject.put("id",id);

        Shelter shelter = new Shelter(name, information,schedule,address,safetyRecommendations);

        when(shelterRepository.save(any(Shelter.class))).thenReturn(shelter);
        when(shelterRepository.findById(any(Long.class))).thenReturn(Optional.of(shelter));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/shelters")
                        .content(shelterObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.information").value(information))
                .andExpect(jsonPath("$.schedule").value(schedule))
                .andExpect(jsonPath("$.address").value(address))
                .andExpect(jsonPath("$.safetyRecommendations").value(safetyRecommendations))
        ;
    }


    @Test
    void editShelter() {


    }

    @Test
    void deleteShelter() {


    }
}