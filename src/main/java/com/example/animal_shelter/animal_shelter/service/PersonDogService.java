package com.example.animal_shelter.animal_shelter.service;


import com.example.animal_shelter.animal_shelter.model.PersonDog;
import com.example.animal_shelter.animal_shelter.repository.PersonDogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <b>Сервис PersonDogService предназначен для обработки данных о владельца или
 * потенциальрных владельцах собак</b>.
 * Данный класс содержит методы добавления, изменения , удаления владельцев собак в базе данных.
 * <i>Далее будут прописаны и другие удобные методы для удобства обработки и хранения данных</i>.
 */
@Service
public class PersonDogService {
    private final Logger logger = LoggerFactory.getLogger(PersonDogService.class);
    private final PersonDogRepository personDogRepository;

    public PersonDogService(PersonDogRepository personDogRepository) {
        this.personDogRepository = personDogRepository;
    }

    /**
     * Метод createPersonDog создает новых <b>владельцев собак</b>, внося онформацию о них в базу данных.
     *
     * @param personDog параметр со значением данных <b>владельцев собак</b>.
     * @return найденный <b>владелец собаки</b>.
     */
    public PersonDog createPersonDog(PersonDog personDog) {
        logger.debug("Creating a new personDog:{}", personDog);
        final var save = personDogRepository.save(personDog);
        logger.debug("A new personDog{}", save);
        return save;
    }

    /**
     * Метод editPersonDog изменяет уже существующую информацию в базе дынных о <b>владельцах собак</b>.
     *
     * @param personDog параметр со значением данных <b>владельцев собак</b>.
     * @return найденный <b>владелец собаки</b>.
     */
    public PersonDog editPersonDog(PersonDog personDog) {
        logger.debug("Edit personCat:{}", personDog);
        final var personDog1 = personDogRepository.save(personDog);
        logger.debug("PersonDog (edit) is{}", personDog1);
        return personDog1;
    }

    /**
     * Метод deletePersonDog удаляет из базы данных ранее внесенную информацию о <b>владельце собак</b> в базу данных.
     *
     * @param id идентификатор искомого <b>владельца собаки</b>, <u>не может быть null</u>.
     */
    public void deletePersonDog(long id) {
        logger.debug("Delete personDog:{}", id);
        personDogRepository.deleteById(id);
    }
}
