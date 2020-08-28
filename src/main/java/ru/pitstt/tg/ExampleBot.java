package ru.pitstt.tg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.pitstt.User;
import ru.pitstt.tg.model.util.ParsingException;
import ru.pitstt.tg.model.util.UserParser;
import ru.pitstt.tg.producer.Producer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExampleBot extends TelegramLongPollingBot {

    private final TelegramProperties telegramProperties;

    private final UserParser userParser;

    private final Producer producer;

    private long chatId;

    @Override
    public void onUpdateReceived(Update update) {
        String text = update.getMessage().getText();
        chatId = update.getMessage().getChatId();
        log.info("chatId = {}, text = {}", chatId, text);
        sendMessage(chatId, getMessage(text));
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage().setChatId(chatId);
        try {
            sendMessage.setText(text);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private String getMessage(String text) {
        if (text.equals("/start")) {
            return "Здравствуйте!"+System.lineSeparator()+"Для записи на тест необходимо прислать данные в следующем формате: " + System.lineSeparator()
                    + "ФИО, Дата рождения, Город" + System.lineSeparator()
                    + "Пример: Иванов Иван Иванович, 21.02.1997, Краснодар";
        } else {
            User user;
            try {
                user = userParser.parsUserInfo(chatId, text);
                producer.produce(user);
            } catch (ParsingException e) {
                return e.getMessage();
            }
            return "Ваша заявка принята!";
        }
    }

    @Override
    public String getBotUsername() {
        return telegramProperties.getName();
    }

    @Override
    public String getBotToken() {
        return telegramProperties.getToken();
    }
}
