package com.example.animal_shelter.animal_shelter.listener;

import com.pengrad.telegrambot.response.GetFileResponse;
import com.example.animal_shelter.animal_shelter.handler.CallBackQueryHandler;
import com.example.animal_shelter.animal_shelter.handler.CommandHandler;
import com.example.animal_shelter.animal_shelter.model.BotUser;
import com.example.animal_shelter.animal_shelter.model.Report;
import com.example.animal_shelter.animal_shelter.repository.*;
import com.example.animal_shelter.animal_shelter.service.ReportService;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

    @Mock
    private ReportService reportService;

    private static final int DAYS_OF_REPORTS = 7;

    @InjectMocks
    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    @BeforeEach
    public void setUp() {
        reportService = mock(ReportService.class); // initialize the reportService mock
        telegramBotUpdatesListener = new TelegramBotUpdatesListener(telegramBot,
                shelterRepository,
                locationMapRepository, documentDogRepository, callBackQueryHandler,
                commandHandler, botUserRepository, reportRepository);
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

    @Test
    public void testSendForwardMessage() throws TelegramException {
        Long chatId = 123456L;
        Integer messageId = 789;
        String telegramChatVolunteers = "VolunteersChat";

        TelegramBot mockedTelegramBot = mock(TelegramBot.class);
        TelegramBotUpdatesListener telegramBotUpdatesListener = new TelegramBotUpdatesListener(mockedTelegramBot,
                mock(ShelterRepository.class),
                mock(LocationMapRepository.class),
                mock(DocumentDogRepository.class),
                mock(CallBackQueryHandler.class),
                mock(CommandHandler.class),
                mock(BotUserRepository.class),
                mock(ReportRepository.class));

        telegramBotUpdatesListener.sendForwardMessage(chatId, messageId);

        ForwardMessage expectedForwardMessage = new ForwardMessage(telegramChatVolunteers, chatId, messageId);
        ArgumentCaptor<ForwardMessage> argumentCaptor = ArgumentCaptor.forClass(ForwardMessage.class);
        verify(mockedTelegramBot, times(1)).execute(argumentCaptor.capture());
        ForwardMessage actualMessage = argumentCaptor.getValue();

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


    //getReport()

    //checkResults()
    @Test
    public void shouldSendMessageToChatIdWhenLastMessageMsIsOlderThanNowTimeMinusTwoDays() {

        Report report1 = mock(Report.class);
        Report report2 = mock(Report.class);
        List<Report> reportList = new ArrayList<>();
        reportList.add(report1);
        reportList.add(report2);
        when(reportRepository.findAll()).thenReturn(reportList);
        when(report1.getChatId()).thenReturn(1L);
        when(report1.getLastMessageMs()).thenReturn(1600000000L);
        when(report2.getChatId()).thenReturn(2L);
        when(report2.getLastMessageMs()).thenReturn(1600005000L);

        telegramBotUpdatesListener.checkResults();


        verify(reportRepository).findAll();
        verify(telegramBot).execute(any(SendMessage.class));
    }

    private SendResponse generateResponseOk() {
        return BotUtils.fromJson("""
                { "ok": true }""", SendResponse.class);
    }

//    @Test
//    public void testGetReport() throws IOException {
//        // arrange
//        String regMessage = "/report01 /photo";
//        Date dateSendMessage = new Date(1600000000L * 1000);
//        Update update = mock(Update.class);
//        Message message = mock(Message.class);
//        Chat chat = mock(Chat.class);
//        PhotoSize[] photoSizes = new PhotoSize[]{mock(PhotoSize.class), mock(PhotoSize.class)};
//        when(update.message()).thenReturn(message);
//        when(message.caption()).thenReturn(regMessage);
//        when(message.photo()).thenReturn(photoSizes);
//        when(message.chat()).thenReturn(chat);
//        when(message.date()).thenReturn(1600000000);
//        when(photoSizes[1].fileId()).thenReturn("1");
//        File file = new File();
//        Path path = Path.of("ghhhh");
//        toString().intern();
//        doAnswer(invocation -> {
//            Object arg = invocation.getArgument(0);
//            // выполнение необходимых действий с аргументом метода
//            return null; // метод ничего не возвращает, поэтому возвращаем null
//        }).when(telegramBot).execute(any(GetFile.class));
//        GetFileResponse getFileResponse = mock(GetFileResponse.class);
//        when(telegramBot.execute(any(GetFile.class))).thenReturn(getFileResponse);
//        when(getFileResponse.file()).thenReturn(file);
//        File file1 = mock(File.class);
//        when(file1.filePath()).thenReturn(String.valueOf(path));
//        byte[] fileContent = "Test content".getBytes();
//        when(telegramBot.getFileContent(any(File.class))).thenReturn(fileContent);
//        SendMessage sendMessage = new SendMessage(1L, "Отчет успешно принят");
//        when(telegramBot.execute(any(SendMessage.class))).thenAnswer(invocation -> sendMessage);
////        doNothing().when(reportService).uploadReport(anyLong(), any(byte[].class), any(File.class), anyString(),
////                anyString(), any(Date.class), anyLong(), anyInt());
//        verify(reportService).uploadReport(anyLong(), any(byte[].class), any(File.class), anyString(),
//                anyString(), any(Date.class), anyLong(), anyInt());
//        telegramBotUpdatesListener.getReport(update);
//
//
//        verify(update, times(2)).message();
//        verify(message, times(1)).caption();
//        verify(message, times(2)).photo();
//        verify(message, times(1)).chat();
//        verify(message, times(1)).date();
//        when(photoSizes[1]).thenReturn(mock(PhotoSize.class));
//        when(photoSizes[1].fileId()).thenReturn("1");
//        verify(reportService).uploadReport(anyLong(), any(byte[].class), any(File.class), anyString(),
//                anyString(), any(Date.class), anyLong(), anyInt());
//        verify(telegramBot).getFileContent(any(File.class));
//        verify(telegramBot).execute(any(GetFile.class));
//        verify(telegramBot).execute(any(SendMessage.class));
//    }


}