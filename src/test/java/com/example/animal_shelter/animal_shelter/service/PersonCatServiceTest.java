package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.model.PersonCat;
import com.example.animal_shelter.animal_shelter.repository.PersonCatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonCatServiceTest {
    @Mock
    private Logger logger;

    @InjectMocks
    private PersonCatService personCatService;
    @Mock
    private PersonCatRepository personCatRepository;
    private PersonCat personCat1;

    @BeforeEach
    public void setup() {
        personCat1 = new PersonCat();
        personCat1.setId(1L);
        personCat1.setName("Василий Вячеславовчи Егоров");
        personCat1.setYearOfBirth(1980);
        personCat1.setPhone("8-999-222-33-33");
        personCat1.setMail("egorov@mail.ru");
        personCat1.setAddress("г.Москва, улица Академия Павлова, дом 3, кв. 5");
        //  expectedPersonCat.setChatId("Не любит детей.");
    }

    //    public PersonCat(Long id, String name, int yearOfBirth, String phone, String mail, String address, Long chatId) {
    //        this.id = id;
    //        this.name = name;
    //        this.yearOfBirth = yearOfBirth;
    //        this.phone = phone;
    //        this.mail = mail;
    //        this.address = address;
    //        this.chatId = chatId;
    //
    //    }
    @Test
    void createPersonCat() {
        when(personCatRepository.save(any())).thenReturn(personCat1);
        PersonCat actualPersonCat = personCatService.createPersonCat(personCat1);
        assertEquals(personCat1, actualPersonCat);
    }

    @Test
    public void editPersonCat() {
        Long personCatId = 1L;
        String newPersonName = "Дмитрий";

        PersonCat existingPersonCat = new PersonCat();
        existingPersonCat.setId(personCatId);
        existingPersonCat.setName("Vasya");

        PersonCat updatedPersonCat = new PersonCat();
        updatedPersonCat.setId(personCatId);
        updatedPersonCat.setName(newPersonName);

        when(personCatRepository.findById(personCatId)).thenReturn(Optional.of(existingPersonCat));
        when(personCatRepository.save(updatedPersonCat)).thenReturn(updatedPersonCat);

        PersonCat result = personCatService.editPersonCat(updatedPersonCat);

        assertNotNull(result);
        assertEquals(newPersonName, result.getName());

        verify(personCatRepository, times(1)).save(updatedPersonCat);
    }

    @Test
    public void returnNullWhenNoPersonCatFound() {
        Long personCatId = 1L;

        PersonCat updatedPersonCat = new PersonCat();
        updatedPersonCat.setId(personCatId);
        updatedPersonCat.setName("Dima");

        when(personCatRepository.findById(personCatId)).thenReturn(Optional.empty());

        PersonCat result = personCatService.editPersonCat(updatedPersonCat);

        assertNull(result);
        verify(personCatRepository, never()).save(updatedPersonCat);
    }

    @Test
    public void deleteCatTest() {
        Long personCatId = 1L;


        doNothing().when(personCatRepository).deleteById(personCatId);

        personCatService.deletePersonCat(personCatId);

        verify(personCatRepository, times(1)).deleteById(personCatId);
        //В данном тесте создается идентификатор кота,
        // затем подготавливается мок объект catRepository для метода deleteById()
        // с этим идентификатором. Вызывается deleteCat() метод из catService, затем проверяется,
        // что deleteById() метод был вызван из catRepository объекта.
    }

//    @Test
//    void testGetAllParsonsCats() {}

//    @Test
//    void testEditPersonCat() {
//
//        PersonCatRepository personCatRepo = mock(PersonCatRepository.class);
//        PersonCat personCat = new PersonCat("Fluffy", "5", 1L);
//        when(personCatRepo.findById(personCat.getId())).thenReturn(Optional.of(personCat));
//        when(personCatRepo.save(personCat)).thenReturn(personCat);
//        PersonCatService personCatService = new PersonCatService(personCatRepo);
//
//
//        PersonCat editedPersonCat = personCatService.editPersonCat(personCat);
//
//
//        verify(personCatRepo, times(1)).findById(personCat.getId());
//        verify(personCatRepo, times(1)).save(personCat);
//        assertNotNull(editedPersonCat);
//        assertEquals("Борис Егорович", editedPersonCat.getName());
//        assertEquals("5", editedPersonCat.getPhone());
//        assertEquals(1l, editedPersonCat.getChatId());
//    }
//    @Test
//    public void returnEmptyListWhenNoPersonCatsFoundTest() {
//        String name = "Игорь";
//
//        List<Cat> foundPersonCats = new ArrayList<>();
//
//        when(personCatRepository.findPersonCatByNameContainsIgnoreCase(name)).thenReturn(foundPersonCats);
//
//        Collection<PersonCat> result = personCatService.findPersonsCatByNamePersons(name);
//
//        assertNotNull(result);
//        assertEquals(0, result.size());
//    }
//    @Test
//    void findPersonsCatByNamePersons() {
//        // Создаем тестовые данные
//        String name = "Олег";
//        PersonCat expectedPersonCat = new PersonCat();
//
//        // Задаем поведение репозитория
//        when(personCatRepository.findPersonCatByNameContainsIgnoreCase(name)).thenReturn((Collection<PersonCat>) expectedPersonCat);
//
//        // Вызываем метод сервиса с тестовыми данными
//        PersonCat actualPersonCat = (PersonCat) personCatService.findPersonsCatByNamePersons(name);
//
//        // Проверяем, что результат работы метода соответствует ожиданиям
//        assertEquals(expectedPersonCat, actualPersonCat);
//    }

//    @Test
//    void findPersonCatsByMail() {
//        String mail = "aaa@mail.ru";
//
//        PersonCat personCat1 = new PersonCat();
//        personCat1.setId(1L);
//        personCat1.setName("Василий Вячеславович Егоров");
//        personCat1.setYearOfBirth(1980);
//        personCat1.setPhone("8-999-222-33-33");
//        personCat1.setMail("egorov@mail.ru");
//        personCat1.setAddress("г.Москва, улица Академия Павлова, дом 3, кв. 5");
//
//
//        PersonCat personCat2 = new PersonCat();
//        personCat2.setId(2L);
//        personCat2.setName("Ольга Васильевна Иванова");
//        personCat2.setYearOfBirth(1982);
//        personCat2.setPhone("8-555-222-33-33");
//        personCat2.setMail("olga@mail.ru");
//        personCat2.setAddress("г.Москва, улица Ради, дом 3, кв. 55");
//
//        List<PersonCat> foundPersonCats = List.of(personCat1, personCat2);
//
//        when(personCatRepository.findPersonCatByMailContainsIgnoreCase(mail)).thenReturn((PersonCat) foundPersonCats);
//
//        List<PersonCat> result = (List<PersonCat>) personCatRepository.findPersonCatByMailContainsIgnoreCase(mail);
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertTrue(result.contains(personCat1));
//        assertTrue(result.contains(personCat2));
//    }
}
