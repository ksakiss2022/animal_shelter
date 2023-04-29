package com.example.animal_shelter.animal_shelter.listener;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import com.example.animal_shelter.animal_shelter.handler.CallBackQueryHandler;
import com.example.animal_shelter.animal_shelter.handler.CommandHandler;
import com.example.animal_shelter.animal_shelter.model.Volunteer;
import com.example.animal_shelter.animal_shelter.repository.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.example.animal_shelter.animal_shelter.service.VolunteerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelegramBotChatVolunteerTest {
    @Mock
    private TelegramBot telegramBot;

    @Mock
    private CommandHandler commandHandler;
    @Mock
    private ShelterRepository shelterRepository;
    @Mock
    private LocationMapRepository locationMapRepository;
    @Mock
    private DocumentDogRepository documentDogRepository;
    @Mock
    private CallBackQueryHandler callBackQueryHandler;
    @Mock
    private BotUserRepository botUserRepository;

    @Mock

    private VolunteerService volunteerService;
    @Mock
    private VolunteerRepository volunteerRepository;
    private static final int DAYS_OF_REPORTS = 7;

    @InjectMocks
    private TelegramBotChatVolunteer telegramBotChatVolunteer;

    private static Object answer(InvocationOnMock invocation) {
        Object SendMessage = invocation.getArguments()[0];
// do something with message argument
        return null; // or some other value to return
    }

    @BeforeEach
    public void setUp() {
        volunteerService = mock(VolunteerService.class); // инициализируем mock ReportService
        telegramBotChatVolunteer = new TelegramBotChatVolunteer(telegramBot,
                shelterRepository, locationMapRepository, documentDogRepository, volunteerService);
    }


    @Test
    public void testSendMessage() throws TelegramException {
        long chatId = 123456;
        String messageText = "Тест!";

        SendMessage expectedMessage = new SendMessage(chatId, messageText);
        TelegramBot mockedTelegramBot = mock(TelegramBot.class);

        when(mockedTelegramBot.execute(any(SendMessage.class))).thenReturn(mock(SendResponse.class));
        TelegramBotChatVolunteer telegramBotChatVolunteer = new TelegramBotChatVolunteer(mockedTelegramBot, null, null, null, null);
        telegramBotChatVolunteer.sendMessage(chatId, messageText);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(mockedTelegramBot, times(1)).execute(argumentCaptor.capture());
        SendMessage actualMessage = argumentCaptor.getValue();


    }

    @Test
    public void testSendForwardMessage() throws TelegramException {
        long chatId = 123456;
        int messageId = 789012;


        TelegramBot mockedTelegramBot = mock(TelegramBot.class);

        TelegramBotChatVolunteer telegramBotChatVolunteer = new TelegramBotChatVolunteer(mockedTelegramBot, null, null, null, null);

        telegramBotChatVolunteer.sendForwardMessage(chatId, messageId);

        ArgumentCaptor<ForwardMessage> argumentCaptor = ArgumentCaptor.forClass(ForwardMessage.class);
        verify(mockedTelegramBot, times(1)).execute(argumentCaptor.capture());
        ForwardMessage actualMessage = argumentCaptor.getValue();

    }

//    @Test
//    public void testOnUpdateReceived() throws TelegramException {
//        long chatId = 123456;
//        String messageText = "/volunteers";
//
//        Message message = mock(Message.class);
//        when(message.text()).thenReturn(messageText);
//        Chat chat = mock(Chat.class);
//        when(chat.id()).thenReturn(chatId);
//        when(message.chat()).thenReturn(chat);
//
//        Update mockUpdate = mock(Update.class);
//        when(mockUpdate.message()).thenReturn(message);
//
//        List<Volunteer> volunteers = new ArrayList<>();
//        volunteers.add(new Volunteer(1l, "Doe", "john.doe@mail.com", 5, "Test description"));
//        VolunteerService volunteerService = mock(VolunteerService.class);
//        when(volunteerService.getAllVolunteers()).thenReturn(volunteers);
//
//        SendMessage expectedMessage = new SendMessage(chatId, "Doe - 5\n");
//        TelegramBot mockedTelegramBot = mock(TelegramBot.class);
//        when(mockedTelegramBot.execute(any(SendMessage.class)))
//                .thenAnswer(invocation -> {
//                    SendMessage argument = invocation.getArgument(0);
//                    System.out.println("Actual SendMessage: " + argument.toString()); // print actual argument
//                    return mock(SendResponse.class);
//                });
//
//        telegramBotChatVolunteer.onUpdateReceived(mockUpdate);
//
//// Проверяем, что правильное сообщение было отправлено на чат
//        verify(mockedTelegramBot, times(1)).execute(expectedMessage);
//
//        System.out.println("Expected SendMessage: " + expectedMessage.toString()); // print expected message
//
//        // проверяем, что getAllVolunteers был вызван ровно 1 раз
//        verify(volunteerService, times(1)).getAllVolunteers();
//
//        // Проверяем, что правильное сообщение было отправлено в чат
//        verify(telegramBot, times(1)).execute(expectedMessage);
//    }

    @Test
    public void testGetBotToken() {
        TelegramBot mockedTelegramBot = mock(TelegramBot.class);
        ShelterRepository mockedShelterRepository = mock(ShelterRepository.class);
        LocationMapRepository mockedLocationMapRepository = mock(LocationMapRepository.class);
        DocumentDogRepository mockedDocumentDogRepository = mock(DocumentDogRepository.class);

        TelegramBotChatVolunteer telegramBotChatVolunteer = new TelegramBotChatVolunteer(
                mockedTelegramBot,
                mockedShelterRepository,
                mockedLocationMapRepository,
                mockedDocumentDogRepository,
                volunteerService);

        String expectedToken = "5952982757:AAGlsxzb13BJxnOQswhAM9ilBFROCIGMRfI";
        String actualToken = telegramBotChatVolunteer.getBotToken();

        assertEquals(expectedToken, actualToken);
    }

    @Test
    public void testProcess() {
        TelegramBot mockedTelegramBot = mock(TelegramBot.class);
        ShelterRepository shelterRepository = mock(ShelterRepository.class);
        LocationMapRepository locationMapRepository = mock(LocationMapRepository.class);
        DocumentDogRepository documentDogRepository = mock(DocumentDogRepository.class);
        TelegramBotChatVolunteer telegramBotChatVolunteer1 = new TelegramBotChatVolunteer(mockedTelegramBot,
                shelterRepository,
                locationMapRepository,
                documentDogRepository, volunteerService);

        List<Update> updates = new ArrayList<>();
        int expectedMessagesProcessed = 0;
        int actualMessagesProcessed = telegramBotChatVolunteer1.process(updates);

        assertEquals(expectedMessagesProcessed, actualMessagesProcessed);

    }
}