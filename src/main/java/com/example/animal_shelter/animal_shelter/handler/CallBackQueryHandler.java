package com.example.animal_shelter.animal_shelter.handler;

import com.example.animal_shelter.animal_shelter.listener.TelegramBotUpdatesListener;
import com.example.animal_shelter.animal_shelter.model.*;
import com.example.animal_shelter.animal_shelter.repository.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CallBackQueryHandler {

    private final TelegramBot telegramBot;

    private final ShelterRepository shelterRepository;

    private final LocationMapRepository locationMapRepository;

    private final DocumentDogRepository documentDogRepository;
    private final DocumentCatRepository documentCatRepository;
    private final BotUserRepository botUserRepository;

    private static final String SHELTER_TELL = "shelter_tell";
    private static final String SHELTER_ADDRESS = "shelter_address";
    private static final String SHELTER_SCHEDULE = "shelter_schedule";
    private static final String SAFETY_RECOMMENDATIONS = "safety_recommendations";
    private static final String SHELTER_LOCATION_MAP = "shelter_location_map";

    private static final Logger LOG = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    public CallBackQueryHandler(TelegramBot telegramBot, ShelterRepository shelterRepository, LocationMapRepository locationMapRepository,
                                DocumentDogRepository documentDogRepository, BotUserRepository botUserRepository,
                            DocumentCatRepository documentCatRepository) {
        this.telegramBot = telegramBot;
        this.shelterRepository = shelterRepository;
        this.locationMapRepository = locationMapRepository;
        this.documentDogRepository = documentDogRepository;
        this.documentCatRepository = documentCatRepository;
        this.botUserRepository = botUserRepository;

    }

    public void handle(@NonNull CallbackQuery callbackQuery, Update update, TelegramBot telegramBot) {
        Long chatId = callbackQuery.message().chat().id();
        String data = callbackQuery.data();
        handleInputMessage(update);
    }


    public void handleInputMessage(Update update) {

        Long chatId = update.callbackQuery().message().chat().id();
         String data = update.callbackQuery().data();

        //сохранить последний запрос
        BotUser botUser = botUserRepository.findBotUserById(Long.valueOf(update.callbackQuery().message().chat().id()));
        botUser.setLastRequest(data);
        botUser = botUserRepository.save(botUser);

        handleInputData(chatId, data);
    }

    /*
        Различные обработчики выбранных пользователем команд
    */
    public void handleInputData( Long chatId ,String data ){
        switch (data) {

            case (SHELTER_TELL): // кнопка "Рассказать о приюте"
                Shelter shelter = getShelterByChatId(chatId);
                String information = shelter.getInformation();
                SendMessage sendMessage5 = new SendMessage(chatId, information);
                telegramBot.execute(sendMessage5);
                break;

            case (SHELTER_ADDRESS): //кнопка "Адрес приюта"
                Shelter shelter1 = getShelterByChatId(chatId);
                String address = shelter1.getAddress();
                SendMessage sendMessage6 = new SendMessage(chatId, address);
                telegramBot.execute(sendMessage6);
                break;

            case (SHELTER_SCHEDULE)://кнопка "Расписание приюта"
                Shelter shelter2 = getShelterByChatId(chatId);
                String schedule = shelter2.getSchedule();
                SendMessage sendMessage7 = new SendMessage(chatId, schedule);
                telegramBot.execute(sendMessage7);
                break;

            case (SAFETY_RECOMMENDATIONS)://кнопка "Рекомендации о технике безопасности на территории приюта."
                Shelter shelter3 = getShelterByChatId(chatId);
                String safetyRecommendations = shelter3.getSafetyRecommendations();
                SendMessage sendMessage8 = new SendMessage(chatId, safetyRecommendations);
                telegramBot.execute(sendMessage8);
                break;


            case (SHELTER_LOCATION_MAP)://кнопка "Схема проезда к приюту"
                Shelter shelter4 = getShelterByChatId(chatId);
                LocationMap locationMap = locationMapRepository.findLocationMapByShelterId(shelter4.getId()).get();
                SendPhoto sendPhoto = new SendPhoto(chatId, locationMap.getData());
                telegramBot.execute(sendPhoto);
                break;

            //кнопка команды "Как взять животное из приюта"
            //выводится разичные списки документов для животного
            case ("ANIMAL_DATING_RULES"):
            case ("DOCUMENTS_TO_ADOPT_ANIMAL"):
            case ("SHIPPING_RECOMMENDATIONS"):
            case ("PUPPY_KITTEN_HOME_IMPROVEMENT_TIPES"):
            case ("ANIMAL_HOME_IMPROVEMENT_TIPES"):
            case ("ANIMAL_WITH_DISABILITY_HOME_IMPROVEMENT_TIPES"):
            case ("REASONS_FOR_REJECTION"):
                getDocumentsForAnimal(data, chatId);
                break;
            case "CYNOLOG_ADVICE":
                sendCynologAdvices(chatId);
                break;
            case "CYNOLOGISTS":
                sendListOfCynologists(chatId);
                break;
        }
    }

    private Shelter getShelterByChatId(Long chatId){
        BotUser botUser = botUserRepository.findBotUserById(Long.valueOf(chatId.toString()));
       TypesShelters typesShelters = botUser.getTypeShelter();
       return shelterRepository.findSheltersByTypeShelter(typesShelters);
    }

    private void getDocumentsForAnimal(String data, Long chatId){
        //получить тип выбранного приюта пользователем
        //в зависимости от выбранного приюта, вывести рекомендации для кошек/рекомендаии для собак
        BotUser botUser = botUserRepository.findBotUserById(Long.valueOf(chatId.toString()));
        TypesShelters typeShelter = botUser.getTypeShelter();
        String text = null;
        TypesDocuments typesDocument = TypesDocuments.valueOf(data);
        if (typeShelter == TypesShelters.DOG_SHELTER) {
            DocumentDog documentDog = documentDogRepository.findDocumentDogByTypeDocumentDog(typesDocument);
            text = documentDog.getText();
        } else {
            TypesDocuments typesDocumentCat = TypesDocuments.valueOf(data);
            DocumentCat documentCat = documentCatRepository.findDocumentCatByTypeDocumentCat(typesDocument);
            text = documentCat.getText();
        }
        SendMessage sendMessage = new SendMessage(chatId, text);
        telegramBot.execute(sendMessage);

    }

    public void sendCynologAdvices(Object chatId){
        BotUser botUser = botUserRepository.findBotUserById(Long.valueOf(chatId.toString()));
        TypesShelters typeShelter = botUser.getTypeShelter();

        String cynologistsAdvice = shelterRepository.findSheltersByTypeShelter(typeShelter).getCynologistsAdvice();

        SendMessage message = new SendMessage(chatId, cynologistsAdvice);
        telegramBot.execute(message);
    }

    public void sendListOfCynologists(Object chatId){
        BotUser botUser = botUserRepository.findBotUserById(Long.valueOf(chatId.toString()));
        TypesShelters typeShelter = botUser.getTypeShelter();
        String cynologists = shelterRepository.findSheltersByTypeShelter(typeShelter).getCynologists();

        SendMessage message = new SendMessage(chatId, cynologists);
        telegramBot.execute(message);

    }
}
