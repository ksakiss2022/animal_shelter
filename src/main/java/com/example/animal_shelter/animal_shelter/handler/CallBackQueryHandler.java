package com.example.animal_shelter.animal_shelter.handler;

import com.example.animal_shelter.animal_shelter.model.*;
import com.example.animal_shelter.animal_shelter.repository.DocumentDogRepository;
import com.example.animal_shelter.animal_shelter.repository.LocationMapRepository;
import com.example.animal_shelter.animal_shelter.repository.ShelterRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CallBackQueryHandler {

    private final TelegramBot telegramBot;

    private Long shelterId = Long.valueOf(1); // временно задала значение. Надо сделать выбор и сохранение значения

    private final ShelterRepository shelterRepository;

    private final LocationMapRepository locationMapRepository;

    private final DocumentDogRepository documentDogRepository;

    public CallBackQueryHandler(TelegramBot telegramBot, ShelterRepository shelterRepository, LocationMapRepository locationMapRepository, DocumentDogRepository documentDogRepository) {
        this.telegramBot = telegramBot;
        this.shelterRepository = shelterRepository;
        this.locationMapRepository = locationMapRepository;
        this.documentDogRepository = documentDogRepository;
    }

    public void handle(@NonNull CallbackQuery callbackQuery, Update update, TelegramBot telegramBot) {
        Object chatId = callbackQuery.message().chat().id();
        String data = callbackQuery.data();
        handleInputMessage(update);
    }

    /*
    Различные обработчики выбранных пользователем команд
     */
    private void handleInputMessage(Update update) {

        Object chatId = update.callbackQuery().message().chat().id();
        CallbackQuery callbackQuery = update.callbackQuery();
        String data = callbackQuery.data();

        //Храним поддерживаемые CallBackQuery в виде констант
        BotState botState;//

        switch (data) {

            case ("shelter_information")://действия для кнопки "Узнать информацию о приюте"
                sendMessagesForShelterInformation(chatId);
                break;
            case ("adopt_doc_from_shelter"): //действия для кнопки "Как взять собаку из приюта"
//                SendMessage sendMessage2 = new SendMessage(chatId, "Нажата кнопка Как взять собаку из приюта");
//                telegramBot.execute(sendMessage2);
                sendMessagesForAdoptDogFromShelter(chatId);
                break;
            case ("send_pet_report"): //действия для кнопки "Прислать отчет о питомце"
                SendMessage sendMessage3 = new SendMessage(chatId, "Нажата кнопка Прислать отчет о питомце");
                telegramBot.execute(sendMessage3);
                break;
            case ("call_volunteer"): //действия для кнопки "Позвать волонтера"
                SendMessage sendMessage4 = new SendMessage(chatId, "Нажата кнопка Позвать волонтера");
                telegramBot.execute(sendMessage4);
                break;

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

                TypesDocument typesDocumentDog = TypesDocument.valueOf(data);
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
            sendMainMenuHandler(update);
            return;
        }

    }

    /*
    выводится список кнопок после выбора пункта "Узнать информацию о приюте"
     */
    private void sendMessagesForShelterInformation(Object chatId) {
        //выводимое сообщение
        SendMessage sendMessage = new SendMessage(chatId.toString(), "Что Вы хотите узнать о приюте?");
        sendMessage.parseMode(ParseMode.Markdown);
        //выводится список кнопок для выбора
        InlineKeyboardButton button1 = new InlineKeyboardButton("Рассказать о приюте");
        button1.callbackData(BotState.SHELTER_TELL.getTitle());

        InlineKeyboardButton button2 = new InlineKeyboardButton("Расписание работы приюта");
        button2.callbackData(BotState.SHELTER_SCHEDULE.getTitle());

        InlineKeyboardButton button3 = new InlineKeyboardButton("Адрес приюта");
        button3.callbackData(BotState.SHELTER_ADDRESS.getTitle());

        InlineKeyboardButton button4 = new InlineKeyboardButton("Схема проезда к приюту");
        button4.callbackData(BotState.SHELTER_LOCATION_MAP.getTitle());

        InlineKeyboardButton button5 = new InlineKeyboardButton("Общие рекомендации о технике безопасности на территории приюта");
        button5.callbackData(BotState.SAFETY_RECOMMENDATIONS.getTitle());

        InlineKeyboardButton button6 = new InlineKeyboardButton("Принять и записать контактные данные для связи");
        button6.callbackData(BotState.ACCEPT_RECORD_CONTACT.getTitle());

        InlineKeyboardButton button7 = new InlineKeyboardButton("Позвать волонтера");
        button7.callbackData(BotState.CALL_VOLUNTEER.getTitle());

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        keyboard.addRow(button1);
        keyboard.addRow(button2);
        keyboard.addRow(button3);
        keyboard.addRow(button4);
        keyboard.addRow(button5);
        keyboard.addRow(button6);
        keyboard.addRow(button7);

        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }


    /*
    выводится список кнопок после выбора пункта "Как взять собаку из приюта"
     */
    private void sendMessagesForAdoptDogFromShelter(Object chatId) {
        //выводимое сообщение
        SendMessage sendMessage = new SendMessage(chatId.toString(), "Выберите нужный вид консультации");
        sendMessage.parseMode(ParseMode.Markdown);

        //выводится список кнопок для выбора
        InlineKeyboardButton button1 = new InlineKeyboardButton("Правила знакомства с животным");
        button1.callbackData(BotState.ANIMAL_DATING_RULES.getTitle());

        InlineKeyboardButton button2 = new InlineKeyboardButton("Документы, необходимых для того, чтобы взять животное из приюта");
        button2.callbackData(BotState.DOCUMENTS_TO_ADOPT_ANIMAL.getTitle());

        InlineKeyboardButton button3 = new InlineKeyboardButton("Рекомендаций по транспортировке животного");
        button3.callbackData(BotState.SHIPPING_RECOMMENDATIONS.getTitle());

        InlineKeyboardButton button4 = new InlineKeyboardButton("Рекомендации по обустройству дома для щенка/котенка");
        button4.callbackData(BotState.PUPPY_KITTEN_HOME_IMPROVEMENT_TIPES.getTitle());

        InlineKeyboardButton button5 = new InlineKeyboardButton("Рекомендации по обустройству дома для взрослого животного");
        button5.callbackData(BotState.ANIMAL_HOME_IMPROVEMENT_TIPES.getTitle());

        InlineKeyboardButton button6 = new InlineKeyboardButton("Рекомендации по обустройству дома для животного с ограниченными возможностями (зрение, передвижение)");
        button6.callbackData(BotState.ANIMAL_WITH_DISABILITY_HOME_IMPROVEMENT_TIPES.getTitle());

        InlineKeyboardButton button7 = new InlineKeyboardButton("Советы кинолога по первичному общению с собакой");
        button7.callbackData(BotState.CYNOLOG_ADVIVCE.getTitle());

        InlineKeyboardButton button8 = new InlineKeyboardButton("Рекомендации по проверенным кинологам для дальнейшего обращения к ним");
        button8.callbackData(BotState.CYNOLOGISTS.getTitle());

        InlineKeyboardButton button9 = new InlineKeyboardButton("Список причин, почему могут отказать и не дать забрать собаку из приюта");
        button9.callbackData(BotState.REASONS_FOR_REJECTION.getTitle());

        InlineKeyboardButton button10 = new InlineKeyboardButton("Принять и записать контактные данные для связи");
        button10.callbackData(BotState.ACCEPT_RECORD_CONTACT.getTitle());

        InlineKeyboardButton button11 = new InlineKeyboardButton("Позвать волонтера");
        button11.callbackData(BotState.CALL_VOLUNTEER.getTitle());

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        keyboard.addRow(button1);
        keyboard.addRow(button2);
        keyboard.addRow(button3);
        keyboard.addRow(button4);
        keyboard.addRow(button5);
        keyboard.addRow(button6);
        keyboard.addRow(button7);
        keyboard.addRow(button8);
        keyboard.addRow(button9);
        keyboard.addRow(button10);
        keyboard.addRow(button11);

        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    // }
    /*
    После выбора приюта предлагается список команд для дальнейшей работы:
    - Узнать информацию о приюте (этап 1).
    - Как взять собаку из приюта (этап 2).
    - Прислать отчет о питомце (этап 3).
    - Позвать волонтера.
     */
    private void sendMainMenuHandler(Update update) {
        Object chatId = update.callbackQuery().message().chat().id();

        SendMessage sendMessage = new SendMessage(chatId, "Выбран приют - " + shelterRepository.findById(shelterId).get().getName());

        InlineKeyboardButton button1 = new InlineKeyboardButton("Узнать информацию о приюте");
        button1.callbackData(BotState.SHELTER_INFORMATION.getTitle());
        InlineKeyboardButton button2 = new InlineKeyboardButton("Как взять собаку из приюта");
        button2.callbackData(BotState.ADOPT_DOC_FROM_SHELTER.getTitle());
        InlineKeyboardButton button3 = new InlineKeyboardButton("Прислать отчет о питомце");
        button3.callbackData(BotState.SEND_PET_REPORT.getTitle());
        InlineKeyboardButton button4 = new InlineKeyboardButton("Позвать волонтера");
        button4.callbackData(BotState.CALL_VOLUNTEER.getTitle());

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.addRow(button1);
        keyboard.addRow(button2);
        keyboard.addRow(button3);
        keyboard.addRow(button4);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }



}
