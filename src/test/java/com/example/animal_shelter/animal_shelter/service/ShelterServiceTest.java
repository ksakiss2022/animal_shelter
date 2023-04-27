package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.controller.ShelterController;
import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.model.DocumentCat;
import com.example.animal_shelter.animal_shelter.model.Shelter;
import com.example.animal_shelter.animal_shelter.model.TypesShelters;
import com.example.animal_shelter.animal_shelter.repository.ShelterRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ShelterServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private ShelterRepository shelterRepository;
    @InjectMocks
    private ShelterService shelterService;

    @Test
    void createShelter() throws Exception {

        final String name = "Приют 123";
        final String information = "Тестовый приют для собак";
        final String schedule = "Расписание тестового приюта - с 10 до 18";
        final String address = "Адрес тестового приюта - д.Простоквашино";
        final String safetyRecommendations = "ТБ - осторожность и аккуратность";
        final TypesShelters typeShelter = TypesShelters.DOG_SHELTER;
        final Long id = null;

        Shelter shelter = new Shelter(name,information,schedule,address,safetyRecommendations,typeShelter);

        when(shelterRepository.save(any(Shelter.class))).thenReturn(shelter);

        Shelter created = shelterService.createShelter(shelter);
        assertNotNull(created);
        assertEquals(shelter.getName(), created.getName());
        assertEquals(shelter.getInformation(), created.getInformation());
        assertEquals(shelter.getSchedule(), created.getSchedule());
        assertEquals(shelter.getAddress(), created.getAddress());
        assertEquals(shelter.getSafetyRecommendations(), created.getSafetyRecommendations());
        assertEquals(shelter.getTypeShelter(), created.getTypeShelter());
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

        Shelter shelter = new Shelter(id,name, information,schedule,address,safetyRecommendations, typesShelters);
        Shelter newShelter = new Shelter(id, newName, newInformation,newSchedule,newAddress,newSafetyRecommendations, newTypeShelter);

        when(shelterRepository.save(shelter)).thenReturn(shelter);
        when(shelterRepository.save(newShelter)).thenReturn(newShelter);

        Shelter created = shelterService.createShelter(shelter);
        Shelter created2 = shelterService.createShelter(newShelter);

        assertNotNull(created2);
        assertNotNull(created);
        verify(shelterRepository, times(1)).save(newShelter);


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

        doNothing().when(shelterRepository).deleteById(id);
        shelterService.deleteShelter(id);
        verify(shelterRepository, atLeastOnce()).deleteById(id);
    }

    @Test
    void getAllShelters() throws Exception{
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

        Shelter shelter = new Shelter(id,name, information,schedule,address,safetyRecommendations, typesShelters);
        Shelter shelter2 = new Shelter(id, newName, newInformation,newSchedule,newAddress,newSafetyRecommendations, newTypeShelter);


        List<Shelter> allShelters = new ArrayList<>();
        allShelters.add(shelter);
        allShelters.add(shelter2);

        when(shelterRepository.findAll()).thenReturn(allShelters);

        Collection<Shelter> result = shelterService.getAllShelters();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(shelter));
        assertTrue(result.contains(shelter2));
    }

    @Test
    void findShelter() throws Exception{
        final String name = "Приют 123";
        final String information = "Тестовый приют для собак";
        final String schedule = "Расписание тестового приюта - с 10 до 18";
        final String address = "Адрес тестового приюта - д.Простоквашино";
        final String safetyRecommendations = "ТБ - осторожность и аккуратность";
        final TypesShelters typeShelter = TypesShelters.DOG_SHELTER;
        final Long id = 1L;

        Shelter shelter1= new Shelter(name,information,schedule,address,safetyRecommendations,typeShelter);

        when(shelterRepository.findById(id)).thenReturn(Optional.of(shelter1));

        Shelter result = shelterService.findShelter(id);

        assertNotNull(result);

        assertEquals(result, shelter1);


    }
}