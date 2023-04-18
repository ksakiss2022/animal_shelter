package com.example.animal_shelter.animal_shelter.handler;

import com.example.animal_shelter.animal_shelter.model.BotState;
import com.example.animal_shelter.animal_shelter.model.BotUser;
import com.example.animal_shelter.animal_shelter.model.Shelter;
import com.example.animal_shelter.animal_shelter.repository.BotUserRepository;
import com.example.animal_shelter.animal_shelter.repository.ShelterRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CommandHandler {
    private final TelegramBot telegramBot;

    private final ShelterRepository shelterRepository;
    private final BotUserRepository botUserRepository;
    private final CallBackQueryHandler callBackQueryHandler;

    public CommandHandler(TelegramBot telegramBot, ShelterRepository shelterRepository, BotUserRepository botUserRepository, CallBackQueryHandler callBackQueryHandler) {
        this.telegramBot = telegramBot;
        this.shelterRepository = shelterRepository;
        this.botUserRepository = botUserRepository;
        this.callBackQueryHandler = callBackQueryHandler;
    }

    public void handle(@NonNull String text, User user) {

        try {
        switch (text) {
            case "Главное меню":
               // keyBoardShelter.sendMenu(chatId);
                callBackQueryHandler.sendMainMenuHandler(user.id());
                break;
            case "Информация о возможностях бота":
                //sendMenuInfoShelter(user.id());
                break;
            case "Вернуться в меню":
                callBackQueryHandler.sendMainMenuHandler(user.id());
                break;
            case ("Узнать информацию о приюте")://действия для кнопки "Узнать информацию о приюте"
                sendMessagesForShelterInformation(user.id());
                break;
            case ("Как взять питомца из приюта"): //действия для кнопки "Как взять собаку из приюта"
                sendMessagesForAdoptDogFromShelter(user.id());
                break;
            case ("Прислать отчет о питомце"): //действия для кнопки "Прислать отчет о питомце"
                SendMessage sendMessage3 = new SendMessage(user.id(), "Нажата кнопка Прислать отчет о питомце");
                telegramBot.execute(sendMessage3);
                break;
            case ("Позвать волонтера"): //действия для кнопки "Позвать волонтера"
                SendMessage sendMessage4 = new SendMessage(user.id(), "Нажата кнопка Позвать волонтера");
                telegramBot.execute(sendMessage4);
                break;
        }

        if ("/start".equals(text)) { // Если пользователь ввел "/start", то выводится Приветствие и список кнопок для дальнейшей работы
            sendWelcomeMessage(user);

        }
        } catch (NullPointerException e) {
            System.out.println("Некорректная команда!");
        }
    }

 /*
отправляет  приветственное сообщение и предлагает список приютов для выбора
*/
    private void sendWelcomeMessage(User user) {
        SendMessage sendMessage = new SendMessage(user.id(), "Привет!\n Меня создали Коробейникова Светлана, Салимгареева Маргарита и Кулаков Николай.\n " + "Они проходят обучение по курсу Java - разработчик. " + "\n Я буду многофункциональным ботом, отвечающим за приюты собак и кошек \n " + "Но пока ещё я мало что умею делать :-)");
        sendMessage.parseMode(ParseMode.Markdown);
        telegramBot.execute(sendMessage);

        // если история по пользователю есть, то вывести последний запрос
        //если нет -  выводить выбор приюта
        BotUser botUser = botUserRepository.findBotUserById(user.id());
        String lastRequest = null;
        if (botUser != null) {

           // lastRequest = botUser.getLastRequest();
            if (lastRequest != null) {
                //выводить последний запрос
             //   callBackQueryHandler.handleInputData(user.id(), lastRequest);
            }
        }
        else {
            botUser = botUserRepository.save(new BotUser(user.id()));
        }

        if (lastRequest == null) {

            //получить список приютов и вывести их в команды
            SendMessage sendMessage1 = new SendMessage(user.id(), "Выберите приют");
            sendMessage1.parseMode(ParseMode.Markdown);

            Collection<Shelter> shelters = shelterRepository.findAll();
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
            for (Shelter shelter : shelters) {
                InlineKeyboardButton button1 = new InlineKeyboardButton(shelter.getName());
                button1.callbackData("Выбран приют -" + shelter.getId());
                keyboard.addRow(button1);
            }
            sendMessage1.replyMarkup(keyboard);
            telegramBot.execute(sendMessage1);
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

        InlineKeyboardButton button6 = new InlineKeyboardButton("Принять и записать контактные данные для связи");
        button6.callbackData(BotState.ACCEPT_RECORD_CONTACT.getTitle());

        InlineKeyboardButton button7 = new InlineKeyboardButton("Позвать волонтера");
        button7.callbackData(BotState.CALL_VOLUNTEER.getTitle());

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        keyboard.addRow(button1);
        keyboard.addRow(button2);
        keyboard.addRow(button3);
        keyboard.addRow(button4);
        keyboard.addRow(button5);
        keyboard.addRow(button6);
        keyboard.addRow(button7);

        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }


    /*
выводится список кнопок после выбора пункта "Как взять собаку из приюта"
 */
    private void sendMessagesForAdoptDogFromShelter(Object chatId) {
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

        InlineKeyboardButton button7 = new InlineKeyboardButton("Советы кинолога по первичному общению с собакой");
        button7.callbackData(BotState.CYNOLOG_ADVIVCE.getTitle());

        InlineKeyboardButton button8 = new InlineKeyboardButton("Рекомендации по проверенным кинологам для дальнейшего обращения к ним");
        button8.callbackData(BotState.CYNOLOGISTS.getTitle());

        InlineKeyboardButton button9 = new InlineKeyboardButton("Список причин, почему могут отказать и не дать забрать собаку из приюта");
        button9.callbackData(BotState.REASONS_FOR_REJECTION.getTitle());

        InlineKeyboardButton button10 = new InlineKeyboardButton("Принять и записать контактные данные для связи");
        button10.callbackData(BotState.ACCEPT_RECORD_CONTACT.getTitle());

        InlineKeyboardButton button11 = new InlineKeyboardButton("Позвать волонтера");
        button11.callbackData(BotState.CALL_VOLUNTEER.getTitle());

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        keyboard.addRow(button1);
        keyboard.addRow(button2);
        keyboard.addRow(button3);
        keyboard.addRow(button4);
        keyboard.addRow(button5);
        keyboard.addRow(button6);
        keyboard.addRow(button7);
        keyboard.addRow(button8);
        keyboard.addRow(button9);
        keyboard.addRow(button10);
        keyboard.addRow(button11);

        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }



}




