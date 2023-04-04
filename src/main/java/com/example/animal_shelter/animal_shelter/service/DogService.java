package com.example.animal_shelter.animal_shelter.service;


import com.example.animal_shelter.animal_shelter.model.Dog;
import com.example.animal_shelter.animal_shelter.repository.DogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <b>Сервис DogService предназначен для обработки данных о собаках</b>.
 * Данный класс содержит методы добавления, изменения , удаления собак в базе данных.
 * <i>Далее будут прописаны и другие удобные методы для работы с базами данных</i>.
 */
@Service
public class DogService {
    private final Logger logger = LoggerFactory.getLogger(DogService.class);
    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    /**
     * Метод createDod создает новых <b>собак</b>, внося онформацию о них в базу данных.
     *
     * @param dog параметр со значением данных <b>собаки</b>.
     * @return найденная <b>собака</b>
     */
    public Dog createDod(Dog dog) {
        logger.debug("Creating a new dog:{}", dog);
        final var save = dogRepository.save(dog);
        logger.debug("A new dog{}", save);
        return save;
    }

    /**
     * Метод editDog изменяет уже существующую информацию в базе дынных о <b>собаках</b>.
     *
     * @param dog параметр со значением данных <b>собаки</b>.
     * @return найденная <b>собака</b>.
     */
    public Dog editDog(Dog dog) {
        logger.debug("Edit dog:{}", dog);
        final var dog1 = dogRepository.save(dog);
        logger.debug("Dog (edit) is{}", dog1);
        return dog1;
    }

    /**
     * Метод deleteDog удаляет из базы данных ранее внесенную информацию о <b>собаках</b> в базу данных.
     *
     * @param id идентификатор искомой <b>собаки</b>, <u>не может быть null</u>.
     */
    public void deleteDog(long id) {
        logger.debug("Delete dog:{}", id);
        dogRepository.deleteById(id);
    }
}
