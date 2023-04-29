package com.example.animal_shelter.animal_shelter.service;


import com.example.animal_shelter.animal_shelter.model.BotUser;
import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.repository.BotUserRepository;
import com.example.animal_shelter.animal_shelter.repository.CatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BotUserServiceTest {
    private Logger logger;
    @InjectMocks
    private BotUserService botUserService;
    @Mock
    private BotUserRepository botUserRepository;
    private BotUser expectedBotUser;

    @BeforeEach
    public void setup() {
        expectedBotUser = new BotUser();
        expectedBotUser.setShelterId(1l);
        expectedBotUser.setId(1L);
        expectedBotUser.setName("Пользователь");
        expectedBotUser.setPhone("8-999-99-99");
        expectedBotUser.setLastRequest("тест");
        expectedBotUser.setContact("Какая -то контактная информация.");
    }

    @Test
    public void createBotUserTest() {
        when(botUserRepository.save(any())).thenReturn(expectedBotUser);
        BotUser actualBotUser = botUserService.createBotUser(expectedBotUser);
        assertEquals(expectedBotUser, actualBotUser);
    }
    @Test
    public void editBotUser() {
        Long userId = 1L;
        String newName = "Тестовое имя 1";

        BotUser existingBotUser = new BotUser();
        existingBotUser.setId(userId);
        existingBotUser.setName("Тестовое имя 2");

        BotUser updatedBotUser = new BotUser();
        updatedBotUser.setId(userId);
        updatedBotUser.setName(newName);


        //   when(botUserRepository.findById(userId)).thenReturn(Optional.of(existingBotUser));
        when(botUserRepository.save(updatedBotUser)).thenReturn(updatedBotUser);

        BotUser result = botUserService.editBotUser(updatedBotUser);

        assertNotNull(result);
        assertEquals(newName, result.getName());
        assertEquals(existingBotUser.getPhone(), result.getPhone());
        assertEquals(existingBotUser.getContact(), result.getContact());
        assertEquals(existingBotUser.getLastRequest(), result.getLastRequest());


        verify(botUserRepository, times(1)).save(updatedBotUser);
    }


        @Test
        public void deleteBotUser() {
            Long userId = 1L;

            // mockito - doNothing() example
            doNothing().when(botUserRepository).deleteById(userId);

            botUserService.deleteBotUser(userId);

            verify(botUserRepository, times(1)).deleteById(userId);
        }

    @Test
    public void findBotUser() {

        BotUser botUser = new BotUser();
        botUser.setId(1L);

        when(botUserRepository.findById(1L)).thenReturn(Optional.of(botUser));

        BotUser result = botUserService.findBotUser(1L);

        verify(botUserRepository).findById(1L);

        assertNotNull(result);

        assertEquals(botUser.getId(), result.getId());
    }
}