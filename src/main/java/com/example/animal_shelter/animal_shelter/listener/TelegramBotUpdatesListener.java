package com.example.animal_shelter.animal_shelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private static final Logger LOG = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {

        //Храним поддерживаемые CallBackQuery в виде констант
          final String SHELTER_INFORMATION = "shelter_information";
          final String SHELTER_TELL = "shelter_tell";
          final String SHELTER_SCHEDULE = "shelter_schedule";
          final String CALL_VOLUNTEER = "call_volunteer";
          final String SAFETY_ADVICE = "safety_advice";
          final String ACCEPT_RECORD_CONTACT = "accept_record_contact";
          final String ADOPT_DOC_FROM_SHELTER = "adopt_doc_from_shelter";
          final String SEND_PET_REPORT = "send_pet_report";

        try {
            updates.forEach(update -> {
                LOG.info("Processing update: {}", update);

                if (update.callbackQuery() != null) {
                    Object chatId = update.callbackQuery().message().chat().id();
                    CallbackQuery callbackQuery = update.callbackQuery();
                    String data = callbackQuery.data();
                    switch (data) {
                        case SHELTER_INFORMATION:
                           SendMessage sendMessage1 = new SendMessage(chatId.toString(), "Что Вы хотите узнать о приюте?");
                            sendMessage1.parseMode(ParseMode.Markdown);

                            InlineKeyboardButton button11 = new InlineKeyboardButton("Рассказать о приюте");
                            button11.callbackData(SHELTER_TELL);
                            InlineKeyboardButton button12 = new InlineKeyboardButton("Расписание работы приюта и адрес, схема проезда");
                            button12.callbackData(SHELTER_SCHEDULE);
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

                            sendMessage1.replyMarkup(keyboard2);
                            telegramBot.execute(sendMessage1);
                            break;
                        case ADOPT_DOC_FROM_SHELTER:
                            SendMessage sendMessage2 = new SendMessage(chatId, "Нажата кнопка Как взять собаку из приюта");
                            telegramBot.execute(sendMessage2);
                            break;
                        case SEND_PET_REPORT:
                            SendMessage sendMessage3 = new SendMessage(chatId, "Нажата кнопка Прислать отчет о питомце");
                            telegramBot.execute(sendMessage3);
                            break;
                        case CALL_VOLUNTEER:
                            SendMessage sendMessage4 = new SendMessage(chatId, "Нажата кнопка Позвать волонтера");
                            telegramBot.execute(sendMessage4);
                            break;
                    }
                }
                else {

                User user = update.message().from();
                String text = update.message().text();
                if ("/start".equals(text)) {
                    SendMessage sendMessage = new SendMessage(user.id(),
                            "Привет!\n Меня создали Коробейникова Светлана, Салимгареева Маргарита и Кулаков Николай.\n " +
                                    "Они проходят обучение по курсу Java - разработчик. " +
                                    "\n Я буду многофункциональным ботом, отвечающим за приюты собак и кошек \n " +
                                    "Но пока ещё я мало что умею делать :-)");
                    sendMessage.parseMode(ParseMode.Markdown);

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

                    sendMessage.replyMarkup(keyboard);

                    telegramBot.execute(sendMessage);
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
