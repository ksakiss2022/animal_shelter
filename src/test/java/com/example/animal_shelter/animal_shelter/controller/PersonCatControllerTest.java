package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.model.PersonCat;
import com.example.animal_shelter.animal_shelter.repository.CatRepository;
import com.example.animal_shelter.animal_shelter.repository.PersonCatRepository;
import com.example.animal_shelter.animal_shelter.service.CatService;
import com.example.animal_shelter.animal_shelter.service.PersonCatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.pro.packaged.P;
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

@WebMvcTest(controllers = PersonCatController.class)
class PersonCatControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonCatRepository personCatRepository;

    @SpyBean
    private PersonCatService personCatService;

    @InjectMocks
    private PersonCatController personCatController;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void createPersonCat()   throws Exception{

        long id = 1;
        String name = "Александр";
        int yearOfBirth = 1980;
        String phone = "8-920-222-22-22";
        String mail = "algmail@.ru";
        String address = "г.Иваново, улица Заречная, 5";
        long chatId= 1;

        JSONObject personCatObject = new JSONObject();

        personCatObject.put("name", name);
        personCatObject.put("yearOfBirth", yearOfBirth);
        personCatObject.put("phone", phone);
        personCatObject.put("mail", mail);
        personCatObject.put("address", address);

        PersonCat personCat = new PersonCat();
        // cat.setId(id);
        personCat.setName(name);
        personCat.setYearOfBirth(yearOfBirth);
        personCat.setPhone(phone);
        personCat.setMail(mail);
        personCat.setAddress(address);

        when(personCatRepository.save(any(PersonCat.class))).thenReturn(personCat);
        when(personCatRepository.findById(id)).thenReturn(Optional.of(personCat));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/person_cats")
                        .content(personCatObject.toString())
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
    void editPersonCat()  throws Exception {
        Long id = Long.valueOf(1);
        String oldName = "Валера";
        int oldYearOfBirth = 1999;
        String oldPhone = "8-000-000-00-00";
        String oldMail = "mm@mail.ru";
        String oldAddress = "г.Тула, улица Ленина, д 55, кв 4";

        String newName = "Вадим";
        int newYearOfBirth = 1899;
        String newPhone = "8-777-000-00-00";
        String newMail = "alery@mail.ru";
        String newAddress = "г.Тула, улица Ленина, д 55, кв 444";


        JSONObject personCatObject = new JSONObject();
        personCatObject.put("id", id);
        personCatObject.put("name", newName);
        personCatObject.put("yearOfBirth", newYearOfBirth);
        personCatObject.put("phone", newPhone);
        personCatObject.put("mail", newMail);
        personCatObject.put("address", newAddress);

        PersonCat personCat = new PersonCat();
        personCat.setId(id);
        personCat.setName(oldName);
        personCat.setYearOfBirth(oldYearOfBirth);
        personCat.setPhone(oldPhone);
        personCat.setMail(oldMail);
        personCat.setAddress(oldAddress);


        PersonCat editPersonCat = new PersonCat();
        editPersonCat.setId(id);
        editPersonCat.setName(newName);
        editPersonCat.setYearOfBirth(newYearOfBirth);
        editPersonCat.setPhone(newPhone);
        editPersonCat.setMail(newMail);
        editPersonCat.setAddress(newAddress);

        when(personCatRepository.findById(any(Long.class))).thenReturn(Optional.of(personCat));
        when(personCatRepository.save(any(PersonCat.class))).thenReturn(editPersonCat);


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/person_cats")
                        .content(personCatObject.toString())
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
    void deletePersonCat() throws Exception {


        long id = 1;
        String name = "Александр";
        int yearOfBirth = 1980;
        String phone = "8-944-222-22-22";
        String mail = "personalex@.ru";
        String address = "г.Кукуево, улица Заречная, 5";
        long chatId= 1;

        PersonCat personCat = new PersonCat();
        personCat.setId(id);
        personCat.setName(name);
        personCat.setYearOfBirth(yearOfBirth);
        personCat.setPhone(phone);
        personCat.setMail(mail);
        personCat.setAddress(address);

        doNothing().when(personCatRepository).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/person_cats/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(personCatRepository, atLeastOnce()).deleteById(id);
    }
    @Test
    void findPersonsCats() throws Exception {
    }

    @Test
    //тест для метода findPersonsCats(), который вернет всех хозяев кошек, если не переданы никакие параметры
    public void shouldReturnAllPersonsCats() throws Exception {
        mockMvc.perform(get("/person_cats"))
                .andExpect(status().isOk());
    }
    @Test
    //тест на пустой список к методу findPersonsCats:
    public void shouldReturnEmptyListIfNoPersonCatsFound() throws Exception {
        mockMvc.perform(get("/person_cats")
                        .param("name", " "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
    @Test
//тест для метода findPersonsCats(), который вернет пустой список, если переданы некорректные параметры:
    public void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/person_cats")
                        .param("name", " ")
                        .param("mail", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}