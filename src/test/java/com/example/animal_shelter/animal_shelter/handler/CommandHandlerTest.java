package com.example.animal_shelter.animal_shelter.handler;

import com.example.animal_shelter.animal_shelter.listener.TelegramBotUpdatesListener;
import com.example.animal_shelter.animal_shelter.model.BotState;
import com.example.animal_shelter.animal_shelter.model.BotUser;
import com.example.animal_shelter.animal_shelter.model.TypesShelters;
import com.example.animal_shelter.animal_shelter.repository.*;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CommandHandlerTest {
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private BotUserRepository botUserRepository;
    @Mock
    private ShelterRepository shelterRepository;
    @Mock
    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    @Mock
    private CallBackQueryHandler callBackQueryHandler;
    @Mock
    private LocationMapRepository locationMapRepository;
    @Mock
    private DocumentDogRepository documentDogRepository;

    @InjectMocks
    private CommandHandler commandHandler;

    @BeforeEach
    public void setUp() {
         commandHandler = new CommandHandler(telegramBot,shelterRepository,botUserRepository,callBackQueryHandler);

    }


    @ParameterizedTest
    @MethodSource("provideParamsForHandle")
    void handle(String text, User user) throws URISyntaxException, IOException {
        when(telegramBot.execute(any())).thenReturn(generateResponseOk());
        String json = Files.readString(
                Paths.get(CommandHandlerTest.class.getResource("/text_update.json").toURI()));
        Update update = getUpdate(json, text);
        BotUser botUser = new BotUser(123L);
        botUser.setTypeShelter(TypesShelters.DOG_SHELTER);
        when(botUserRepository.findBotUserById(any())).thenReturn(botUser);
        commandHandler.handle(text,user);
        //Then
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();
        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(123L);
    }



    @Test
    void sendMainMenuHandler() {
        when(telegramBot.execute(any())).thenReturn(generateResponseOk());
        BotUser botUser = new BotUser(123L);
        botUser.setTypeShelter(TypesShelters.DOG_SHELTER);
        when(botUserRepository.findBotUserById(any())).thenReturn(botUser);

        User user = new User(123L);

        commandHandler.sendMainMenuHandler(123L);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();
        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(123L);
    }

    @Test
    void returnResponseReplyKeyboardMarkup() {
        when(telegramBot.execute(any())).thenReturn(generateResponseOk());
        User user = new User(123L);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("INFO_ABOUT_BOT_MENU"),
                new KeyboardButton("INFO_SHELTER"));

        commandHandler.returnResponseReplyKeyboardMarkup(replyKeyboardMarkup,123l,"");

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();
        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(123L);
    }

//    @Test
//    void sendMessage() {
//
//    }
@Test
public void testSendMessage() throws TelegramException {
    long chatId = 123456;
    String messageText = "Тест!";

    TelegramBot mockedTelegramBot = mock(TelegramBot.class);
    TelegramBotUpdatesListener telegramBotUpdatesListener = new TelegramBotUpdatesListener(mockedTelegramBot,
            mock(ShelterRepository.class),
            mock(LocationMapRepository.class),
            mock(DocumentDogRepository.class),
            mock(CallBackQueryHandler.class),
            mock(CommandHandler.class),
            mock(BotUserRepository.class),
            mock(ReportRepository.class));

    telegramBotUpdatesListener.sendMessage(chatId, messageText);

    SendMessage expectedMessage = new SendMessage(chatId, messageText);
    ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);

    verify(mockedTelegramBot, times(1)).execute(argumentCaptor.capture());
    SendMessage actualMessage = argumentCaptor.getValue();

    expectedMessage = new SendMessage(chatId, messageText);

}
    private SendResponse generateResponseOk() {
        return BotUtils.fromJson("""
                { "ok": true }""", SendResponse.class);
    }
    private Update getUpdate(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%command%", replaced), Update.class);
    }

    static Stream<Arguments> provideParamsForHandle() {
        User user = new User(123L);
        return java.util.stream.Stream.of(
                //  Arguments.of(BotState.DOCUMENTS_TO_ADOPT_ANIMAL.getTitle()
                Arguments.of("Главное меню",user)
        );
    }
}