package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.Volunteer;
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

    private final Logger logger = LoggerFactory.getLogger(CatService.class);

    private VolunteerRepository volunteerRepository;


    /**
     * В конструкторе VolunteerService() создается пустой список волонтеров.
     *
     * @param volunteerRepository
     */
    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
        volunteers = new ArrayList<>();
    }

    /**
     * Список волонтеров хранится в переменной volunteers типа ArrayList<Volunteer>
     */
    private ArrayList<Volunteer> volunteers;

    public VolunteerService() {

    }


    /**
     * Метод addVolunteer() добавляет нового волонтера в список.
     *
     * @param volunteer параметр со значением данных <b>волонтер</b>
     */
    public Volunteer createVolunteer(Volunteer volunteer) {
        logger.debug("Creating a new volunteer:{}", volunteer);
        final var save = volunteerRepository.save(volunteer);
        logger.debug("A new volunteer{}", save);
        return save;
    }

    /**
     * Метод getVolunteers() возвращает весь список
     * волонтеров для использования в других частях приюта или в телеграм-боте.
     *
     * @return найденный <b>волонтер</b>.
     */

    public Collection<Volunteer> getVolunteers() {
        logger.debug("Collection all volunteer:{}");
        final var all = volunteerRepository.findAll();
        logger.debug("All volunteer is{}", all);
        return all;
    }

    public Collection<Volunteer> findVolunteerByName(String breed) {
        logger.debug("Find volunteer by name:{}", breed);
        final var findVolunteerByNameContainingIgnoreCase = volunteerRepository.findVolunteerByNameContainingIgnoreCase(breed);
        logger.debug("Volunteer by name is{}", findVolunteerByNameContainingIgnoreCase);
        return findVolunteerByNameContainingIgnoreCase;
    }

    public Volunteer findVolunteerByPhone(String phone) {
        logger.debug("Find Volunteer by phone:{}", phone);
        final var findVolunteerByPhoneContainingIgnoreCase = volunteerRepository.findVolunteerByPhoneContainingIgnoreCase(phone);
        logger.debug("Volunteer by phone is{}", findVolunteerByPhoneContainingIgnoreCase);
        return findVolunteerByPhoneContainingIgnoreCase;
    }
}
