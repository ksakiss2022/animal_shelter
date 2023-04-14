package com.example.animal_shelter.animal_shelter.service;


import com.example.animal_shelter.animal_shelter.model.PersonCat;
import com.example.animal_shelter.animal_shelter.model.PersonDog;
import com.example.animal_shelter.animal_shelter.repository.PersonDogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
        if (personDogRepository.findById(personDog.getId()).isPresent()) {
        final var personDog1 = personDogRepository.save(personDog);
        logger.debug("PersonDog (edit) is{}", personDog1);
        return personDog1;
        } else {
            logger.debug("No person person dog found with id {}", personDog.getId());
            return null;
        }
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

    /**
     * Метод getAllParsonsDogs выводит список обо всех <b> хозяевах собак</b> внесенных в базу данных.
     *
     * @return найденные <b>хозяева собак</b>.
     */
    public Collection<PersonDog> getAllParsonsDogs() {
        logger.debug("Collection all persons dogs:{}");
        final var all = personDogRepository.findAll();
        logger.debug("All persons dogs is{}", all);
        return all;
    }

    /**
     * Метод findPersonsDogByNamePersons ищет хозяев собак по имени хозяина.
     *
     * @param name параметр со значением данных <b>имя хозяина собаки</b>.
     * @return найденные <b>именя хозяев собак</b>.
     */
    public Collection<PersonDog> findPersonsDogByNamePersons(String name) {
        logger.debug("Find persons dog by breed:{}", name);
        final var findPersonDogByNameContainsIgnoreCase = personDogRepository.findPersonDogByNameContainsIgnoreCase(name);
        logger.debug("Persons dog by breed is{}", findPersonDogByNameContainsIgnoreCase);
        return findPersonDogByNameContainsIgnoreCase;
    }

    /**
     * Метод findPersonsDogByMail ищет хозяев собак по адресу mail.
     *
     * @param mail параметр со значением данных <b>@mail</b>.
     * @return найденные <b>хозяева собак по @mail]</b>.
     */
    public PersonDog findPersonsDogByMail(String mail) {
        logger.debug("Find persons dog by mail:{}", mail);
        final var findPersonDogByMailContainsIgnoreCase = personDogRepository.findPersonDogByMailContainsIgnoreCase(mail);
        logger.debug("Persons cats by mail is{}", findPersonDogByMailContainsIgnoreCase);
        return findPersonDogByMailContainsIgnoreCase;
    }
}
