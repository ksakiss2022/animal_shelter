package com.example.animal_shelter.animal_shelter.handler;

import com.example.animal_shelter.animal_shelter.listener.TelegramBotUpdatesListener;
import com.example.animal_shelter.animal_shelter.model.BotState;
import com.example.animal_shelter.animal_shelter.model.BotUser;
import com.example.animal_shelter.animal_shelter.model.TypesShelters;
import com.example.animal_shelter.animal_shelter.repository.BotUserRepository;
import com.example.animal_shelter.animal_shelter.repository.ShelterRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CommandHandler {
    private final TelegramBot telegramBot;

    private final ShelterRepository shelterRepository;

    private final long telegramChatVolunteers = 1921555244;

    private static final String INFO_ABOUT_REPORT = "Для отчета нужна следующая информация:\n" +
            "- Фото животного.  \n" +
            "- Рацион животного\n" +
            "- Общее самочувствие и привыкание к новому месту\n" +
            "- Изменение в поведении: отказ от cтарых привычек, приобретение новых.\nСкопируйте следующий пример. Не забудьте прикрепить фото";

    private static final String REPORT_EXAMPLE = "Рацион: как в ресторане;\n" +
            "Самочувствие: здоров как бык;\n" +
            "Поведение: умильное;";
    private final BotUserRepository botUserRepository;
    private final CallBackQueryHandler callBackQueryHandler;

    private static final Logger LOG = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private static final String INFO_ABOUT_BOT = "Информация о возможностях бота \n- Бот может показать информацию о приюте \n" +
            "- Покажет какие документы нужны \n- Бот может принимать ежедневный отчет о питомце\n" +
            "- Может передать контактные данные волонтерам для связи" +
            "";

    private static final String START_MESSAGE = "Привет!\nМеня создали Коробейникова Светлана, Салимгареева Маргарита " +
            "и Кулаков Николай.\n" + "Они проходят обучение по курсу Java - разработчик. " + "\nЯ буду многофункциональным " +
            "ботом, отвечающим за приюты собак и кошек.";

    private static final String MAIN_MENU = "Главное меню";
    private static final String BACK_TO_MENU = "Вернуться в меню";
    private static final String INFO_ABOUT_BOT_MENU = "Информация о возможностях бота";
    private static final String INFO_SHELTER = "Узнать информацию о приюте";
    private static final String TAKE_PET_SHELTER = "Как взять питомца из приюта";
    private static final String SET_REPORT = "Прислать отчет о питомце";
    private static final String CALL_VOLUNTEER = "Позвать волонтера";
    private static final String SHARE_CONTACT = "Оставить контактные данные";
    private static final String INFO_VOLUNTEER = "Ваш запрос обработан. Подождите, пожалуйста, " +
            "в ближайшее время с Вами должен будет связаться волонтёр";




    public CommandHandler(TelegramBot telegramBot, ShelterRepository shelterRepository, BotUserRepository botUserRepository, CallBackQueryHandler callBackQueryHandler) {
        this.telegramBot = telegramBot;
        this.shelterRepository = shelterRepository;
        this.botUserRepository = botUserRepository;
        this.callBackQueryHandler = callBackQueryHandler;
    }

    public void handle(@NonNull String text, User user) {

        try {
        switch (text) {
            case MAIN_MENU:
            case BACK_TO_MENU:
               // keyBoardShelter.sendMenu(chatId);
                sendMainMenuHandler(user.id());
                break;
            case INFO_ABOUT_BOT_MENU:
               // sendMenuInfoShelter(user.id());
                sendMessage(user.id(), INFO_ABOUT_BOT);
                break;
            case (INFO_SHELTER)://действия для кнопки "Узнать информацию о приюте"
                sendMessagesForShelterInformation(user.id());
                break;
            case (TAKE_PET_SHELTER): //действия для кнопки "Как взять собаку из приюта"
                sendMessagesForAdoptDogFromShelter(user.id());
                break;
            case (SET_REPORT): //действия для кнопки "Прислать отчет о питомце"
                SendMessage sendMessage3 = new SendMessage(user.id(), INFO_ABOUT_REPORT);
                telegramBot.execute(sendMessage3);
                SendMessage sendMessage31 = new SendMessage(user.id(), REPORT_EXAMPLE);
                telegramBot.execute(sendMessage31);
                break;
            case (CALL_VOLUNTEER): //действия для кнопки "Позвать волонтера"
                SendMessage sendMessage4 = new SendMessage(user.id(), INFO_VOLUNTEER);
                telegramBot.execute(sendMessage4);
                sendMessage(telegramChatVolunteers,  "Пользователь "+ user.id() + " " + user.firstName() + " " +
                        user.username() + " хочет связаться с " + "волонтёром. Перезвоните ему, пожалуйста!");
                break;

            case "\uD83D\uDC31 CAT":
                //запомнить выбранный приют
               BotUser botUser = botUserRepository.save(new BotUser(Long.valueOf(user.id().toString()),TypesShelters.CAT_SHELTER) );
                //вывести список команд главного меню
                sendMainMenuHandler(user.id());
                break;
            case "\uD83D\uDC36 DOG":
                //запомнить выбранный приют
                BotUser botUser1 = botUserRepository.save(new BotUser(Long.valueOf(user.id().toString()),TypesShelters.DOG_SHELTER ));
                //вывести список команд главного меню
                sendMainMenuHandler(user.id());
                break;
        }

            if ("/start".equals(text)) { // Если пользователь ввел "/start", то выводится Приветствие и список кнопок для дальнейшей работы
                sendWelcomeMessage(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 /*
отправляет  приветственное сообщение и предлагает список приютов для выбора
*/
    private void sendWelcomeMessage(User user) {
        SendMessage sendMessage = new SendMessage(user.id(), START_MESSAGE);
        sendMessage.parseMode(ParseMode.Markdown);
        telegramBot.execute(sendMessage);

        // если история по пользователю есть, то выводить главное меню
        //если нет -  выводить выбор приюта
        BotUser botUser = botUserRepository.findBotUserById(user.id());
        TypesShelters typeShelter = null;
        if (botUser != null) {
            typeShelter = botUser.getTypeShelter();
            if (typeShelter != null) {
                //выводить главное меню
                sendMainMenuHandler(user.id());
            }
        }
        if (typeShelter == null) {
            LOG.info("Method sendMessage has been run: {}, {}", user.id(), "Вызвано меню выбора ");
            //получить список приютов и вывести их в команды
            SendMessage sendMessage1 = new SendMessage(user.id(), "Выберите приют");
            sendMessage1.parseMode(ParseMode.Markdown);
            String emojiCat = EmojiParser.parseToUnicode(":cat:");
            String emojiDog = EmojiParser.parseToUnicode(":dog:");

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                    new KeyboardButton(emojiCat + " CAT"));
            replyKeyboardMarkup.addRow(new KeyboardButton(emojiDog + " DOG"));

            returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, user.id(), "Выберите, кого хотите приютить:");
        }
    }

/*
выводится список кнопок после выбора пункта "Узнать информацию о приюте"
 */
    private void sendMessagesForShelterInformation(Object chatId) {
        //выводимое сообщение
        SendMessage sendMessage = new SendMessage(chatId.toString(), "Что Вы хотите узнать о приюте?");
        sendMessage.parseMode(ParseMode.Markdown);
        //выводится список кнопок для выбора
        InlineKeyboardButton button1 = new InlineKeyboardButton("Рассказать о приюте");
        button1.callbackData(BotState.SHELTER_TELL.getTitle());

        InlineKeyboardButton button2 = new InlineKeyboardButton("Расписание работы приюта");
        button2.callbackData(BotState.SHELTER_SCHEDULE.getTitle());

        InlineKeyboardButton button3 = new InlineKeyboardButton("Адрес приюта");
        button3.callbackData(BotState.SHELTER_ADDRESS.getTitle());

        InlineKeyboardButton button4 = new InlineKeyboardButton("Схема проезда к приюту");
        button4.callbackData(BotState.SHELTER_LOCATION_MAP.getTitle());

        InlineKeyboardButton button5 = new InlineKeyboardButton("Общие рекомендации о технике безопасности на территории приюта");
        button5.callbackData(BotState.SAFETY_RECOMMENDATIONS.getTitle());

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        keyboard.addRow(button1);
        keyboard.addRow(button2);
        keyboard.addRow(button3);
        keyboard.addRow(button4);
        keyboard.addRow(button5);

        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }


    /*
выводится список кнопок после выбора пункта "Как взять животное из приюта"
 */
    private void sendMessagesForAdoptDogFromShelter(Object chatId) {
        //получить тип выбранного приюта пользователем
        //в зависимости от выбранного приюта, вывести рекомендации для кошек/рекомендаии для собак
        BotUser botUser = botUserRepository.findBotUserById(Long.valueOf(chatId.toString()));
        TypesShelters typeShelter = botUser.getTypeShelter();


        //выводимое сообщение
        SendMessage sendMessage = new SendMessage(chatId.toString(), "Выберите нужный вид консультации");
        sendMessage.parseMode(ParseMode.Markdown);

        //выводится список кнопок для выбора
        InlineKeyboardButton button1 = new InlineKeyboardButton("Правила знакомства с животным");
        button1.callbackData(BotState.ANIMAL_DATING_RULES.getTitle());

        InlineKeyboardButton button2 = new InlineKeyboardButton("Документы, необходимых для того, чтобы взять животное из приюта");
        button2.callbackData(BotState.DOCUMENTS_TO_ADOPT_ANIMAL.getTitle());

        InlineKeyboardButton button3 = new InlineKeyboardButton("Рекомендаций по транспортировке животного");
        button3.callbackData(BotState.SHIPPING_RECOMMENDATIONS.getTitle());

        InlineKeyboardButton button4 = new InlineKeyboardButton("Рекомендации по обустройству дома для щенка/котенка");
        button4.callbackData(BotState.PUPPY_KITTEN_HOME_IMPROVEMENT_TIPES.getTitle());

        InlineKeyboardButton button5 = new InlineKeyboardButton("Рекомендации по обустройству дома для взрослого животного");
        button5.callbackData(BotState.ANIMAL_HOME_IMPROVEMENT_TIPES.getTitle());

        InlineKeyboardButton button6 = new InlineKeyboardButton("Рекомендации по обустройству дома для животного с ограниченными возможностями (зрение, передвижение)");
        button6.callbackData(BotState.ANIMAL_WITH_DISABILITY_HOME_IMPROVEMENT_TIPES.getTitle());

        InlineKeyboardButton button9 = new InlineKeyboardButton("Список причин, почему могут отказать и не дать забрать животное из приюта");
        button9.callbackData(BotState.REASONS_FOR_REJECTION.getTitle());

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        keyboard.addRow(button1);
        keyboard.addRow(button2);
        keyboard.addRow(button3);
        keyboard.addRow(button4);
        keyboard.addRow(button5);
        keyboard.addRow(button6);

        if(typeShelter==TypesShelters.DOG_SHELTER) {
            InlineKeyboardButton button7 = new InlineKeyboardButton("Советы кинолога по первичному общению с собакой");
            button7.callbackData(BotState.CYNOLOG_ADVICE.getTitle());

            InlineKeyboardButton button8 = new InlineKeyboardButton("Рекомендации по проверенным кинологам для дальнейшего обращения к ним");
            button8.callbackData(BotState.CYNOLOGISTS.getTitle());

            keyboard.addRow(button7);
            keyboard.addRow(button8);
        }

        keyboard.addRow(button9);

        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    /*
    После выбора приюта предлагается список команд для дальнейшей работы:
    - Узнать информацию о приюте (этап 1).
    - Как взять собаку из приюта (этап 2).
    - Прислать отчет о питомце (этап 3).
    - Позвать волонтера.
     */
    public void sendMainMenuHandler(Object chatId) {
        LOG.info("Method sendMainMenuHandler has been run: {}, {}", chatId, "вызвали Главное меню");

        BotUser botUser = botUserRepository.findBotUserById(Long.valueOf(chatId.toString()));
        TypesShelters typeShelter = botUser.getTypeShelter();

        SendMessage sendMessage = new SendMessage(chatId, "Выбран " + typeShelter);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(INFO_ABOUT_BOT_MENU),
                new KeyboardButton(INFO_SHELTER));
        replyKeyboardMarkup.addRow(new KeyboardButton(TAKE_PET_SHELTER),
                new KeyboardButton(SET_REPORT));
        replyKeyboardMarkup.addRow(new KeyboardButton(CALL_VOLUNTEER),
                new KeyboardButton(SHARE_CONTACT).requestContact(true));



        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, Long.valueOf(chatId.toString()), MAIN_MENU);

    }
    public void returnResponseReplyKeyboardMarkup(ReplyKeyboardMarkup replyKeyboardMarkup, Long chatId, String text) {
        replyKeyboardMarkup.resizeKeyboard(true);
        replyKeyboardMarkup.oneTimeKeyboard(false);
        replyKeyboardMarkup.selective(false);
        SendMessage request = new SendMessage(chatId, text)
                .replyMarkup(replyKeyboardMarkup)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);
        SendResponse sendResponse = telegramBot.execute(request);
        if (!sendResponse.isOk()) {
            int codeError = sendResponse.errorCode();
            String description = sendResponse.description();
            LOG.info("code of error: {}", codeError);
            LOG.info("description -: {}", description);
        }
    }

    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }
}




