package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.BotUser;
import com.example.animal_shelter.animal_shelter.repository.BotUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BotUserService {
    private final Logger logger = LoggerFactory.getLogger(BotUserService.class);
    private final BotUserRepository botUserRepository;

    public BotUserService(BotUserRepository botUserRepository) {
        this.botUserRepository = botUserRepository;
    }

    /**
     * Метод createShelter создает новых <b>пользователей</b>, внося онформацию о них в базу данных.
     *
     * @param botUser параметр со значением данных <b>пользователя</b>.
     * @return найденный <b>пользователь</b>.
     */
    public BotUser createBotUser(BotUser botUser) {
        logger.debug("Creating a new BotUser:{}", botUser);
        final var save = botUserRepository.save(botUser);
        logger.debug("A new BotUser{}", save);
        return save;
    }

    /**
     * Метод editShelter изменяет уже существующую информацию в базе данных о <b>пользователе бота</b>.
     *
     * @param botUser параметр со значением данных <b>пользователя бота</b>.
     * @return найденный <b>пользователь бота/b>.
     */
    public BotUser editBotUser(BotUser botUser) {
        logger.debug("Edit BotUser:{}", botUser);
        final var botUser1 = botUserRepository.save(botUser);
        logger.debug("BotUser (edit) is{}", botUser1);
        return botUser1;
    }

    /**
     * Метод deleteBotUser удаляет из базы данных ранее внесенную информацию о <b>пользователе бота</b> в базу данных.
     *
     * @param id идентификатор искомого <b>пользователя бота</b>, <u>не может быть null</u>.
     */
    public void deleteBotUser(long id) {
        logger.debug("Delete BotUser:{}", id);
        botUserRepository.deleteById(id);
    }

    /**
     * Метод findBotUser получает найденного <b> пользователя бота</b> внесенного в базу данных.
     *
     * @return найденный <b>пользователь бота</b>.
     */
    public BotUser findBotUser(long id) {
        logger.debug("find BotUser by id");
        return botUserRepository.findById(id).orElse(null);
    }

}
