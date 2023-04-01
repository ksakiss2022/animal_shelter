package com.example.animal_shelter.animal_shelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

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
        try {
            updates.forEach(update -> {
                LOG.info("Processing update: {}", update);
                User user = update.message().from();
                String text = update.message().text();
                if ("/start".equals(text)) {
                    SendMessage sendMessage = new SendMessage(user.id(),
                            "Привет!\n Меня создали Коробейникова Светлана, Салимгареева Маргарита и Кулаков Николай.\n " +
                                    "Они проходят обучение по курсу Java - разработчик. " +
                                    "\n Я буду многофункциональным ботом, отвечающим за приюты собак и кошек \n " +
                                    "Но пока ещё я мало что умею делать :-)");
                    sendMessage.parseMode(ParseMode.Markdown);
                    telegramBot.execute(sendMessage);
                } else {
                    telegramBot.execute(
                            new SendMessage(user.id(), "Некорректная команда!"));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}
