package com.example.animal_shelter.animal_shelter.service;


import com.example.animal_shelter.animal_shelter.model.PersonCat;
import com.example.animal_shelter.animal_shelter.repository.PersonCatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        final var personCat1 = personCatRepository.save(personCat);
        logger.debug("PersonCat (edit) is{}", personCat1);
        return personCat1;
    }

    /**
     * Метод deletePersonCat удаляет из базы данных ранее внесенную информацию о <b>владельце кошек</b> в базу данных.
     * @param id идентификатор искомого <b>владельца кошки</b>, <u>не может быть null</u>.
     */
    public void deletePersonCat(long id) {
        logger.debug("Delete personCat:{}",id);
        personCatRepository.deleteById(id);
    }
}
