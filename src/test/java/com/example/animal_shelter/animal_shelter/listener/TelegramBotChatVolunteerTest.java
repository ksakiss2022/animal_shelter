package com.example.animal_shelter.animal_shelter.listener;

import com.example.animal_shelter.animal_shelter.model.Volunteer;
import com.example.animal_shelter.animal_shelter.model.VolunteerChat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.Before;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
class TelegramBotChatVolunteerTest {
    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private VolunteerChat volunteerChat;

//    @Before
//    public void init() {
//        MockitoAnnotations.initMocks(this);
//    }
//    //Для данного примера мы замокали объект TelegramBot и инъектируем его в класс
//    // TelegramBotService. Затем мы создали метод testSendMessage(),
//    // который отправляет сообщение с заданным текстом и id чата через наш метод sendMessage().
//    // Наконец, мы проверяем, что метод execute() был вызван только один раз с заданными
//    // аргументами sendmessage.
//    @Test
//    public void testSendMessage() {
//        long chatId = 12345L;
//        String text = "Test message";
//        SendMessage sendMessage = new SendMessage(chatId, text);
//        //  volunteerChat.sendMessage(chatId, text);
//        verify(telegramBot, times(1)).execute(sendMessage);
//    }
//    @Test
//    void sendMessage() {
//    }
//
//    @Test
//    void sendForwardMessage() {
//    }
//
//    @Test
//    void onUpdateReceived() {
//    }
//
//    @Test
//    void getBotUsername() {
//    }
//
//    @Test
//    void getBotToken() {
//    }
//
//    @Test
//    void process() {
//    }
}