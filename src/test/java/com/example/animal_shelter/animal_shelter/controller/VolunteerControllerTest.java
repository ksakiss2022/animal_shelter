package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.model.Volunteer;
import com.example.animal_shelter.animal_shelter.repository.CatRepository;
import com.example.animal_shelter.animal_shelter.repository.VolunteerRepository;
import com.example.animal_shelter.animal_shelter.service.CatService;
import com.example.animal_shelter.animal_shelter.service.VolunteerService;
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

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VolunteerController.class)
class VolunteerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VolunteerRepository volunteerRepository;

    @SpyBean
    private VolunteerService volunteerService;

    @InjectMocks
    private VolunteerController volunteerController;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createVolunteer() throws Exception {
        long id = 1;
        String spatialization = "Специализируется на кошках";
        String name = "Игорь";
        int workExperience = 5;
        String description = "Большой опыт в подборе достойного питомца, подходящего по характеру и внешнему виду к новым хозяевам.";

        JSONObject volunteerObject = new JSONObject();

        volunteerObject.put("spatialization", spatialization);
        volunteerObject.put("name", name);
        volunteerObject.put("workExperience", workExperience);
        volunteerObject.put("description", description);

        Volunteer volunteer = new Volunteer();

        volunteer.setSpatialization(spatialization);
        volunteer.setName(name);
        volunteer.setWorkExperience(workExperience);
        volunteer.setDescription(description);

        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);
        when(volunteerRepository.findById(id)).thenReturn(Optional.of(volunteer));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/volunteers")
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //  .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.spatialization").value(spatialization))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.workExperience").value(workExperience))
                .andExpect(jsonPath("$.description").value(description));

    }

    @Test
    void editVolunteer() throws Exception {
        Long id = Long.valueOf(1);
        String oldSpatialization = "Специализируается на собаках";
        String oldName = "Глеб";
        int oldWorkExperience = 15;
        String oldDescription = "Отлично подбирает питание, и уход к новым питомцам.";

        String newSpatialization = "Специализируется на кошках";
        String newName = "Мария";
        int newWorkExperience = 11;
        String newDescription = "Знает все о кошках,уходе за ними.";

        JSONObject volunteerObject = new JSONObject();
        volunteerObject.put("id", id);
        volunteerObject.put("spatialization", newSpatialization);
        volunteerObject.put("name", newName);
        volunteerObject.put("workExperience", newWorkExperience);
        volunteerObject.put("description", newDescription);

        Volunteer volunteer = new Volunteer();
        volunteer.setId(id);
        volunteer.setName(oldName);
        volunteer.setSpatialization(oldSpatialization);
        volunteer.setWorkExperience(oldWorkExperience);
        volunteer.setDescription(oldDescription);


        Volunteer editVolunteer = new Volunteer();
        editVolunteer.setId(id);
        editVolunteer.setName(newName);
        editVolunteer.setSpatialization(newSpatialization);
        editVolunteer.setWorkExperience(newWorkExperience);
        editVolunteer.setDescription(newDescription);

        when(volunteerRepository.findById(any(Long.class))).thenReturn(Optional.of(volunteer));
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(editVolunteer);


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/volunteers")
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.spatialization").value(newSpatialization))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.workExperience").value(newWorkExperience))
                .andExpect(jsonPath("$.description").value(newDescription));
    }

    @Test
    void deleteVolunteer() throws Exception {
        long id = 1;
        String spatialization = "Специализируется на кошках";
        String name = "Игорь";
        int workExperience = 5;
        String description = "Большой опыт в подборе достойного питомца, подходящего по характеру и внешнему виду к новым хозяевам.";

        Volunteer volunteer = new Volunteer();
        volunteer.setId(id);
        volunteer.setSpatialization(spatialization);
        volunteer.setName(name);
        volunteer.setWorkExperience(workExperience);
        volunteer.setDescription(description);

        doNothing().when(volunteerRepository).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/volunteers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(volunteerRepository, atLeastOnce()).deleteById(id);
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
    public void shouldReturnAllVolunteers() throws Exception {
        mockMvc.perform(get("/volunteers"))
                .andExpect(status().isOk());
    }
    @Test
    //тест на пустой список:
    public void shouldReturnEmptyListIfNoVolunteerFound() throws Exception {
        mockMvc.perform(get("/volunteers")
                        .param("name", " "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
    @Test
//тест для метода findCat(), который вернет пустой список, если переданы некорректные параметры:
    public void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/volunteers")
                        .param("name", " ")
                        .param("spatialization", ""))
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