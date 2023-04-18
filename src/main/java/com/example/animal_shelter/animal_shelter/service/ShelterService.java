package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.Shelter;
import com.example.animal_shelter.animal_shelter.repository.ShelterRepository;
import liquibase.pro.packaged.S;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
        * <b>Сервис ShelterService предназначен для обработки данных о приюте</b>.
        * Данный класс содержит методы добавления, изменения , удаления приютов в базе данных.
        * <i>Далее будут прописаны и другие удобные методы для удобства обработки и хранения данных</i>.
 */
@Service
public class ShelterService {
    private final Logger logger = LoggerFactory.getLogger(ShelterService.class);
    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    /**
     * Метод createShelter создает новые <b>приюты</b>, внося онформацию о них в базу данных.
     *
     * @param shelter параметр со значением данных <b>приюта</b>.
     * @return найденный <b>приют</b>.
     */
    public Shelter createShelter(Shelter shelter) {
        logger.debug("Creating a new Shelter:{}", shelter);
        final var save = shelterRepository.save(shelter);
        logger.debug("A new Shelter{}", save);
        return save;
    }

    /**
     * Метод editShelter изменяет уже существующую информацию в базе данных о <b>приюте</b>.
     *
     * @param shelter параметр со значением данных <b>приюта</b>.
     * @return найденный <b>приют/b>.
     */
    public Shelter editShelter(Shelter shelter) {
        logger.debug("Edit Shelter:{}", shelter);
        final var shelter1 = shelterRepository.save(shelter);
        logger.debug("Shelter (edit) is{}", shelter1);
        return shelter1;
    }

    /**
     * Метод deleteShelter удаляет из базы данных ранее внесенную информацию о <b>приюте</b> в базу данных.
     *
     * @param id идентификатор искомого <b>приюта</b>, <u>не может быть null</u>.
     */
    public void deleteShelter(long id) {
        logger.debug("Delete Shelter:{}", id);
        shelterRepository.deleteById(id);
    }


    /**
     * Метод getAllShelters выводит список обо всех <b> приютах</b> внесенных в базу данных.
     *
     * @return найденные <b>приюты</b>.
     */
    public Collection<Shelter> getAllShelters() {
        logger.debug("Collection all Shelter:{}");
        final var all = shelterRepository.findAll();
        logger.debug("All persons Shelters are{}", all);
        return all;
    }
    /**
     * Метод findShelter получает найденный <b> приют</b> внесенный в базу данных.
     *
     * @return найденный <b>приют</b>.
     */
    public Shelter findShelter(long id) {
        logger.debug("find Shelter by id");
        return shelterRepository.findById(id).orElse(null);
    }




}
