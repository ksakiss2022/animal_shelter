package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.model.Volunteer;
import com.example.animal_shelter.animal_shelter.repository.CatRepository;
import com.example.animal_shelter.animal_shelter.repository.VolunteerRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {
    private Logger logger;
    @InjectMocks
    private VolunteerService volunteerService;
    @Mock
    private VolunteerRepository volunteerRepository;
    private Volunteer volunteer1;

    @BeforeEach
    public void setup() {
        volunteer1 = new Volunteer();
        volunteer1.setId(1L);
        volunteer1.setSpatialization("Специализируается на кошках");
        volunteer1.setName("Лада");
        volunteer1.setWorkExperience(20);
        volunteer1.setDescription("Любит кошек.");
    }

    @Test
    public void createVolunteerTest() {
        when(volunteerRepository.save(any())).thenReturn(volunteer1);
        Volunteer actualVolunteer = volunteerService.createVolunteer(volunteer1);
        assertEquals(volunteer1, actualVolunteer);
    }


    @Test
    public void editVolunteerTest() {
        Long volunteerId = 1L;
        String newName = "Петр";

        Volunteer expectedVolunteer = new Volunteer();
        expectedVolunteer.setId(volunteerId);
        expectedVolunteer.setName("Никита");

        Volunteer updatedVolunteer = new Volunteer();
        updatedVolunteer.setId(volunteerId);
        updatedVolunteer.setName(newName);


        when(volunteerRepository.findById(volunteerId)).thenReturn(Optional.of(expectedVolunteer));
        when(volunteerRepository.save(updatedVolunteer)).thenReturn(updatedVolunteer);

        Volunteer result = volunteerService.editVolunteer(updatedVolunteer);

        assertNotNull(result);
        assertEquals(newName, result.getName());
        assertEquals(expectedVolunteer.getSpatialization(), result.getSpatialization());
        assertEquals(expectedVolunteer.getWorkExperience(), result.getWorkExperience());
        assertEquals(expectedVolunteer.getDescription(), result.getDescription());


        verify(volunteerRepository, times(1)).save(updatedVolunteer);
    }

    @Test
    public void returnNullWhenNoVolunteerFoundTest() {
        Long volunteerId = 1L;

        Volunteer updatedVolunteer = new Volunteer();
        updatedVolunteer.setId(volunteerId);


        when(volunteerRepository.findById(volunteerId)).thenReturn(Optional.empty());

        Volunteer result = volunteerService.editVolunteer(updatedVolunteer);

        assertNull(result);


        verify(volunteerRepository, never()).save(updatedVolunteer);
        //В данном тесте происходит создание объектов Cat и проверка изменения имени. Методы mockito when ->
        // then используются для простой имитации репозитория и его методов findById() и save().
        // Также используется метод verify(), который подсчитывает количество вызовов метода save() в репозитории.

    }

    @Test
    public void deleteVolunteerTest() {
        Long volunteerId = 1L;

        // mockito - doNothing() example
        doNothing().when(volunteerRepository).deleteById(volunteerId);

        volunteerService.deleteVolunteer(volunteerId);

        verify(volunteerRepository, times(1)).deleteById(volunteerId);
        //В данном тесте создается идентификатор кота,
        // затем подготавливается мок объект catRepository для метода deleteById()
        // с этим идентификатором. Вызывается deleteCat() метод из catService, затем проверяется,
        // что deleteById() метод был вызван из catRepository объекта.
    }

    @Test
    public void returnAllVolunteersTest() {
        Volunteer volunteer1 = new Volunteer();
        volunteer1.setId(1L);
        volunteer1.setSpatialization("Специализируается на кошках");
        volunteer1.setName("Лада");
        volunteer1.setWorkExperience(20);
        volunteer1.setDescription("Любит кошек.");

        Volunteer volunteer2 = new Volunteer();
        volunteer2.setId(2L);
        volunteer2.setSpatialization("Специализируается на собаках");
        volunteer2.setName("Петр");
        volunteer2.setWorkExperience(10);
        volunteer2.setDescription("Любит собак.");


        List<Volunteer> allVolunteer = new ArrayList<>();
        allVolunteer.add(volunteer1);
        allVolunteer.add(volunteer2);

        when(volunteerRepository.findAll()).thenReturn(allVolunteer);

        Collection<Volunteer> result = volunteerService.getAllVolunteers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(volunteer1));
        assertTrue(result.contains(volunteer2));
    }

    @Test
    public void returnEmptyListWhenNoVolunteersTest() {
        List<Volunteer> allVolunteers = new ArrayList<>();

        when(volunteerRepository.findAll()).thenReturn(allVolunteers);

        Collection<Volunteer> result = volunteerService.getAllVolunteers();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    //В данном тесте подготовлен список всех котов, которые ожидаются при вызове метода findAll()
// из репозитория. Метод when() Mockito используется для имитации результата вызова метода findAll() в rep,
// а метод assertTrue() используется для проверки, что метод getAllCats()
// возвращает список содержащий все созданные объекты Cat. Также в этом тесте проверяется,
// что возвращаемый список корректно обрабатывает ситуацию отсутствия котов в БД.
    @Test
    public void findVolunteersBySpatializationTest() {
        String spatialization = "Специализируется на кошках";

        Volunteer volunteer1 = new Volunteer();
        volunteer1.setId(1L);
        volunteer1.setSpatialization("Специализируается на кошках");
        volunteer1.setName("Лада");
        volunteer1.setWorkExperience(20);
        volunteer1.setDescription("Любит кошек.");

        Volunteer volunteer2 = new Volunteer();
        volunteer2.setId(2L);
        volunteer2.setSpatialization("Специализируается на собаках");
        volunteer2.setName("Петр");
        volunteer2.setWorkExperience(10);
        volunteer2.setDescription("Любит собак.");

        List<Volunteer> foundVolunteers = new ArrayList<>();
        foundVolunteers.add(volunteer1);
        foundVolunteers.add(volunteer2);

        when(volunteerRepository.findVolunteerBySpatializationContainingIgnoreCase(spatialization)).thenReturn(foundVolunteers);

        Collection<Volunteer> result = volunteerService.findVolunteerBySpatialization(spatialization);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(volunteer1));
        assertTrue(result.contains(volunteer2));
    }

    @Test
    public void returnEmptyListWhenNofoundVolunteersFoundTest() {
        String setSpatialization = "Специализация на собаках";

        List<Volunteer> foundVolunteer = new ArrayList<>();

        when(volunteerRepository.findVolunteerBySpatializationContainingIgnoreCase(setSpatialization)).thenReturn(foundVolunteer);

        Collection<Volunteer> result = volunteerService.findVolunteerBySpatialization(setSpatialization);

        assertNotNull(result);
        assertEquals(0, result.size());
    }
    //В этом тесте создаются объекты Cat, имитируется возращаемое значение метода
    // findCatByBreedContainsIgnoreCase() и вызывается метод findCatByBreed()
    // из catService. Проверяется, что результат метода правильный
    // и содержит только созданных котов. Также проверяется обработка ситуации,
    // когда котов с запрошенной породой не найдено.

    @Test
    public void findCatByNameVolunteerTest() {
        // Создаем тестовые данные
        String name = "Глеб";
        Volunteer expectedVolunteer = new Volunteer();

        // Задаем поведение репозитория
        when(volunteerRepository.findVolunteerByNameContainingIgnoreCase(name)).thenReturn(expectedVolunteer);

        // Вызываем метод сервиса с тестовыми данными
        Volunteer actualVolunteer = volunteerService.findVolunteerByName(name);

        // Проверяем, что результат работы метода соответствует ожиданиям
        assertEquals(expectedVolunteer, actualVolunteer);
    }
    //В данном примере используется фреймворк Mockito
    // для создания мок-объектов репозитория и логгера, а также методы аннотации
    // @BeforeEach для инициализации объектов перед каждым тестом и @Test для обоз
}