package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.repository.CatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <b>Сервис CatService предназначен для обработки данных о кошка</b>.
 * Данный класс содержит методы добавления, изменения , удаления кошек в базе данных.
 * <i>Далее будут прописаны и другие удобные методы для работы с базами данных</i>.
 */
@Service
public class CatService {
    private final Logger logger = LoggerFactory.getLogger(CatService.class);
    private final CatRepository catRepository;

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    /**
     * Метод createCat создает новых <b>кошек</b>, вносимая онформацию о них в базу данных.
     *
     * @param cat параметр со значением данных <b>кошка</b>.
     * @return найденная <b>кошка</b>.
     */
    public Cat createCat(Cat cat) {
        logger.debug("Creating a new cat:{}", cat);
        final var save = catRepository.save(cat);
        logger.debug("A new cat{}", save);
        return save;
    }

    /**
     * Метод editCat изменяет уже сущест вующую информацию в базе дынных о <b>кошка</b>.
     *
     * @param cat параметр со значением данных <b>кошка</b>.
     * @return найденная <b>кошка</b>.
     */
    public Cat editCat(Cat cat) {
        logger.debug("Edit cat:{}", cat);
        final var cat1 = catRepository.save(cat);
        logger.debug("Cat (edit) is{}", cat1);
        return cat1;
    }

    /**
     * Метод deleteCat удаляет из базы данных ранее внесенную информацию о <b>кошках</b> в базу данных.
     *
     * @param id идентификатор искомой <b>кошки</b>, <u>не может быть null</u>.
     */
    public void deleteCat(long id) {
        logger.debug("Delete cat:{}", id);
        catRepository.deleteById(id);
    }
}
