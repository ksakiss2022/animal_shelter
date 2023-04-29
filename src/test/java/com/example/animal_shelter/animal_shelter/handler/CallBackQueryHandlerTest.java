package com.example.animal_shelter.animal_shelter.handler;

import com.example.animal_shelter.animal_shelter.model.*;
import com.example.animal_shelter.animal_shelter.repository.*;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class CallBackQueryHandlerTest {
    @Mock
    private TelegramBot telegramBot;

    @Mock
    private ShelterRepository shelterRepository;

    @Mock
    private LocationMapRepository locationMapRepository;
    @Mock
    private DocumentDogRepository documentDogRepository;
    @Mock
    private DocumentCatRepository documentCatRepository;

    @Mock
    private BotUserRepository botUserRepository;

    @InjectMocks
    private CallBackQueryHandler callBackQueryHandler;

    @BeforeEach
    public void setUp() {
        callBackQueryHandler = new CallBackQueryHandler(telegramBot,shelterRepository,locationMapRepository,documentDogRepository,botUserRepository,documentCatRepository);
    }


    @Test
    void handleInputData() {
    }

    @Test
    void sendCynologAdvices() throws URISyntaxException, IOException {
        String text = "CYNOLOG_ADVICE";
        User user = new User(123L);
        Shelter shelter = new Shelter();
        when(telegramBot.execute(any())).thenReturn(generateResponseOk());
        String json = Files.readString(
                Paths.get(CommandHandlerTest.class.getResource("/text_updateWithCallback.json").toURI()));
        Update update = getUpdate(json, text);
        BotUser botUser = new BotUser(123L);
        botUser.setTypeShelter(TypesShelters.DOG_SHELTER);
        when(botUserRepository.findBotUserById(any())).thenReturn(botUser);
        when(shelterRepository.findSheltersByTypeShelter(any())).thenReturn(shelter);
        callBackQueryHandler.handle(update.callbackQuery(),update,telegramBot);
        //Then
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();
        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(123L);
    }

    @Test
    void sendListOfCynologists() throws URISyntaxException, IOException{
        String text = "CYNOLOGISTS";
        User user = new User(123L);
        Shelter shelter = new Shelter();
        shelter.setCynologists("список кинологов");
        when(telegramBot.execute(any())).thenReturn(generateResponseOk());
        String json = Files.readString(
                Paths.get(CommandHandlerTest.class.getResource("/text_updateWithCallback.json").toURI()));
        Update update = getUpdate(json, text);
        BotUser botUser = new BotUser(123L);
        botUser.setTypeShelter(TypesShelters.DOG_SHELTER);
        when(botUserRepository.findBotUserById(any())).thenReturn(botUser);
        when(shelterRepository.findSheltersByTypeShelter(any())).thenReturn(shelter);
        callBackQueryHandler.handle(update.callbackQuery(),update,telegramBot);
        //Then
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();
        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(123L);
    }

    @Test
    void handleGetDocumentsForAnimal() throws URISyntaxException, IOException{
        String text = "ANIMAL_DATING_RULES";
        User user = new User(123L);
        //Shelter shelter = new Shelter();
        DocumentDog documentDog = new DocumentDog();
        documentDog.setText("рекомендации");
       // shelter.setCynologists("список кинологов");
        when(telegramBot.execute(any())).thenReturn(generateResponseOk());
        String json = Files.readString(
                Paths.get(CommandHandlerTest.class.getResource("/text_updateWithCallback.json").toURI()));
        Update update = getUpdate(json, text);
        BotUser botUser = new BotUser(123L);
        botUser.setTypeShelter(TypesShelters.DOG_SHELTER);
        when(botUserRepository.findBotUserById(any())).thenReturn(botUser);
        //when(shelterRepository.findSheltersByTypeShelter(any())).thenReturn(shelter);
        when(documentDogRepository.findDocumentDogByTypeDocumentDog(any())).thenReturn(documentDog);
        callBackQueryHandler.handle(update.callbackQuery(),update,telegramBot);
        //Then
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();
        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(123L);
    }

    @Test
    void sendShelterTell() throws URISyntaxException, IOException{
        String text = "shelter_tell";
        User user = new User(123L);
        Shelter shelter = new Shelter();
        shelter.setInformation("информация");
        when(telegramBot.execute(any())).thenReturn(generateResponseOk());
        String json = Files.readString(
                Paths.get(CommandHandlerTest.class.getResource("/text_updateWithCallback.json").toURI()));
        Update update = getUpdate(json, text);
        BotUser botUser = new BotUser(123L);
        botUser.setTypeShelter(TypesShelters.DOG_SHELTER);
        when(botUserRepository.findBotUserById(any())).thenReturn(botUser);
        when(shelterRepository.findSheltersByTypeShelter(any())).thenReturn(shelter);
        callBackQueryHandler.handle(update.callbackQuery(),update,telegramBot);
        //Then
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();
        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(123L);
    }

    @Test
    void sendShelterAddress() throws URISyntaxException, IOException{
        String text = "shelter_address";
        User user = new User(123L);
        Shelter shelter = new Shelter();
        shelter.setAddress("адрес");
        when(telegramBot.execute(any())).thenReturn(generateResponseOk());
        String json = Files.readString(
                Paths.get(CommandHandlerTest.class.getResource("/text_updateWithCallback.json").toURI()));
        Update update = getUpdate(json, text);
        BotUser botUser = new BotUser(123L);
        botUser.setTypeShelter(TypesShelters.DOG_SHELTER);
        when(botUserRepository.findBotUserById(any())).thenReturn(botUser);
        when(shelterRepository.findSheltersByTypeShelter(any())).thenReturn(shelter);
        callBackQueryHandler.handle(update.callbackQuery(),update,telegramBot);
        //Then
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();
        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(123L);
    }

    @Test
    void sendShelterSchedule() throws URISyntaxException, IOException{
        String text = "shelter_schedule";
        User user = new User(123L);
        Shelter shelter = new Shelter();
        shelter.setSchedule("расписание");
        when(telegramBot.execute(any())).thenReturn(generateResponseOk());
        String json = Files.readString(
                Paths.get(CommandHandlerTest.class.getResource("/text_updateWithCallback.json").toURI()));
        Update update = getUpdate(json, text);
        BotUser botUser = new BotUser(123L);
        botUser.setTypeShelter(TypesShelters.DOG_SHELTER);
        when(botUserRepository.findBotUserById(any())).thenReturn(botUser);
        when(shelterRepository.findSheltersByTypeShelter(any())).thenReturn(shelter);
        callBackQueryHandler.handle(update.callbackQuery(),update,telegramBot);
        //Then
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();
        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(123L);
    }
    @Test
    void sendSafetyRecommendations() throws URISyntaxException, IOException{
        String text = "safety_recommendations";
        User user = new User(123L);
        Shelter shelter = new Shelter();
        shelter.setSafetyRecommendations("рекомендации");
        when(telegramBot.execute(any())).thenReturn(generateResponseOk());
        String json = Files.readString(
                Paths.get(CommandHandlerTest.class.getResource("/text_updateWithCallback.json").toURI()));
        Update update = getUpdate(json, text);
        BotUser botUser = new BotUser(123L);
        botUser.setTypeShelter(TypesShelters.DOG_SHELTER);
        when(botUserRepository.findBotUserById(any())).thenReturn(botUser);
        when(shelterRepository.findSheltersByTypeShelter(any())).thenReturn(shelter);
        callBackQueryHandler.handle(update.callbackQuery(),update,telegramBot);
        //Then
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();
        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(123L);
    }

    @Test
    void sendShelterLocationMap() throws URISyntaxException, IOException{
        String text = "shelter_location_map";
        User user = new User(123L);
        Shelter shelter = new Shelter();
        shelter.setAddress("адрес");
        LocationMap locationMap = new LocationMap();
        locationMap.setData("hello.jpg".getBytes());
        when(telegramBot.execute(any())).thenReturn(generateResponseOk());
        String json = Files.readString(
                Paths.get(CommandHandlerTest.class.getResource("/text_updateWithCallback.json").toURI()));
        Update update = getUpdate(json, text);
        BotUser botUser = new BotUser(123L);
        botUser.setTypeShelter(TypesShelters.DOG_SHELTER);
        when(botUserRepository.findBotUserById(any())).thenReturn(botUser);
        when(shelterRepository.findSheltersByTypeShelter(any())).thenReturn(shelter);
        when(locationMapRepository.findLocationMapByShelterId(any())).thenReturn(Optional.of(locationMap));
        callBackQueryHandler.handle(update.callbackQuery(),update,telegramBot);
        //Then
//        ArgumentCaptor<SendPhoto> argumentCaptor = ArgumentCaptor.forClass(SendPhoto.class);
//        Mockito.verify(telegramBot).execute(argumentCaptor.getValue());
//        BaseRequest actual = argumentCaptor.getValue();
//        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(123L);
    }


    private SendResponse generateResponseOk() {
        return BotUtils.fromJson("""
                { "ok": true }""", SendResponse.class);
    }

    private Update getUpdate(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%data%", replaced), Update.class);
    }

}