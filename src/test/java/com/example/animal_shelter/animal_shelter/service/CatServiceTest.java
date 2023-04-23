package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.repository.CatRepository;
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
class CatServiceTest {

    private Logger logger;
    @InjectMocks
    private CatService catService;
    @Mock
    private CatRepository catRepository;
    private Cat expectedCat;

    @BeforeEach
    public void setup() {
        expectedCat = new Cat();
        expectedCat.setId(1L);
        expectedCat.setBreed("Персидская");
        expectedCat.setNameCat("Барсик");
        expectedCat.setYearOfBirthCat(2020);
        expectedCat.setDescription("Не любит детей.");
    }

    @Test
    public void createCatTest() {
        when(catRepository.save(any())).thenReturn(expectedCat);
        Cat actualCat = catService.createCat(expectedCat);
        assertEquals(expectedCat, actualCat);
    }


    @Test
    public void editCatTest() {
        Long catId = 1L;
        String newName = "Мурзик";

        Cat existingCat = new Cat();
        existingCat.setId(catId);
        existingCat.setNameCat("Барсик");

        Cat updatedCat = new Cat();
        updatedCat.setId(catId);
        updatedCat.setNameCat(newName);


        when(catRepository.findById(catId)).thenReturn(Optional.of(existingCat));
        when(catRepository.save(updatedCat)).thenReturn(updatedCat);

        Cat result = catService.editCat(updatedCat);

        assertNotNull(result);
        assertEquals(newName, result.getNameCat());
        assertEquals(existingCat.getBreed(), result.getBreed());
        assertEquals(existingCat.getYearOfBirthCat(), result.getYearOfBirthCat());
        assertEquals(existingCat.getDescription(), result.getDescription());


        verify(catRepository, times(1)).save(updatedCat);
    }

    @Test
    public void returnNullWhenNoCatFoundTest() {
        Long catId = 1L;

        Cat updatedCat = new Cat();
        updatedCat.setId(catId);


        when(catRepository.findById(catId)).thenReturn(Optional.empty());

        Cat result = catService.editCat(updatedCat);

        assertNull(result);


        verify(catRepository, never()).save(updatedCat);
        //В данном тесте происходит создание объектов Cat и проверка изменения имени. Методы mockito when ->
        // then используются для простой имитации репозитория и его методов findById() и save().
        // Также используется метод verify(), который подсчитывает количество вызовов метода save() в репозитории.

    }

    @Test
    public void deleteCatTest() {
        Long catId = 1L;

        // mockito - doNothing() example
        doNothing().when(catRepository).deleteById(catId);

        catService.deleteCat(catId);

        verify(catRepository, times(1)).deleteById(catId);
        //В данном тесте создается идентификатор кота,
        // затем подготавливается мок объект catRepository для метода deleteById()
        // с этим идентификатором. Вызывается deleteCat() метод из catService, затем проверяется,
        // что deleteById() метод был вызван из catRepository объекта.
    }

    @Test
    public void returnAllCatsTest() {
        Cat cat1 = new Cat();
        cat1.setId(1L);
        cat1.setNameCat("Барсик");
        cat1.setBreed("Персидская");
        cat1.setYearOfBirthCat(2019);
        cat1.setDescription("Любит ласку, не любит громкие звуки.");

        Cat cat2 = new Cat();
        cat2.setId(2L);
        cat2.setNameCat("Мурка");
        cat2.setBreed("Шотландская");
        cat2.setYearOfBirthCat(2020);
        cat2.setDescription("Любит детей, не любит одиночество.");

        List<Cat> allCats = new ArrayList<>();
        allCats.add(cat1);
        allCats.add(cat2);

        when(catRepository.findAll()).thenReturn(allCats);

        Collection<Cat> result = catService.getAllCats();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(cat1));
        assertTrue(result.contains(cat2));
    }

    @Test
    public void returnEmptyListWhenNoCatsTest() {
        List<Cat> allCats = new ArrayList<>();

        when(catRepository.findAll()).thenReturn(allCats);

        Collection<Cat> result = catService.getAllCats();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    //В данном тесте подготовлен список всех котов, которые ожидаются при вызове метода findAll()
// из репозитория. Метод when() Mockito используется для имитации результата вызова метода findAll() в rep,
// а метод assertTrue() используется для проверки, что метод getAllCats()
// возвращает список содержащий все созданные объекты Cat. Также в этом тесте проверяется,
// что возвращаемый список корректно обрабатывает ситуацию отсутствия котов в БД.
    @Test
    public void findCatsByBreedTest() {
        String breed = "Персидская";

        Cat cat1 = new Cat();
        cat1.setId(1L);
        cat1.setNameCat("Барсик");
        cat1.setBreed(breed);
        cat1.setYearOfBirthCat(2019);
        cat1.setDescription("Любит ласку, не любит громкие звуки.");

        Cat cat2 = new Cat();
        cat2.setId(2L);
        cat2.setNameCat("Том");
        cat2.setBreed(breed);
        cat2.setYearOfBirthCat(2018);
        cat2.setDescription("Любит спать и есть.");

        List<Cat> foundCats = new ArrayList<>();
        foundCats.add(cat1);
        foundCats.add(cat2);

        when(catRepository.findCatByBreedContainsIgnoreCase(breed)).thenReturn(foundCats);

        Collection<Cat> result = catService.findCatByBreed(breed);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(cat1));
        assertTrue(result.contains(cat2));
    }

    @Test
    public void returnEmptyListWhenNoCatsFoundTest() {
        String breed = "Британская";

        List<Cat> foundCats = new ArrayList<>();

        when(catRepository.findCatByBreedContainsIgnoreCase(breed)).thenReturn(foundCats);

        Collection<Cat> result = catService.findCatByBreed(breed);

        assertNotNull(result);
        assertEquals(0, result.size());
    }
    //В этом тесте создаются объекты Cat, имитируется возращаемое значение метода
    // findCatByBreedContainsIgnoreCase() и вызывается метод findCatByBreed()
    // из catService. Проверяется, что результат метода правильный
    // и содержит только созданных котов. Также проверяется обработка ситуации,
    // когда котов с запрошенной породой не найдено.

    @Test
    public void findCatByNameCatTest() {
        // Создаем тестовые данные
        String name = "Whiskers";
        Cat expectedCat = new Cat();

        // Задаем поведение репозитория
        when(catRepository.findCatByNameCatContainsIgnoreCase(name)).thenReturn(expectedCat);

        // Вызываем метод сервиса с тестовыми данными
        Cat actualCat = catService.findCatByNameCat(name);

        // Проверяем, что результат работы метода соответствует ожиданиям
        assertEquals(expectedCat, actualCat);
    }
    //В данном примере используется фреймворк Mockito
    // для создания мок-объектов репозитория и логгера, а также методы аннотации
    // @BeforeEach для инициализации объектов перед каждым тестом и @Test для обозначения тестового метода.
}