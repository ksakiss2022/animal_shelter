package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Shelter;
import com.example.animal_shelter.animal_shelter.model.TypesShelters;
import com.example.animal_shelter.animal_shelter.repository.ShelterRepository;
import com.example.animal_shelter.animal_shelter.service.ShelterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.pro.packaged.E;
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
import static org.mockito.Mockito.*;
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
        final TypesShelters typeShelter = TypesShelters.DOG_SHELTER;
        final Long id = null;

        JSONObject shelterObject = new JSONObject();
        shelterObject.put("name",name);
        shelterObject.put("information",information);
        shelterObject.put("schedule",schedule);
        shelterObject.put("address",address);
        shelterObject.put("safetyRecommendations",safetyRecommendations);
        //shelterObject.put("id",id);
        shelterObject.put("typeShelter",typeShelter);

        Shelter shelter = new Shelter(name, information,schedule,address,safetyRecommendations, typeShelter);

        when(shelterRepository.save(any(Shelter.class))).thenReturn(shelter);
        when(shelterRepository.findById(any(Long.class))).thenReturn(Optional.of(shelter));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/shelters")
                        .content(shelterObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
               // .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.information").value(information))
                .andExpect(jsonPath("$.schedule").value(schedule))
                .andExpect(jsonPath("$.address").value(address))
                .andExpect(jsonPath("$.safetyRecommendations").value(safetyRecommendations))
                .andExpect(jsonPath("$.typeShelter").value(typeShelter.name()));
    }


    @Test
    void editShelter() throws Exception{

        final String name = "Приют 123";
        final String information = "Тестовый приют для собак";
        final String schedule = "Расписание тестового приюта - с 10 до 18";
        final String address = "Адрес тестового приюта - д.Простоквашино";
        final String safetyRecommendations = "ТБ - осторожность и аккуратность";
        final TypesShelters typesShelters = TypesShelters.DOG_SHELTER;
        final Long id = 1L;

        final String newName = "Приют 245";
        final String newInformation = "Тестовый приют для собак";
        final String newSchedule = "Расписание тестового приюта - с 10 до 18";
        final String newAddress = "Адрес тестового приюта - д.Простоквашино";
        final String newSafetyRecommendations = "ТБ - осторожность и аккуратность";
        final TypesShelters newTypeShelter = TypesShelters.DOG_SHELTER;
        final Long newId = 2L;


        JSONObject shelterObject = new JSONObject();
        shelterObject.put("name",name);
        shelterObject.put("information",information);
        shelterObject.put("schedule",schedule);
        shelterObject.put("address",address);
        shelterObject.put("safetyRecommendations",safetyRecommendations);
        shelterObject.put("typeShelter",typesShelters);
        shelterObject.put("id",id);

        Shelter shelter = new Shelter(id,name, information,schedule,address,safetyRecommendations, typesShelters);
        Shelter newShelter = new Shelter(id, newName, newInformation,newSchedule,newAddress,newSafetyRecommendations, newTypeShelter);

        when(shelterRepository.findById(any(Long.class))).thenReturn(Optional.of(shelter));
        when(shelterRepository.save(any(Shelter.class))).thenReturn(newShelter);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/shelters")
                        .content(shelterObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.information").value(newInformation))
                .andExpect(jsonPath("$.schedule").value(newSchedule))
                .andExpect(jsonPath("$.address").value(newAddress))
                .andExpect(jsonPath("$.safetyRecommendations").value(newSafetyRecommendations))
                .andExpect(jsonPath("$.typeShelter").value(newTypeShelter.name()));


    }

    @Test
    void deleteShelter() throws Exception {
        String name = "Приют 123";
        String information = "Тестовый приют для собак";
        String schedule = "Расписание тестового приюта - с 10 до 18";
        String address = "Адрес тестового приюта - д.Простоквашино";
        String safetyRecommendations = "ТБ - осторожность и аккуратность";
        TypesShelters typesShelters = TypesShelters.DOG_SHELTER;
        Long id = 1L;

        Shelter shelter = new Shelter();
        shelter.setName(name);
        shelter.setInformation(information);
        shelter.setSchedule(schedule);
        shelter.setAddress(address);
        shelter.setSafetyRecommendations(safetyRecommendations);
        shelter.setTypeShelter(typesShelters);

        when(shelterRepository.findById(id)).thenReturn(Optional.of(shelter));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/shelters/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(shelterRepository, atLeastOnce()).deleteById(id);

    }
}