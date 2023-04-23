package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.Dog;
import com.example.animal_shelter.animal_shelter.repository.DogRepository;
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
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DogServiceTest {

    private Logger logger;
    @InjectMocks
    private DogService dogService;
    @Mock
    private DogRepository dogRepository;
    private Dog expectedDog;

    @BeforeEach
    public void setup() {
        expectedDog = new Dog();
        expectedDog.setId(1L);
        expectedDog.setBreedDog("Бигль");
        expectedDog.setNameDog("Мелани");
        expectedDog.setYearOfBirthDog(2015);
        expectedDog.setDescription("Зайка");
    }
    @Test
    public void shouldCreateDog() {
        when(dogRepository.save(any())).thenReturn(expectedDog);
        Dog actualDog = dogService.createDod(expectedDog);
        assertEquals(expectedDog, actualDog);
    }


    @Test
    public void shouldEditDog() {
        Long dogId = 1L;
        String newName = "Шарик";

        Dog existingDog = new Dog();
        existingDog.setId(dogId);
        existingDog.setNameDog("Тузик");

        Dog updatedDog = new Dog();
        updatedDog.setId(dogId);
        updatedDog.setNameDog(newName);

        // mockito - when -> then
        when(dogRepository.findById(dogId)).thenReturn(Optional.of(existingDog));
        when(dogRepository.save(updatedDog)).thenReturn(updatedDog);

        Dog result = dogService.editDog(updatedDog);

        assertNotNull(result);
        assertEquals(newName, result.getNameDog());
        assertEquals(existingDog.getBreedDog(), result.getBreedDog());
        assertEquals(existingDog.getYearOfBirthDog(), result.getYearOfBirthDog());
        assertEquals(existingDog.getDescription(), result.getDescription());

        // verify if the repository save method was called
        verify(dogRepository, times(1)).save(updatedDog);
    }

    @Test
    public void shouldReturnNullWhenNoDogFound() {
        Long dogId = 1L;

        Dog updatedDog = new Dog();
        updatedDog.setId(dogId);

        // mockito - when -> then
        when(dogRepository.findById(dogId)).thenReturn(Optional.empty());

        Dog result = dogService.editDog(updatedDog);

        assertNull(result);

        // verify if the repository save method was never called
        verify(dogRepository, never()).save(updatedDog);
        //В данном тесте происходит создание объектов Dog и проверка изменения имени. Методы mockito when ->
        // then используются для простой имитации репозитория и его методов findById() и save().
        // Также используется метод verify(), который подсчитывает количество вызовов метода save() в репозитории.

    }

    @Test
    public void shouldDeleteDog() {
        Long dogId = 1L;

        // mockito - doNothing() example
        doNothing().when(dogRepository).deleteById(dogId);

        dogService.deleteDog(dogId);

        verify(dogRepository, times(1)).deleteById(dogId);
        //В данном тесте создается идентификатор собаки,
        // затем подготавливается мок объект dogRepository для метода deleteById()
        // с этим идентификатором. Вызывается deleteDog() метод из dogService, затем проверяется,
        // что deleteById() метод был вызван из dogRepository объекта.
    }

    @Test
    public void shouldReturnAllDogs() {
        Dog dog1 = new Dog();
        dog1.setId(1L);
        dog1.setNameDog("Мелани");
        dog1.setBreedDog("Бигль");
        dog1.setYearOfBirthDog(2015);
        dog1.setDescription("Зайка");

        Dog dog2 = new Dog();
        dog2.setId(2L);
        dog2.setNameDog("Рекс");
        dog2.setBreedDog("Овчарка");
        dog2.setYearOfBirthDog(2000);
        dog2.setDescription("Комиссар.");

        List<Dog> allDogs = new ArrayList<>();
        allDogs.add(dog1);
        allDogs.add(dog2);

        when(dogRepository.findAll()).thenReturn(allDogs);

        Collection<Dog> result = dogService.getAllDogs();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(dog1));
        assertTrue(result.contains(dog2));
    }

    @Test
    public void shouldReturnEmptyListWhenNoDogs() {
        List<Dog> allDogs = new ArrayList<>();

        when(dogRepository.findAll()).thenReturn(allDogs);

        Collection<Dog> result = dogService.getAllDogs();

        assertNotNull(result);
        assertEquals(0, result.size());
    }
    //В данном тесте подготовлен список всех собак, которые ожидаются при вызове метода findAll()
// из репозитория. Метод when() Mockito используется для имитации результата вызова метода findAll() в rep,
// а метод assertTrue() используется для проверки, что метод getAllDogs()
// возвращает список содержащий все созданные объекты Dog. Также в этом тесте проверяется,
// что возвращаемый список корректно обрабатывает ситуацию отсутствия собак в БД.
    @Test
    public void shouldFindDogsByBreedDog() {
        String breedDog = "Бигль";

        Dog dog1 = new Dog();
        dog1.setId(1L);
        dog1.setNameDog("Мелани");
        dog1.setBreedDog(breedDog);
        dog1.setYearOfBirthDog(2015);
        dog1.setDescription("Зайка.");

        Dog dog2 = new Dog();
        dog2.setId(2L);
        dog2.setNameDog("Рекс");
        dog2.setBreedDog(breedDog);
        dog2.setYearOfBirthDog(2000);
        dog2.setDescription("Комиссар");

        List<Dog> foundDogs = new ArrayList<>();
        foundDogs.add(dog1);
        foundDogs.add(dog2);

        when(dogRepository.findDogByBreedDogContainsIgnoreCase(breedDog)).thenReturn(foundDogs);

        Collection<Dog> result = dogService.findDogByBreed(breedDog);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(dog1));
        assertTrue(result.contains(dog2));
    }

    @Test
    public void shouldReturnEmptyListWhenNoDogsFound() {
        String breedDog = "Бигль";

        List<Dog> foundDogs = new ArrayList<>();

        when(dogRepository.findDogByBreedDogContainsIgnoreCase(breedDog)).thenReturn(foundDogs);

        Collection<Dog> result = dogService.findDogByBreed(breedDog);

        assertNotNull(result);
        assertEquals(0, result.size());
    }
    //В этом тесте создаются объекты Dog, имитируется возращаемое значение метода
    // findDogByBreedDogContainsIgnoreCase() и вызывается метод findDogByBreedDog()
    // из dogService. Проверяется, что результат метода правильный
    // и содержит только созданных собак. Также проверяется обработка ситуации,
    // когда собак с запрошенной породой не найдено.

    @Test
    public void testFindDogByNameDog() {
        // Создаем тестовые данные
        String name = "Melani";
        Dog expectedDog = new Dog();

        // Задаем поведение репозитория
        when(dogRepository.findDogByNameDogContainsIgnoreCase(name)).thenReturn(expectedDog);

        // Вызываем метод сервиса с тестовыми данными
        Dog actualDog = dogService.findDogByNameDog(name);

        // Проверяем, что результат работы метода соответствует ожиданиям
        assertEquals(expectedDog, actualDog);
    }
    //Обратите внимание, что в данном примере используется фреймворк Mockito
    // для создания мок-объектов репозитория и логгера, а также методы аннотации
    // @BeforeEach для инициализации объектов перед каждым тестом и @Test для обозначения тестового метода.
}
