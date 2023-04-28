package com.example.animal_shelter.animal_shelter.listener;

import com.example.animal_shelter.animal_shelter.handler.CallBackQueryHandler;
import com.example.animal_shelter.animal_shelter.handler.CommandHandler;
import com.example.animal_shelter.animal_shelter.model.BotUser;
import com.example.animal_shelter.animal_shelter.repository.*;
import com.example.animal_shelter.animal_shelter.service.ReportService;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
//import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TelegramBotUpdatesListenerTest {
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private ReportRepository reportRepository;
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
//    @Mock
//    private PersonDogRepository personDogRepository;
//    @Mock
//    private PersonCatRepository personCatRepository;
//    @Mock
//    private ReportService reportService;
    @InjectMocks
    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    @BeforeEach
    public void setUp() {
        telegramBotUpdatesListener = new TelegramBotUpdatesListener(telegramBot,
                shelterRepository,
                locationMapRepository,documentDogRepository,callBackQueryHandler,
                commandHandler, botUserRepository, reportRepository);
       // when(telegramBot.execute(any())).thenReturn(generateResponseOk());
    }
    @Test
    void testUpdatesListener() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("/text_update.json").toURI()));
        Update update = getUpdate(json, "/start");
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        doNothing().when(commandHandler).handle(any(), argumentCaptor.capture());
        telegramBotUpdatesListener.process(Collections.singletonList(update));
        verify(commandHandler, times(1)).handle(any(), argumentCaptor.capture());
    }


    private Update getUpdate(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%command%", replaced), Update.class);
    }


    @Test
    void sendMessage()  {

    }

    @Test
    void sendForwardMessage() {
    }

    @Test
    void shareContact() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("/text_updateContact.json").toURI()));
        Update update = getUpdate(json, "/contact");
        BotUser botUser = new BotUser(123L);
        //botUser.setName("name user");
        when(telegramBot.execute(any())).thenReturn(generateResponseOk());
        List<BotUser> sortChatId = new ArrayList<>();
        sortChatId.add(botUser);

        when(botUserRepository.findAll()).thenReturn(sortChatId);
        when(botUserRepository.save(any())).thenReturn(botUser);

        telegramBotUpdatesListener.shareContact(update);

        ArgumentCaptor<BaseRequest> argumentCaptor = ArgumentCaptor.forClass(BaseRequest.class);
       Mockito.verify(telegramBot, times(3)).execute(argumentCaptor.capture());
        BaseRequest actual = argumentCaptor.getValue();
        Assertions.assertThat(actual.getParameters().get("message_id")).isEqualTo(123);
    }

    @Test
    void getReport() {
    }

    @Test
    void checkResults() {
    }
    private SendResponse generateResponseOk() {
        return BotUtils.fromJson("""
                { "ok": true }""", SendResponse.class);
    }

}