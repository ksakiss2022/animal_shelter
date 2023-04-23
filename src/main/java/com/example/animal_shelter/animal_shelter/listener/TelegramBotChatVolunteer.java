package com.example.animal_shelter.animal_shelter.listener;
import com.example.animal_shelter.animal_shelter.model.VolunteerChat;
import com.example.animal_shelter.animal_shelter.model.Volunteer;
import com.example.animal_shelter.animal_shelter.repository.*;
import com.example.animal_shelter.animal_shelter.service.VolunteerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;

import com.pengrad.telegrambot.model.Update;

import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.jdbc.datasource.init.DatabasePopulatorUtils.execute;

@Service
public class TelegramBotChatVolunteer implements UpdatesListener {
    private static final Logger LOG = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    //Метод, использующий это выражение, скорее всего осуществляет поиск входных сообщений на предмет соответствия этому шаблону.
    // Если текстовые сообщения имеют нужный формат, то они проходят проверку и используются в дальнейшем. Если текст не соответствует шаблону, то он может быть проигнорирован или обработан каким-то другим способом.
    //
    //По сути, данный код задает шаблон для парсинга текстовых сообщений и извлечения нужной информации из них.
    private static final String CHAT_VOLUNTEER_MESSAGE = "(ВОПРОС:)(\\s)(\\W+)(;)\n" +
            "(Согласовать животное:)(\\s)(\\W+)(;)\n" +
            "(Согласовать время:)(\\s)(\\W+)(;)";
    private final TelegramBot telegramBot;

    private static final long telegramChatVolunteers = 0;
    private final ShelterRepository shelterRepository;
    private final LocationMapRepository locationMapRepository;

    private final DocumentDogRepository documentDogRepository;

    @Autowired
    private BotUserRepository botUserRepository;
    @Autowired
    private VolunteerRepository volunteerRepository;
    @Autowired
    private VolunteerService volunteerService;


    public TelegramBotChatVolunteer(TelegramBot telegramBot,
                                    ShelterRepository shelterRepository,
                                    LocationMapRepository locationMapRepository,
                                    DocumentDogRepository documentDogRepository) {
        this.telegramBot = telegramBot;
        this.shelterRepository = shelterRepository;
        this.locationMapRepository = locationMapRepository;
        this.documentDogRepository = documentDogRepository;

    }

    public void sendMessage(long chatId, String text) {
        com.pengrad.telegrambot.request.SendMessage message = new com.pengrad.telegrambot.request.SendMessage(chatId, text);
        telegramBot.execute(message);
    }

    public void sendForwardMessage(Long chatId, Integer messageId) {
        ForwardMessage forwardMessage = new ForwardMessage(telegramChatVolunteers, chatId, messageId);
        telegramBot.execute(forwardMessage);
    }

    public void onUpdateReceived(Update update) {
        if (update.message() != null && update.message().text() != null) {
            String messageText = update.message().text();
            long chatId = update.message().chat().id();

            if ("/volunteers".equals(messageText)) {
                List<Volunteer> volunteers = (List<Volunteer>) volunteerService.getVolunteers();
                StringBuilder stringBuilder = new StringBuilder();

                if (volunteers.isEmpty()) {
                    stringBuilder.append("Нет волонтеров в приюте");
                } else {
                    for (Volunteer volunteer : volunteers) {
                        stringBuilder.append(volunteer.getName()).append(" - ").append(volunteer.getContactInfo()).append("\n");
                    }
                }
                telegramBot.execute(new SendMessage(update.message().chat().id(), "*****"));
                System.out.println("**********: " + update.message().chat().id());
            } else if ("/chat".equals(messageText)) {
                List<Volunteer> volunteers = (List<Volunteer>) volunteerService.getVolunteers();
                Volunteer volunteer = null;
                if (!volunteers.isEmpty()) {
                    volunteer = volunteers.get(0);
                    VolunteerChat volunteerChat = new VolunteerChat();
                    volunteerChat.sendMessage("Сообщение от пользователя с id=" + chatId, volunteer);
                }
                VolunteerChat volunteerChat = new VolunteerChat();
                volunteerChat.sendMessage("Сообщение от пользователя с id=" + chatId, volunteer);

                SendMessage message = new SendMessage(chatId, "Вы вошли в чат с волонтером");
                telegramBot.execute(new SendMessage(update.message().chat().id(), "*****"));
                System.out.println("**********: " + update.message().chat().id());
            }
        }
    }




    public String getBotUsername() {
        return "Volunteer_from_bot";
    }


    public String getBotToken() {
        return "5952982757:AAGlsxzb13BJxnOQswhAM9ilBFROCIGMRfI";
    }


    @Override
    public int process(List<Update> list) {
        return 0;
    }
}