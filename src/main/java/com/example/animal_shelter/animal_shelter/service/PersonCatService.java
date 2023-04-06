package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.Dog;
import com.example.animal_shelter.animal_shelter.model.PersonCat;
import com.example.animal_shelter.animal_shelter.repository.PersonCatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * <b>Сервис PersonCatService предназначен для обработки данных о владельца или потенциальрных владельцах кошек</b>.
 * Данный класс содержит методы добавления, изменения , удаления владельцев кошек в базе данных.
 * <i>Далее будут прописаны и другие удобные методы для удобства обработки и хранения данных</i>.
 */
@Service
public class PersonCatService {
    private final Logger logger = LoggerFactory.getLogger(PersonCatService.class);
    private final PersonCatRepository personCatRepository;

    public PersonCatService(PersonCatRepository personCatRepository) {
        this.personCatRepository = personCatRepository;
    }

    /**
     * Метод createPersonCat создает новых <b>владельцев кошек</b>, внося онформацию о них в базу данных.
     *
     * @param personCat параметр со значением данных <b>владельцев кошек</b>.
     * @return найденный <b>владелец кошки</b>.
     */
    public PersonCat createPersonCat(PersonCat personCat) {
        logger.debug("Creating a new personCat:{}", personCat);
        final var save = personCatRepository.save(personCat);
        logger.debug("A new personCat{}", save);
        return save;
    }

    /**
     * Метод editPersonCat изменяет уже существующую информацию в базе дынных о <b>владельцах кошек</b>.
     *
     * @param personCat параметр со значением данных <b>владельцев кошек</b>.
     * @return найденный <b>владелец кошки</b>.
     */
    public PersonCat editPersonCat(PersonCat personCat) {
        logger.debug("Edit personCat:{}", personCat);
        if (personCatRepository.findById(personCat.getId()).isPresent()) {
        final var personCat1 = personCatRepository.save(personCat);
        logger.debug("PersonCat (edit) is{}", personCat1);
        return personCat1;
        } else {
            logger.debug("No person cat found with id {}", personCat.getId());
            return null;
        }
    }

    /**
     * Метод deletePersonCat удаляет из базы данных ранее внесенную информацию о <b>владельце кошек</b> в базу данных.
     *
     * @param id идентификатор искомого <b>владельца кошки</b>, <u>не может быть null</u>.
     */
    public void deletePersonCat(long id) {
        logger.debug("Delete personCat:{}", id);
        personCatRepository.deleteById(id);
    }


    /**
     * Метод getAllParsonsCats выводит список обо всех <b> хозяевах кошук</b> внесенных в базу данных.
     *
     * @return найденные <b>хозяева кошек</b>.
     */
    public Collection<PersonCat> getAllParsonsCats() {
        logger.debug("Collection all persons cats:{}");
        final var all = personCatRepository.findAll();
        logger.debug("All persons cats is{}", all);
        return all;
    }

    /**
     * Метод findPersonsCatByNamePersons ищет хозяев кошек по имени хозяина.
     *
     * @param name параметр со значением данных <b>имя хозяина кошки</b>.
     * @return найденные <b>именя хозяев кошек</b>.
     */
    public Collection<PersonCat> findPersonsCatByNamePersons(String name) {
        logger.debug("Find persons cat by breed:{}", name);
        final var findPersonCatByNameContainsIgnoreCase = personCatRepository.findPersonCatByNameContainsIgnoreCase(name);
        logger.debug("Persons cat by breed is{}", findPersonCatByNameContainsIgnoreCase);
        return findPersonCatByNameContainsIgnoreCase;
    }

    /**
     * Метод findPersonsCatByMail ищет хозяев кошек по адресу mail.
     *
     * @param mail параметр со значением данных <b>@mail</b>.
     * @return найденные <b>хозяева кошек по @mail]</b>.
     */
    public PersonCat findPersonsCatByMail(String mail) {
        logger.debug("Find persons cat by mail:{}", mail);
        final var findPersonCatByMailContainsIgnoreCase = personCatRepository.findPersonCatByMailContainsIgnoreCase(mail);
        logger.debug("Persons cats by mail is{}", findPersonCatByMailContainsIgnoreCase);
        return findPersonCatByMailContainsIgnoreCase;
    }
}
