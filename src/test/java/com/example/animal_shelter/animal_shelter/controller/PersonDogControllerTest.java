package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.PersonCat;
import com.example.animal_shelter.animal_shelter.model.PersonDog;
import com.example.animal_shelter.animal_shelter.repository.PersonCatRepository;
import com.example.animal_shelter.animal_shelter.repository.PersonDogRepository;
import com.example.animal_shelter.animal_shelter.service.PersonCatService;
import com.example.animal_shelter.animal_shelter.service.PersonDogService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonDogController.class)
class PersonDogControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonDogRepository personDogRepository;

    @SpyBean
    private PersonDogService personDogService;

    @InjectMocks
    private PersonDogController personDogController;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createPersonDog()  throws Exception {
        long id = 1;
        String name = "Никита";
        int yearOfBirth = 2000;
        String phone = "8-920-222-33-33";
        String mail = "nik@.ru";
        String address = "г.Внуково, улица Зеленая, дом 5, кв. 32.";
        long chatId= 1;

        JSONObject personDogObject = new JSONObject();

        personDogObject.put("name", name);
        personDogObject.put("yearOfBirth", yearOfBirth);
        personDogObject.put("phone", phone);
        personDogObject.put("mail", mail);
        personDogObject.put("address", address);

        PersonDog personDog = new PersonDog();

        personDog.setName(name);
        personDog.setYearOfBirth(yearOfBirth);
        personDog.setPhone(phone);
        personDog.setMail(mail);
        personDog.setAddress(address);

        when(personDogRepository.save(any(PersonDog.class))).thenReturn(personDog);
        when(personDogRepository.findById(id)).thenReturn(Optional.of(personDog));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/person_dogs")
                        .content(personDogObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //  .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.yearOfBirth").value(yearOfBirth))
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.mail").value(mail))
                .andExpect(jsonPath("$.address").value(address));
    }

    @Test
    void editPersonDog()  throws Exception {
        Long id = Long.valueOf(1);
        String oldName = "Матвей";
        int oldYearOfBirth = 1988;
        String oldPhone = "8-444-000-00-00";
        String oldMail = "matvey@mail.ru";
        String oldAddress = "г.Подольск, улица Правое дело, д 11, кв 332";

        String newName = "Евегений";
        int newYearOfBirth = 1997;
        String newPhone = "8-666-000-00-00";
        String newMail = "evgesha@mail.ru";
        String newAddress = "г.Нижний новгород, улица Орджоникидзе, д 111, кв 333";


        JSONObject personDogObject = new JSONObject();
        personDogObject.put("id", id);
        personDogObject.put("name", newName);
        personDogObject.put("yearOfBirth", newYearOfBirth);
        personDogObject.put("phone", newPhone);
        personDogObject.put("mail", newMail);
        personDogObject.put("address", newAddress);

        PersonDog personDog = new PersonDog();
        personDog.setId(id);
        personDog.setName(oldName);
        personDog.setYearOfBirth(oldYearOfBirth);
        personDog.setPhone(oldPhone);
        personDog.setMail(oldMail);
        personDog.setAddress(oldAddress);


        PersonDog editPersonDog = new PersonDog();
        editPersonDog.setId(id);
        editPersonDog.setName(newName);
        editPersonDog.setYearOfBirth(newYearOfBirth);
        editPersonDog.setPhone(newPhone);
        editPersonDog.setMail(newMail);
        editPersonDog.setAddress(newAddress);

        when(personDogRepository.findById(any(Long.class))).thenReturn(Optional.of(personDog));
        when(personDogRepository.save(any(PersonDog.class))).thenReturn(editPersonDog);


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/person_dogs")
                        .content(personDogObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.yearOfBirth").value(newYearOfBirth))
                .andExpect(jsonPath("$.phone").value(newPhone))
                .andExpect(jsonPath("$.mail").value(newMail))
                .andExpect(jsonPath("$.address").value(newAddress));
    }

    @Test
    void deletePersonDog()  throws Exception {
    }

    @Test
    void findPersonsDogs()  throws Exception{
    }
}