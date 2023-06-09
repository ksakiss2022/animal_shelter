package com.example.animal_shelter.animal_shelter.listener;

import com.example.animal_shelter.animal_shelter.handler.CallBackQueryHandler;
import com.example.animal_shelter.animal_shelter.handler.CommandHandler;
import com.example.animal_shelter.animal_shelter.model.BotUser;
import com.example.animal_shelter.animal_shelter.model.Report;
import com.example.animal_shelter.animal_shelter.repository.*;
import com.example.animal_shelter.animal_shelter.service.ReportService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private static final Logger LOG = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private static final String REGEX_MESSAGE = "(Рацион:)(\\s)(\\W+)(;)\n" +
            "(Самочувствие:)(\\s)(\\W+)(;)\n" +
            "(Поведение:)(\\s)(\\W+)(;)";

    private final TelegramBot telegramBot;

    private static final long telegramChatVolunteers = 0;

    private long daysOfReports;

    private final ShelterRepository shelterRepository;

    private final LocationMapRepository locationMapRepository;

    private final DocumentDogRepository documentDogRepository;
    private final CallBackQueryHandler callBackQueryHandler;


    private final  BotUserRepository botUserRepository;


    private final  ReportRepository reportRepository;
    @Autowired
    private PersonDogRepository personDogRepository;
    @Autowired
    private PersonCatRepository personCatRepository;
    @Autowired
    private ReportService reportService;

    private final CommandHandler commandHandler;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, ShelterRepository shelterRepository, LocationMapRepository locationMapRepository, DocumentDogRepository documentDogRepository, CallBackQueryHandler callBackQueryHandler, CommandHandler commandHandler,
                                      BotUserRepository botUserRepository, ReportRepository reportRepository) {
        this.telegramBot = telegramBot;
        this.shelterRepository = shelterRepository;
        this.locationMapRepository = locationMapRepository;
        this.documentDogRepository = documentDogRepository;
        this.callBackQueryHandler = callBackQueryHandler;
        this.commandHandler = commandHandler;
        this.botUserRepository = botUserRepository;
        this.reportRepository = reportRepository;
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    private boolean isCat = false;

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
                    callBackQueryHandler.handle(update.callbackQuery(), update, telegramBot);
                    return;
                }
                String nameUser = update.message().chat().firstName();
                String textUpdate = update.message().text();
                Integer messageId = update.message().messageId();
                long chatIdPerson = update.message().chat().id();
                daysOfReports = reportRepository.findAll().stream()
                        .filter(s -> s.getChatId() == chatIdPerson)
                        .count() + 1;

                long compareTime = LocalDate.now().getDayOfMonth();

                Long lastMessageTime = reportRepository.findAll().stream()
                        .filter(s -> s.getChatId() == chatIdPerson)
                        .map(Report::getLastMessageMs).max(Long::compare).orElseGet(() -> null);
                if (lastMessageTime != null) {
                    Date lastDateSendMessage = new Date(lastMessageTime * 1000);
                    long numberOfDay = lastDateSendMessage.getDate();

                    if (daysOfReports < 30) {
                        if (compareTime != numberOfDay) {
                            //Обработка отчета ( Фото и текст)
                            if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                                getReport(update);
                            }
                        } else {
                            if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                                sendMessage(chatIdPerson, "Вы уже отправляли отчет сегодня");
                            }
                        }
                        if (daysOfReports == 31) {
                            sendMessage(chatIdPerson, "Вы прошли испытательный срок!");
                        }
                    }
                } else {
                    if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                        getReport(update);
                    }
                }
                if (update.message() != null && update.message().photo() != null && update.message().caption() == null) {
                    sendMessage(chatIdPerson, "Отчет нужно присылать с описанием!");
                }

                // Добавление имени и телефона в базу через кнопку оставить контакты
                if (update.message() != null && update.message().contact() != null) {
                    shareContact(update);
                }



                else if (update.message()!=null&&update.message().text()!=null) {
                    commandHandler.handle(update.message().text(), update.message().from());
                }
                //else {

                // }
                //}
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }

    public void sendForwardMessage(Long chatId, Integer messageId) {
        ForwardMessage forwardMessage = new ForwardMessage(telegramChatVolunteers, chatId, messageId);
        telegramBot.execute(forwardMessage);
    }


    public void shareContact(Update update) {
        if (update.message().contact() != null) {
            String firstName = update.message().contact().firstName();
            String lastName = update.message().contact().lastName();
            String phone = update.message().contact().phoneNumber();
            String username = update.message().chat().username();
            long finalChatId = update.message().chat().id();

            List<BotUser> sortChatId = botUserRepository.findAll().stream().filter(i -> i.getId() == finalChatId)
                    .collect(Collectors.toList());

            if (!sortChatId.isEmpty()){
                //проверить, что имя заполнено
                String nameUser = sortChatId.get(0).getName();
                if(nameUser!= null) {
                    sendMessage(finalChatId, "Вы уже в базе");
                    return;
                }
            }

            if (lastName != null ) {
                String name = firstName + " " + lastName + " " + username;
                BotUser botUser = sortChatId.get(0);
                botUser.setName(name);
                botUser.setPhone(phone);
                botUser = botUserRepository.save(botUser);
                sendMessage(finalChatId, "Вас успешно добавили в базу. Скоро вам перезвонят.");

                //Сообщение в чат волонтерам
                sendMessage(telegramChatVolunteers, phone + " " + firstName + " Добавил(а) свой номер в базу");
                sendForwardMessage(finalChatId, update.message().messageId());
            }

//            if (lastName != null) {
//                String name = firstName + " " + lastName + " " + username;
//                if(isCat){
//                    personCatRepository.save(new PersonCat(name, phone, finalChatId));
//                } else {
//                    personDogRepository.save(new PersonDog(name, phone, finalChatId));
//                }
//                sendMessage(finalChatId, "Вас успешно добавили в базу. Скоро вам перезвонят.");
//                return;
//            }
//            if (isCat) {
//                personCatRepository.save(new PersonCat(firstName, phone, finalChatId));
//            } else {
//                personDogRepository.save(new PersonDog(firstName, phone, finalChatId));
//            }
            // sendMessage(finalChatId, "Вас успешно добавили в базу. Скоро вам перезвонят.");
            //Сообщение в чат волонтерам
//            sendMessage(telegramChatVolunteers, phone + " " + firstName + " Добавил(а) свой номер в базу");
//            sendForwardMessage(finalChatId, update.message().messageId());
        }
    }

    public void getReport(Update update) {
        Pattern pattern = Pattern.compile(REGEX_MESSAGE);
        Matcher matcher = pattern.matcher(update.message().caption());
        if (matcher.matches()) {
            String ration = matcher.group(3);
            String health = matcher.group(7);
            String habits = matcher.group(11);

            GetFile getFileRequest = new GetFile(update.message().photo()[1].fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
            try {
                File file = getFileResponse.file();
//                file.fileSize();
                String fullPathPhoto = file.filePath();

                long timeDate = update.message().date();
                Date dateSendMessage = new Date(timeDate * 1000);
                byte[] fileContent = telegramBot.getFileContent(file);
                reportService.uploadReport(update.message().chat().id(), fileContent, file,
                        ration, health, habits, fullPathPhoto, dateSendMessage, timeDate, daysOfReports);


                telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет успешно принят"));

                System.out.println("Отчет успешно принят от: " + update.message().chat().id());
            } catch (IOException e) {
                System.out.println("Ошибка загрузки фото");
            }
        } else {
            GetFile getFileRequest = new GetFile(update.message().photo()[1].fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
            try {
                File file = getFileResponse.file();
                file.fileSize();
                String fullPathPhoto = file.filePath();

                long timeDate = update.message().date();
                Date dateSendMessage = new Date(timeDate * 1000);
                byte[] fileContent = telegramBot.getFileContent(file);
                reportService.uploadReport(update.message().chat().id(), fileContent, file, update.message().caption(),
                        fullPathPhoto, dateSendMessage, timeDate, daysOfReports);

                telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет успешно принят"));
                System.out.println("Отчет успешно принят от: " + update.message().chat().id());
            } catch (IOException e) {
                System.out.println("Ошибка загрузки фото");
            }

        }

    }
    @Scheduled(cron = "* 30 21 * * *")
    public void checkResults() {
        if (daysOfReports < 30) {
            var twoDay = TimeUnit.DAYS.toMillis(2);
            LocalDateTime localDateTime = LocalDateTime.now();
            long nowTime = Timestamp.valueOf(localDateTime).getTime() - twoDay;
            var getDistinct = reportRepository.findAll().stream()
                    .sorted(Comparator.comparing(Report::getChatId))
                    .max(Comparator.comparing(Report::getLastMessageMs));
            getDistinct.stream()
                    .filter(i -> i.getLastMessageMs() * 1000 < nowTime)
                    .forEach(s -> sendMessage(s.getChatId(), "Вы забыли прислать отчет"));
        }

    }
}