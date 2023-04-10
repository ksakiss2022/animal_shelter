package com.example.animal_shelter.animal_shelter.listener;

import com.example.animal_shelter.animal_shelter.controller.ShelterController;
import com.example.animal_shelter.animal_shelter.model.*;
import com.example.animal_shelter.animal_shelter.repository.BotUserRepository;
import com.example.animal_shelter.animal_shelter.repository.DocumentDogRepository;
import com.example.animal_shelter.animal_shelter.repository.LocationMapRepository;
import com.example.animal_shelter.animal_shelter.repository.ShelterRepository;
import com.example.animal_shelter.animal_shelter.service.ShelterService;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private static final Logger LOG = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    @Autowired
    private ShelterRepository shelterRepository;

    @Autowired
    private LocationMapRepository locationMapRepository;

    @Autowired
    private DocumentDogRepository documentDogRepository;
    @Autowired
    private BotUserRepository botUserRepository;


    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    private Long shelterId = Long.valueOf(1); // временно задала значение. Надо сделать выбор и сохранение значения

    BotUser botUser = new BotUser();

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    /**
     * Обрабатывает данные или команды, вводимые пользователем в Телеграм-боте
     * Если вводится "start", то выводится Приветствие и список команд для выбора
     * Если пользователь нажал на кнопку, то выводятся или дополнительные команды или какая-нибудь информация
     */
    public int process(List<Update> updates) {

        Long ShelterId;
        try {
            updates.forEach(update -> {
                LOG.info("Processing update: {}", update);

                if (update.callbackQuery() != null) {
                    Object chatId = update.callbackQuery().message().chat().id();
                    CallbackQuery callbackQuery = update.callbackQuery();
                    String data = callbackQuery.data();
                    handleInputMessage(update);
                } else {
                    User user = update.message().from();
                    String text = update.message().text();
                    if ("/start".equals(text)) { // Если пользователь ввел "/start", то выводится Приветствие и список кнопок для дальнейшей работы
                        sendWelcomeMessage(user);
                    } else {
                        telegramBot.execute(
                                new SendMessage(user.id(), "Некорректная команда!"));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /*
    отправляет  приветственное сообщение и предлагает список приютов для выбора
     */
    private void sendWelcomeMessage(User user) {
        SendMessage sendMessage = new SendMessage(user.id(),
                "Привет!\n Меня создали Коробейникова Светлана, Салимгареева Маргарита и Кулаков Николай.\n " +
                        "Они проходят обучение по курсу Java - разработчик. " +
                        "\n Я буду многофункциональным ботом, отвечающим за приюты собак и кошек \n " +
                        "Но пока ещё я мало что умею делать :-)");
        sendMessage.parseMode(ParseMode.Markdown);

        //надо сделать выбор приюта или поиск последнего запроса и из него брать приют
//        botUser = botUserRepository.findBotUserById(user.id());
//        String lastRequest = null;
//        if (botUser != null) {
//            shelterId = botUser.getShelterId();
//            lastRequest = botUser.getLastRequest();
//            if (lastRequest != null) {
//
//            }
//        }
//        if (lastRequest == null) {
            //получить список приютов и вывести их в команды
            SendMessage sendMessage1 = new SendMessage(user.id(),
                    "Выберите приют");
            sendMessage1.parseMode(ParseMode.Markdown);

            Collection<Shelter> shelters = shelterRepository.findAll();
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
            for (Shelter shelter :
                    shelters) {
                InlineKeyboardButton button1 = new InlineKeyboardButton(shelter.getName());
                button1.callbackData("Выбран приют -" + shelter.getId());
                keyboard.addRow(button1);
            }
            sendMessage1.replyMarkup(keyboard);
            telegramBot.execute(sendMessage1);
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

    /*
    Разлличные обработчики выбранных пользователем команд
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
            case ("DOG_DATING_RULES"):
            case ("DOCUMENTS_TO_ADOPT_DOG"):
            case ("SHIPPING_RECOMMENDATIONS"):
            case ("PUPPY_HOME_IMPROVEMENT_TIPES"):
            case ("DOG_HOME_IMPROVEMENT_TIPES"):
            case ("DOG_WITH_DISABILITY_HOME_IMPROVEMENT_TIPES"):
            case ("CYNOLOG_ADVIVCE"):
            case ("REASONS_FOR_REJECTION"):

                TypesDocumentDog typesDocumentDog = TypesDocumentDog.valueOf(data);
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
        InlineKeyboardButton button1 = new InlineKeyboardButton("Правила знакомства с собакой");
        button1.callbackData(BotState.DOG_DATING_RULES.getTitle());

        InlineKeyboardButton button2 = new InlineKeyboardButton("Документы, необходимых для того, чтобы взять собаку из приюта");
        button2.callbackData(BotState.DOCUMENTS_TO_ADOPT_DOG.getTitle());

        InlineKeyboardButton button3 = new InlineKeyboardButton("Рекомендаций по транспортировке животного");
        button3.callbackData(BotState.SHIPPING_RECOMMENDATIONS.getTitle());

        InlineKeyboardButton button4 = new InlineKeyboardButton("Рекомендации по обустройству дома для щенка");
        button4.callbackData(BotState.PUPPY_HOME_IMPROVEMENT_TIPES.getTitle());

        InlineKeyboardButton button5 = new InlineKeyboardButton("Рекомендации по обустройству дома для взрослой собаки");
        button5.callbackData(BotState.DOG_HOME_IMPROVEMENT_TIPES.getTitle());

        InlineKeyboardButton button6 = new InlineKeyboardButton("Рекомендации по обустройству дома для собаки с ограниченными возможностями (зрение, передвижение)");
        button6.callbackData(BotState.DOG_WITH_DISABILITY_HOME_IMPROVEMENT_TIPES.getTitle());

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


}