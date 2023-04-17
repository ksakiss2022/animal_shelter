package com.example.animal_shelter.animal_shelter.handler;

import com.example.animal_shelter.animal_shelter.model.Shelter;
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

    private  final ShelterRepository shelterRepository;


    public CommandHandler(TelegramBot telegramBot, ShelterRepository shelterRepository) {
        this.telegramBot = telegramBot;
        this.shelterRepository = shelterRepository;
    }

    public void handle(@NonNull String text, User user) {

        if ("/start".equals(text)) { // Если пользователь ввел "/start", то выводится Приветствие и список кнопок для дальнейшей работы
            sendWelcomeMessage(user);
        } else {
            telegramBot.execute(new SendMessage(user.id(), "Некорректная команда!"));
        }
    }

 /*
отправляет  приветственное сообщение и предлагает список приютов для выбора
*/
    private void sendWelcomeMessage(User user) {
        SendMessage sendMessage = new SendMessage(user.id(), "Привет!\n Меня создали Коробейникова Светлана, Салимгареева Маргарита и Кулаков Николай.\n " + "Они проходят обучение по курсу Java - разработчик. " + "\n Я буду многофункциональным ботом, отвечающим за приюты собак и кошек \n " + "Но пока ещё я мало что умею делать :-)");
        sendMessage.parseMode(ParseMode.Markdown);
        telegramBot.execute(sendMessage);

        //надо сделать выбор приюта или поиск последнего запроса и из него брать приют
//        botUser = botUserRepository.findBotUserById(user.id());
//        String lastRequest = null;
//        if (botUser != null) {
//            shelterId = botUser.getShelterId();
//            lastRequest = botUser.getLastRequest();
//            if (lastRequest != null) {
//
//            }
//        }
//        if (lastRequest == null) {
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




