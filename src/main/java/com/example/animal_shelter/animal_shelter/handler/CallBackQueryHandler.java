package com.example.animal_shelter.animal_shelter.handler;

import com.example.animal_shelter.animal_shelter.model.*;
import com.example.animal_shelter.animal_shelter.repository.BotUserRepository;
import com.example.animal_shelter.animal_shelter.repository.DocumentDogRepository;
import com.example.animal_shelter.animal_shelter.repository.LocationMapRepository;
import com.example.animal_shelter.animal_shelter.repository.ShelterRepository;
import com.example.animal_shelter.animal_shelter.service.BotUserService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CallBackQueryHandler {

    private final TelegramBot telegramBot;

    private Long shelterId = Long.valueOf(1); // временно задала значение. Надо сделать выбор и сохранение значения

    private final ShelterRepository shelterRepository;

    private final LocationMapRepository locationMapRepository;

    private final DocumentDogRepository documentDogRepository;
    private final BotUserRepository botUserRepository;



    public CallBackQueryHandler(TelegramBot telegramBot, ShelterRepository shelterRepository, LocationMapRepository locationMapRepository, DocumentDogRepository documentDogRepository, BotUserRepository botUserRepository) {
        this.telegramBot = telegramBot;
        this.shelterRepository = shelterRepository;
        this.locationMapRepository = locationMapRepository;
        this.documentDogRepository = documentDogRepository;
        this.botUserRepository = botUserRepository;

    }

    public void handle(@NonNull CallbackQuery callbackQuery, Update update, TelegramBot telegramBot) {
        Object chatId = callbackQuery.message().chat().id();
        String data = callbackQuery.data();
        handleInputMessage(update);
    }

    /*
    Различные обработчики выбранных пользователем команд
     */
    public void handleInputMessage(Update update) {

        Object chatId = update.callbackQuery().message().chat().id();
         String data = update.callbackQuery().data();

        //сохранить последний запрос
        BotUser botUser = botUserRepository.findBotUserById(Long.valueOf(update.callbackQuery().message().chat().id()));
        botUser.setLastRequest(data);
        botUser = botUserRepository.save(botUser);

        handleInputData(chatId, data);
    }

    public void handleInputData( Object chatId ,String data  ){
        switch (data) {

//            case ("shelter_information")://действия для кнопки "Узнать информацию о приюте"
//                sendMessagesForShelterInformation(chatId);
//                break;
//            case ("adopt_doc_from_shelter"): //действия для кнопки "Как взять собаку из приюта"
////                SendMessage sendMessage2 = new SendMessage(chatId, "Нажата кнопка Как взять собаку из приюта");
////                telegramBot.execute(sendMessage2);
//                sendMessagesForAdoptDogFromShelter(chatId);
//                break;
//            case ("send_pet_report"): //действия для кнопки "Прислать отчет о питомце"
//                SendMessage sendMessage3 = new SendMessage(chatId, "Нажата кнопка Прислать отчет о питомце");
//                telegramBot.execute(sendMessage3);
//                break;
//            case ("call_volunteer"): //действия для кнопки "Позвать волонтера"
//                SendMessage sendMessage4 = new SendMessage(chatId, "Нажата кнопка Позвать волонтера");
//                telegramBot.execute(sendMessage4);
//                break;
//
            case ("shelter_tell"): // кнопка "Рассказать о приюте"
                Shelter shelter = shelterRepository.findById(shelterId).get();
                String information = shelter.getInformation();
                SendMessage sendMessage5 = new SendMessage(chatId, information);
                telegramBot.execute(sendMessage5);
                break;

            case ("shelter_address"): //кнопка "Адрес приюта"
                Shelter shelter1 = shelterRepository.findById(shelterId).get();
                String address = shelter1.getAddress();
                SendMessage sendMessage6 = new SendMessage(chatId, address);
                telegramBot.execute(sendMessage6);
                break;

            case ("shelter_schedule")://кнопка "Расписание приюта"
                Shelter shelter2 = shelterRepository.findById(shelterId).get();
                String schedule = shelter2.getSchedule();
                SendMessage sendMessage7 = new SendMessage(chatId, schedule);
                telegramBot.execute(sendMessage7);
                break;

            case ("safety_recommendations")://кнопка "Рекомендации о технике безопасности на территории приюта."
                Shelter shelter3 = shelterRepository.findById(shelterId).get();
                String safetyRecommendations = shelter3.getSafetyRecommendations();
                SendMessage sendMessage8 = new SendMessage(chatId, safetyRecommendations);
                telegramBot.execute(sendMessage8);
                break;


            case ("shelter_location_map")://кнопка "Схема проезда к приюту"
                LocationMap locationMap = locationMapRepository.findLocationMapByShelterId(shelterId).get();
                SendPhoto sendPhoto = new SendPhoto(chatId, locationMap.getData());
                telegramBot.execute(sendPhoto);
                break;

            //кнопка команды "Как взять собаку из приюта"
            //выводится разичные списки документов для собаки
            case ("ANIMAL_DATING_RULES"):
            case ("DOCUMENTS_TO_ADOPT_ANIMAL"):
            case ("SHIPPING_RECOMMENDATIONS"):
            case ("PUPPY_KITTEN_HOME_IMPROVEMENT_TIPES"):
            case ("ANIMAL_HOME_IMPROVEMENT_TIPES"):
            case ("ANIMAL_WITH_DISABILITY_HOME_IMPROVEMENT_TIPES"):
            case ("CYNOLOG_ADVIVCE"):
            case ("REASONS_FOR_REJECTION"):

                TypesDocuments typesDocumentDog = TypesDocuments.valueOf(data);
                DocumentDog documentDog = documentDogRepository.findDocumentDogByTypeDocumentDog(typesDocumentDog);

                String text = documentDog.getText();
                SendMessage sendMessage9 = new SendMessage(chatId, text);
                telegramBot.execute(sendMessage9);
                break;

            case ("accept_record_contact"):

        }

        if (data.startsWith("Выбран приют")) {

            //запомнить выбранный приют
            String part = data.replace("Выбран приют -", "");
            shelterId = Long.valueOf(part);
            //вывести список команд главного меню
            sendMainMenuHandler(chatId);
            return;
        }

    }




    // }
    /*
    После выбора приюта предлагается список команд для дальнейшей работы:
    - Узнать информацию о приюте (этап 1).
    - Как взять собаку из приюта (этап 2).
    - Прислать отчет о питомце (этап 3).
    - Позвать волонтера.
     */
    public void sendMainMenuHandler(Object chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Выбран приют - " + shelterRepository.findById(shelterId).get().getName());
     //   logger.info("Method sendMenuTakeAnimal has been run: {}, {}", chatId, "вызвали Как взять питомца из приюта");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Информация о возможностях бота"),
                new KeyboardButton("Узнать информацию о приюте"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Как взять питомца из приюта"),
                new KeyboardButton("Прислать отчет о питомце"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать волонтера"));

        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, Long.valueOf(chatId.toString()), "Главное меню");

    }
    public void returnResponseReplyKeyboardMarkup(ReplyKeyboardMarkup replyKeyboardMarkup, Long chatId, String text) {
        replyKeyboardMarkup.resizeKeyboard(true);
        replyKeyboardMarkup.oneTimeKeyboard(false);
        replyKeyboardMarkup.selective(false);
        SendMessage request = new SendMessage(chatId, text)
                .replyMarkup(replyKeyboardMarkup)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);
        SendResponse sendResponse = telegramBot.execute(request);
        if (!sendResponse.isOk()) {
            int codeError = sendResponse.errorCode();
            String description = sendResponse.description();
//            logger.info("code of error: {}", codeError);
//            logger.info("description -: {}", description);
        }
    }


}
