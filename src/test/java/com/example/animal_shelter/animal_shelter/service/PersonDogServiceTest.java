package com.example.animal_shelter.animal_shelter.service;


import com.example.animal_shelter.animal_shelter.model.PersonDog;
import com.example.animal_shelter.animal_shelter.repository.PersonDogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    void testEditPersonDog() {

        PersonDogRepository PersonDogRepository1 = mock(PersonDogRepository.class);
        PersonDog personDog = new PersonDog("Борис Иванович", "4444", 3L);
        when(PersonDogRepository1.findById(personDog.getId())).thenReturn(Optional.of(personDog));
        when(PersonDogRepository1.save(personDog)).thenReturn(personDog);
        PersonDogService personDogService = new PersonDogService(PersonDogRepository1);


        PersonDog editedPersonDog = personDogService.editPersonDog(personDog);


        verify(PersonDogRepository1, times(1)).findById(personDog.getId());
        verify(PersonDogRepository1, times(1)).save(personDog);
        assertNotNull(editedPersonDog);
        assertEquals("Борис Иванович", editedPersonDog.getName());
        assertEquals("4444", editedPersonDog.getPhone());
        assertEquals(3l, editedPersonDog.getChatId());
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

    @Test
    public void returnEmptyListWhenNoPersonDogsFoundTest() {
        String name = "Игорь";

        List<PersonDog> foundPersonDogs = new ArrayList<>();

        when(personDogRepository.findPersonDogByNameContainsIgnoreCase(name)).thenReturn(foundPersonDogs);

        Collection<PersonDog> result = personDogService.findPersonsDogByNamePersons(name);

        assertNotNull(result);
        assertEquals(0, result.size());
    }
    @Test
    void getAllPersonsDogs() {
        PersonDog personDog1 = new PersonDog();
        personDog1.setId(1L);
        personDog1.setName("Маргарита");
        personDog1.setYearOfBirth(1987);
        personDog1.setPhone("8-999-999-00-00");
        personDog1.setMail("margo@gmail.ru");
        personDog1.setAddress("Москва");

        PersonDog personDog2 = new PersonDog();
        personDog2.setId(2L);
        personDog2.setName("Николай");
        personDog2.setYearOfBirth(1980);
        personDog2.setPhone("8-333-999-00-00");
        personDog2.setMail("nikolaq@gmail.ru");
        personDog2.setAddress("Санкт-Петербург");

        List<PersonDog> personDogs = new ArrayList<>();
        personDogs.add(personDog1);
        personDogs.add(personDog2);

        when(personDogRepository.findAll()).thenReturn(personDogs);

        Collection<PersonDog> result = personDogService.getAllParsonsDogs();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(personDog1));
        assertTrue(result.contains(personDog2));
    }
@Test
void findPersonsDogByNamePersons() {
    // Создаем тестовые данные
    String name = "Олег";
    PersonDog expectedPersonDog = new PersonDog();
    List<PersonDog> expectedPersonDogList = new ArrayList<>();
    expectedPersonDogList.add(expectedPersonDog);

    // Задаем поведение репозитория
    when(personDogRepository.findPersonDogByNameContainsIgnoreCase(name)).thenReturn(expectedPersonDogList);

    // Вызываем метод сервиса с тестовыми данными
    Collection<PersonDog> actualPersonDogList = personDogService.findPersonsDogByNamePersons(name);

    // Проверяем, что результат работы метода соответствует ожиданиям
    assertEquals(expectedPersonDogList, actualPersonDogList);
}

    @Test
    void findPersonsDogByMail() {

    // Создаем тестовые данные
    String mail = "Дина";
    PersonDog expectedPersonDog = new PersonDog();

    // Задаем поведение репозитория
    when(personDogRepository.findPersonDogByMailContainsIgnoreCase(mail)).thenReturn(expectedPersonDog);

    // Вызываем метод сервиса с тестовыми данными
    PersonDog actualPersonDog = personDogService.findPersonsDogByMail(mail);

    // Проверяем, что результат работы метода соответствует ожиданиям
    assertEquals(expectedPersonDog, actualPersonDog);
}

}
