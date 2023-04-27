package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.model.Volunteer;
import com.example.animal_shelter.animal_shelter.repository.CatRepository;
import com.example.animal_shelter.animal_shelter.repository.VolunteerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


/**
 * <b>Сервис ShelterVolunteer предназначен для обработки данных о волонтерах приюта</b>.
 * Данный класс содержит список волонтеров в переменной volunteers,
 * создается пустой список волонтеров, с возможностью добавля нового волонтера в список.
 * Также возвращает весь список волонтеров для использования в других частях приюта или в телеграм-боте.
 * <i>Далее будут прописаны и другие удобные методы для работы с базами данных</i>.
 */
@Service
public class VolunteerService {

    private final Logger logger = LoggerFactory.getLogger(VolunteerService.class);
    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }



    /**
     * Метод createVolunteer создает новых <b>волонтеров</b>, вносимая онформацию о них в базу данных.
     *
     * @param volunteer параметр со значением данных <b>волонтер</b>.
     * @return найденная <b>волонтер</b>.
     */
    public Volunteer createVolunteer(Volunteer volunteer) {
        logger.debug("Creating a new volunteer:{}", volunteer);
        final var save = volunteerRepository.save(volunteer);
        logger.debug("A new volunteer{}", save);
        return save;
    }

    /**
     * Метод editVolunteer изменяет уже сущест вующую информацию в базе дынных о <b>волонтерах</b>.
     *
     * @param volunteer параметр со значением данных <b>волонтер</b>.
     * @return найденная <b>волонтер</b>.
     */
    public Volunteer editVolunteer(Volunteer volunteer) {
        logger.debug("Edit volunteer:{}", volunteer);
        if(volunteerRepository.findById(volunteer.getId()).isPresent()) {
            final var volunteer1 = volunteerRepository.save(volunteer);
            logger.debug("Volunteer (edit) is{}", volunteer1);
            return volunteer1;
        } else {
            logger.debug("No volunteer found with id {}", volunteer.getId());
            return null;
        }
    }
    /**
     * Метод deleteVolunteer удаляет из базы данных ранее внесенную информацию о <b>волонтерах</b> в базу данных.
     *
     * @param id идентификатор искомого <b>волонтера</b>, <u>не может быть null</u>.
     * @return
     */
    public Volunteer deleteVolunteer(long id) {
        logger.debug("Delete volunteer:{}", id);
        volunteerRepository.deleteById(id);
        return null;
    }

    /**
     * Метод getAllVolunteers выводит список обо всех <b>волнтерах</b> внесенных в базу данных.
     * @return найденные <b>волонтеры</b>.
     */
    public Collection<Volunteer> getAllVolunteers() {
        logger.debug("Collection all volunteers:{}");
        final var all = volunteerRepository.findAll();
        logger.debug("All volunteers is{}", all);
        return all;
    }

    /**
     * Метод findVolunteerBySpatialization ищет волонтеров по породе.
     * @paramm spatialization параметр со значением данных <b>специализация</b>.
     * @return найденные <b>нужная специализация</b>.
     */
    public Collection<Volunteer> findVolunteerBySpatialization(String spatialization) {
        logger.debug("Find volunteer by spatialization:{}",spatialization);
        final var findVolunteerBySpatializationContainingIgnoreCase = volunteerRepository.findVolunteerBySpatializationContainingIgnoreCase(spatialization);
        logger.debug("Volunteer by breed is{}", findVolunteerBySpatializationContainingIgnoreCase);
        return findVolunteerBySpatializationContainingIgnoreCase;
    }

    /**
     * Метод findVolunteerByName ищет волонтеров по имени.
     * @param name параметр со значением данных <b>имя</b>.
     * @return найденные <b>волонтеры по имени</b>.
     */
    public Volunteer findVolunteerByName(String name) {
        logger.debug("Find Volunteer by name:{}",name);
        final var findVolunteerByNameContainingIgnoreCase = volunteerRepository.findVolunteerByNameContainingIgnoreCase(name);
        logger.debug("Volunteer by name is{}", findVolunteerByNameContainingIgnoreCase);
        return findVolunteerByNameContainingIgnoreCase;
    }
}
