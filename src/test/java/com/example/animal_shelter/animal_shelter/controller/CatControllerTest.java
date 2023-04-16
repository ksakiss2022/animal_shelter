package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.repository.CatRepository;
import com.example.animal_shelter.animal_shelter.service.CatService;
import com.fasterxml.jackson.databind.ObjectMapper;
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


import java.util.*;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CatController.class)
class CatControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CatRepository catRepository;

    @SpyBean
    private CatService catService;

    @InjectMocks
    private CatController catController;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCat() throws Exception {
        long id = 1;
        String breed = "Сибирская";
        String nameCat = "Голди";
        int yearOfBirthCat = 2020;
        String description = "Спокойная, уровновешенная, любит рыбу.";

        JSONObject catObject = new JSONObject();

        catObject.put("breed", breed);
        catObject.put("nameCat", nameCat);
        catObject.put("yearOfBirthCat", yearOfBirthCat);
        catObject.put("description", description);

        Cat cat = new Cat();
        // cat.setId(id);
        cat.setBreed(breed);
        cat.setNameCat(nameCat);
        cat.setYearOfBirthCat(yearOfBirthCat);
        cat.setDescription(description);

        when(catRepository.save(any(Cat.class))).thenReturn(cat);
        when(catRepository.findById(id)).thenReturn(Optional.of(cat));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cats")
                        .content(catObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //  .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.breed").value(breed))
                .andExpect(jsonPath("$.nameCat").value(nameCat))
                .andExpect(jsonPath("$.yearOfBirthCat").value(yearOfBirthCat))
                .andExpect(jsonPath("$.description").value(description));

    }

    @Test
    void editCat() throws Exception {
        Long id = Long.valueOf(1);
        String oldBreed = "Сибирская";
        String oldNameCat = "Голди";
        int oldYearOfBirthCat = 2020;
        String oldDescription = "Спокойная, уровновешенная, любит рыбу.";

        String newBreed = "Британская";
        String newNameCat = "Муся";
        int newYearOfBirthCat = 2021;
        String newDescription = "Не любитласку, ест исключительно индейку.";

        JSONObject сatObject = new JSONObject();
        сatObject.put("id", id);
        сatObject.put("breed", newBreed);
        сatObject.put("nameCat", newNameCat);
        сatObject.put("yearOfBirthCat", newYearOfBirthCat);
        сatObject.put("description", newDescription);

        Cat cat = new Cat();
        cat.setId(id);
        cat.setBreed(oldBreed);
        cat.setNameCat(oldNameCat);
        cat.setYearOfBirthCat(oldYearOfBirthCat);
        cat.setDescription(oldDescription);


        Cat editCat = new Cat();
        editCat.setId(id);
        editCat.setBreed(newBreed);
        editCat.setNameCat(newNameCat);
        editCat.setYearOfBirthCat(newYearOfBirthCat);
        editCat.setDescription(newDescription);

        when(catRepository.findById(any(Long.class))).thenReturn(Optional.of(cat));
        when(catRepository.save(any(Cat.class))).thenReturn(editCat);


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/cats")
                        .content(сatObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.breed").value(newBreed))
                .andExpect(jsonPath("$.nameCat").value(newNameCat))
                .andExpect(jsonPath("$.yearOfBirthCat").value(newYearOfBirthCat))
                .andExpect(jsonPath("$.description").value(newDescription));
    }

    @Test
    void deleteCat() throws Exception {
        long id = 1;
        String breed = "Сибирская";
        String nameCat = "Голди";
        int yearOfBirthCat = 2020;
        String description = "Спокойная, уровновешенная, любит рыбу.";

        Cat cat = new Cat();
        cat.setId(id);
        cat.setBreed(breed);
        cat.setNameCat(nameCat);
        cat.setYearOfBirthCat(yearOfBirthCat);
        cat.setDescription(description);

        doNothing().when(catRepository).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cats/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(catRepository, atLeastOnce()).deleteById(id);
    }

//    @Test
//    public void findCatt() throws Exception {
//        long id1 = 1;
//        String breed1 = "Персидский кот";
//        int yearOfBirthCat1 = 2019;
//        String description1 = "Лысеет от стресса";
//
//        long id2 = 2;
//        String breed2 = "сиамский кот";
//        int yearOfBirthCat2 = 2023;
//        String description2 = "Игривый котенок";
//
//
//        String nameCat = "Барсик";
//
//        Cat cat1 = new Cat();
//        cat1.setId(id1);
//        cat1.setNameCat(nameCat);
//        cat1.setYearOfBirthCat(yearOfBirthCat1);
//        cat1.setDescription(description1);
//        cat1.setBreed(breed1);
//
//        Cat cat2 = new Cat();
//        cat2.setId(id2);
//        cat2.setNameCat(nameCat);
//        cat2.setYearOfBirthCat(yearOfBirthCat2);
//        cat2.setDescription(description2);
//        cat2.setBreed(breed2);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/cats")
//                        .queryParam("nameCat", nameCat)
//                        // .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(List.of(cat1, cat2))));
//
//
//    }
//    //when(catRepository.findCatByNameCatContainsIgnoreCase(nameCat)).thenReturn((Cat) Set.of(cat1, cat2));
//    //  when(catRepository.findCatByNameCatContainsIgnoreCase(any(String.class))).thenReturn((Cat) Set.of(cat1, cat2));
//
    @Test
    //тест для метода findCat(), который вернет всех кошек, если не переданы никакие параметры
    public void shouldReturnAllCats() throws Exception {
        mockMvc.perform(get("/cats"))
                .andExpect(status().isOk());
    }
    @Test
    //тест на пустой список:
    public void shouldReturnEmptyListIfNoCatsFound() throws Exception {
        mockMvc.perform(get("/cats")
                        .param("nameCat", " "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
    @Test
//тест для метода findCat(), который вернет пустой список, если переданы некорректные параметры:
    public void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/cats")
                        .param("nameCat", " ")
                        .param("breed", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

//    @Test
//    //Напишите тест для метода findCat(), который вернет кошку по кличке, если передан параметр nameCat:
//    public void shouldReturnCatByName() throws Exception {
//        String name = "Васька";
//        mockMvc.perform(get("/cats")
//                        .param("nameCat", name))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.nameCat",is(name)));
//
//    }
//    @Test
//    public void shouldReturnCatByName() throws Exception {
//        String name = "Васька";
//        mockMvc.perform(get("/cats")
//                        .param("nameCat", name))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.nameCat", is(name)))
//                .andExpect(jsonPath("$.age", is(2)))
//                .andExpect(jsonPath("$.breed", is("Шотландская короткошерстная")));
//    }

//@Test
////тест для метода findCat(), который вернет кошку по породе, если передан параметр breed:
//public void shouldReturnCatByBreed() throws Exception {
//    String breed = "Персидская";
//    mockMvc.perform(get("/cats")
//                    .param("breed", breed))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.breed", is(breed)));
//}

}
