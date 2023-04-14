package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Dog;
import com.example.animal_shelter.animal_shelter.repository.DogRepository;
import com.example.animal_shelter.animal_shelter.service.DogService;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DogController.class)
public class DogControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DogRepository dogRepository;

    @SpyBean
    private DogService dogService;

    @InjectMocks
    private DogController dogController;

    @Test
    public void createDog() throws Exception {

        final Long id = 1L;
        final String breedDog = "Бигль";
        final String nameDog = "Мелани";
        final int yearOfBirthDog = 2015;
        final String description = "Умеет выполнять команду зайка";


        JSONObject dogObject = new JSONObject();
        dogObject.put("id", id);
        dogObject.put("breedDog", breedDog);
        dogObject.put("nameDog", nameDog);
        dogObject.put("yearOfBirthDog", yearOfBirthDog);
        dogObject.put("description", description);


        Dog dog = new Dog(id, breedDog, nameDog, yearOfBirthDog, description);

        when(dogRepository.save(any(Dog.class))).thenReturn(dog);
        when(dogRepository.findById(any(Long.class))).thenReturn(Optional.of(dog));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/dogs")
                        .content(dogObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.breedDog").value(breedDog))
                .andExpect(jsonPath("$.nameDog").value(nameDog))
                .andExpect(jsonPath("$.yearOfBirthDog").value(yearOfBirthDog))
                .andExpect(jsonPath("$.description").value(description));
    }

    @Test
    public void editDog() throws Exception {
        long id = 1;
        String oldBreedDog = "Охотничья";
        String oldNameDog = "Тузик";
        int oldYearOfBirthDog = 2017;
        String oldDescription = "Спокойная, уравновешенная, любит рыбу.";

        String newBreedDog = "Бигль";
        String newNameDog = "Мелани";
        int newYearOfBirthDog = 2015;
        String newDescription = "Хорошо выполняет команду зайка";

        JSONObject dogObject = new JSONObject();

        dogObject.put("id", id);
        dogObject.put("breed", newBreedDog);
        dogObject.put("nameCat", newNameDog);
        dogObject.put("yearOfBirthCat", newYearOfBirthDog);
        dogObject.put("description", newDescription);

        Dog dog = new Dog(id, oldBreedDog, oldNameDog, oldYearOfBirthDog, oldDescription);

        Dog editDog = new Dog(id, newBreedDog, newNameDog, newYearOfBirthDog, newDescription);

        when(dogRepository.findById(id)).thenReturn(Optional.of(dog));
        when(dogRepository.save(any(Dog.class))).thenReturn(editDog);


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/dogs")
                        .content(dogObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.breedDog").value(newBreedDog))
                .andExpect(jsonPath("$.nameDog").value(newNameDog))
                .andExpect(jsonPath("$.yearOfBirthDog").value(newYearOfBirthDog))
                .andExpect(jsonPath("$.description").value(newDescription));
    }

    @Test
    void deleteDog() throws Exception {
        mockMvc.perform(
                        delete("/dogs/{id}", 1))
                .andExpect(status().isOk());
        verify(dogService).deleteDog(1L);
    }


    @Test
    void findDog() throws Exception {
        when(dogService.getAllDogs()).thenReturn(List.of(new Dog()));

        mockMvc.perform(
                        get("/dogs"))
                .andExpect(status().isOk());
    }

}