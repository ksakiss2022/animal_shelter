package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.PersonCat;
import com.example.animal_shelter.animal_shelter.model.PersonDog;
import com.example.animal_shelter.animal_shelter.repository.PersonCatRepository;
import com.example.animal_shelter.animal_shelter.repository.PersonDogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonDogServiceTest {
    @Mock
    private Logger logger;

    @InjectMocks
    private PersonDogService personDogService;
    @Mock
    private PersonDogRepository personDogRepository;
    private PersonDog expectedPersonDog;

    @BeforeEach
    public void setup() {
        expectedPersonDog = new PersonDog();
        expectedPersonDog.setId(1L);
        expectedPersonDog.setName("Василий Вячеславовчи Егоров");
        expectedPersonDog.setYearOfBirth(1980);
        expectedPersonDog.setPhone("8-999-222-33-33");
        expectedPersonDog.setMail("egorov@mail.ru");
        expectedPersonDog.setAddress("г.Москва, улица Академия Павлова, дом 3, кв. 5");
    }

    @Test
    void createPersonDog() {
        when(personDogRepository.save(any())).thenReturn(expectedPersonDog);
        PersonDog actualPersonDog = personDogService.createPersonDog(expectedPersonDog);
        assertEquals(expectedPersonDog, actualPersonDog);
    }

    @Test
    void editPersonDog() {
        Long personDogId = 1L;
        String newPersonName = "Дмитрий";

        PersonDog existingPersonCat = new PersonDog();
        existingPersonCat.setId(personDogId);
        existingPersonCat.setName("Василий");

        PersonDog updatedPersonDog = new PersonDog();
        updatedPersonDog.setId(personDogId);
        updatedPersonDog.setName(newPersonName);

        when(personDogRepository.findById(personDogId)).thenReturn(Optional.of(existingPersonCat));
        when(personDogRepository.save(updatedPersonDog)).thenReturn(updatedPersonDog);

        PersonDog result = personDogService.editPersonDog(updatedPersonDog);

        assertNotNull(result);
        assertEquals(newPersonName, result.getName());

        verify(personDogRepository, times(1)).save(updatedPersonDog);
    }

    @Test
    void deletePersonDog() {
        Long personeDogId = 1L;

        // mockito - doNothing() example
        doNothing().when(personDogRepository).deleteById(personeDogId);

        personDogService.deletePersonDog(personeDogId);

        verify(personDogRepository, times(1)).deleteById(personeDogId);
        //В данном тесте создается идентификатор кота,
        // затем подготавливается мок объект catRepository для метода deleteById()
        // с этим идентификатором. Вызывается deleteCat() метод из catService, затем проверяется,
        // что deleteById() метод был вызван из catRepository объекта.
    }

    @Test
    public void returnNullWhenNoPersonDogFound() {
        Long personDogId = 1L;

        PersonDog updatedPersonDog = new PersonDog();
        updatedPersonDog.setId(personDogId);
        updatedPersonDog.setName("Дмитрий");

        when(personDogRepository.findById(personDogId)).thenReturn(Optional.empty());

        PersonDog result = personDogService.editPersonDog(updatedPersonDog);

        assertNull(result);
        verify(personDogRepository, never()).save(updatedPersonDog);
    }
//    @Test
//    void getAllParsonsDogs() {
//    }
//
//    @Test
//    void findPersonsDogByNamePersons() {
//    }
//
//    @Test
//    void findPersonsDogByMail() {
//    }
}
