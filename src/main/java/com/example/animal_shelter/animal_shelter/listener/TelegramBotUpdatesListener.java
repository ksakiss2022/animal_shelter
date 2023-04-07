package com.example.animal_shelter.animal_shelter.listener;

import com.example.animal_shelter.animal_shelter.controller.ShelterController;
import com.example.animal_shelter.animal_shelter.model.LocationMap;
import com.example.animal_shelter.animal_shelter.model.Shelter;
import com.example.animal_shelter.animal_shelter.repository.LocationMapRepository;
import com.example.animal_shelter.animal_shelter.repository.ShelterRepository;
import com.example.animal_shelter.animal_shelter.service.ShelterService;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
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



    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    private Long shelterId = Long.valueOf(1); // временно задала значение. Надо сделать выбор и сохранение значения

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

        //Храним поддерживаемые CallBackQuery в виде констант
          final String SHELTER_INFORMATION = "shelter_information";
          final String SHELTER_TELL = "shelter_tell";
          final String SHELTER_SCHEDULE = "shelter_schedule";
          final String SHELTER_ADDRESS = "shelter_address";
          final String SHELTER_LOCATION_MAP = "shelter_location_map";
          final String CALL_VOLUNTEER = "call_volunteer";
          final String SAFETY_ADVICE = "safety_advice";
          final String ACCEPT_RECORD_CONTACT = "accept_record_contact";
          final String ADOPT_DOC_FROM_SHELTER = "adopt_doc_from_shelter";
          final String SEND_PET_REPORT = "send_pet_report";

          Long ShelterId;

        try {
            updates.forEach(update -> {
                LOG.info("Processing update: {}", update);

                if (update.callbackQuery() != null) {
                    Object chatId = update.callbackQuery().message().chat().id();
                    CallbackQuery callbackQuery = update.callbackQuery();
                    String data = callbackQuery.data();
                    switch (data) {

                        case SHELTER_INFORMATION://действия для кнопки "Узнать информацию о приюте"
                            //выводимое сообщение
                           SendMessage sendMessage1 = new SendMessage(chatId.toString(), "Что Вы хотите узнать о приюте?");
                            sendMessage1.parseMode(ParseMode.Markdown);
                            //выводится список кнопок для выбора
                            InlineKeyboardButton button11 = new InlineKeyboardButton("Рассказать о приюте");
                            button11.callbackData(SHELTER_TELL);

                            InlineKeyboardButton button12 = new InlineKeyboardButton("Расписание работы приюта");
                            button12.callbackData(SHELTER_SCHEDULE);

                            InlineKeyboardButton button16 = new InlineKeyboardButton("Адрес приюта");
                            button16.callbackData(SHELTER_ADDRESS);

                            InlineKeyboardButton button17 = new InlineKeyboardButton("Схема проезда к приюту");
                            button17.callbackData(SHELTER_LOCATION_MAP);

                            InlineKeyboardButton button13 = new InlineKeyboardButton("Общие рекомендации о технике безопасности на территории приюта");
                            button13.callbackData(SAFETY_ADVICE);

                            InlineKeyboardButton button14 = new InlineKeyboardButton("Принять и записать контактные данные для связи");
                            button14.callbackData(ACCEPT_RECORD_CONTACT);

                            InlineKeyboardButton button15 = new InlineKeyboardButton("Позвать волонтера");
                            button15.callbackData(CALL_VOLUNTEER);

                            InlineKeyboardMarkup keyboard2 = new InlineKeyboardMarkup();

                            keyboard2.addRow(button11);
                            keyboard2.addRow(button12);
                            keyboard2.addRow(button13);
                            keyboard2.addRow(button14);
                            keyboard2.addRow(button15);
                            keyboard2.addRow(button17);
                            keyboard2.addRow(button16);


                            sendMessage1.replyMarkup(keyboard2);
                            telegramBot.execute(sendMessage1);
                            break;
                        case ADOPT_DOC_FROM_SHELTER: //действия для кнопки "Как взять собаку из приюта"
                            SendMessage sendMessage2 = new SendMessage(chatId, "Нажата кнопка Как взять собаку из приюта");
                            telegramBot.execute(sendMessage2);
                            break;
                        case SEND_PET_REPORT: //действия для кнопки "Прислать отчет о питомце"
                            SendMessage sendMessage3 = new SendMessage(chatId, "Нажата кнопка Прислать отчет о питомце");
                            telegramBot.execute(sendMessage3);
                            break;
                        case CALL_VOLUNTEER: //действия для кнопки "Позвать волонтера"
                            SendMessage sendMessage4 = new SendMessage(chatId, "Нажата кнопка Позвать волонтера");
                            telegramBot.execute(sendMessage4);
                            break;

                        case  SHELTER_TELL:
                            Shelter shelter  = shelterRepository.findById(shelterId).get();
                            String information = shelter.getInformation();
                            SendMessage sendMessage5 = new SendMessage(chatId, information);
                            telegramBot.execute(sendMessage5);
                            break;

                        case  SHELTER_ADDRESS:
                            Shelter shelter1  = shelterRepository.findById(shelterId).get();
                            String address = shelter1.getAddress();
                            SendMessage sendMessage6 = new SendMessage(chatId, address);
                            telegramBot.execute(sendMessage6);
                            break;

                        case  SHELTER_SCHEDULE:
                            Shelter shelter2  = shelterRepository.findById(shelterId).get();
                            String schedule = shelter2.getSchedule();
                            SendMessage sendMessage7 = new SendMessage(chatId, schedule);
                            telegramBot.execute(sendMessage7);
                            break;

                        case  SHELTER_LOCATION_MAP:
                            LocationMap locationMap  = locationMapRepository.findLocationMapByShelterId(shelterId).get();
                            //String schedule = locationMap.getMediaType();
//                            SendMessage sendMessage8 = new SendMessage(chatId, schedule);
//                            telegramBot.execute(sendMessage8);
                           // Path path = Path.of(locationMap.getFilePath());
                            SendPhoto sendPhoto = new SendPhoto(chatId,locationMap.getData());
                            telegramBot.execute(sendPhoto);
                            break;

                    }

                    if (data.startsWith("Выбран приют")){

                            //выводится список кнопок для выбора

                            //запомнить выбранный приют
                            String part = data.replace("Выбран приют -", "");
                            shelterId = Long.valueOf(part);

                            SendMessage sendMessage6 = new SendMessage(chatId, "Выбран приют - " + shelterRepository.findById(shelterId).get().getName());

                            InlineKeyboardButton button1 = new InlineKeyboardButton("Узнать информацию о приюте");
                            button1.callbackData(SHELTER_INFORMATION);
                            InlineKeyboardButton button2 = new InlineKeyboardButton("Как взять собаку из приюта");
                            button2.callbackData(ADOPT_DOC_FROM_SHELTER);
                            InlineKeyboardButton button3 = new InlineKeyboardButton("Прислать отчет о питомце");
                            button3.callbackData(SEND_PET_REPORT);
                            InlineKeyboardButton button4 = new InlineKeyboardButton("Позвать волонтера");
                            button4.callbackData(CALL_VOLUNTEER);

                            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
                            keyboard.addRow(button1);
                            keyboard.addRow(button2);
                            keyboard.addRow(button3);
                            keyboard.addRow(button4);
                            sendMessage6.replyMarkup(keyboard);
                            telegramBot.execute(sendMessage6);
                            return;
                    }
                }
                else {

                User user = update.message().from();
                String text = update.message().text();
                if ("/start".equals(text)) { // Если пользователь ввел "/start", то выводится Приветствие и список кнопок для дальнейшей работы
                    SendMessage sendMessage = new SendMessage(user.id(),
                            "Привет!\n Меня создали Коробейникова Светлана, Салимгареева Маргарита и Кулаков Николай.\n " +
                                    "Они проходят обучение по курсу Java - разработчик. " +
                                    "\n Я буду многофункциональным ботом, отвечающим за приюты собак и кошек \n " +
                                    "Но пока ещё я мало что умею делать :-)");
                    sendMessage.parseMode(ParseMode.Markdown);

                    //надо сделать выбор приюта или поиск последнего запроса и из него брать приют

                    //получить список приютов и вывести их в команды
                    SendMessage sendMessage1 = new SendMessage(user.id(),
                            "Выберите приют");
                    sendMessage1.parseMode(ParseMode.Markdown);

                    Collection <Shelter> shelters = shelterRepository.findAll();
                    InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
                    for (Shelter shelter:
                         shelters) {
                        InlineKeyboardButton button1 = new InlineKeyboardButton(shelter.getName());
                        button1.callbackData("Выбран приют -" + shelter.getId());
                        keyboard.addRow(button1);
                    }
                    sendMessage1.replyMarkup(keyboard);
                    telegramBot.execute(sendMessage1);

                } else {
                    telegramBot.execute(
                            new SendMessage(user.id(), "Некорректная команда!"));
                }
                } });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}
