package ru.pitstt.tg.model.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.pitstt.User;
import ru.pitstt.tg.model.City;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserParser {

    public User parsUserInfo(long chatId, String text) throws ParsingException {
        String[] userInfo = text.split(",");
        if (userInfo.length != 3) {
            log.error("not valid user information: {}", text);
            throw new ParsingException("Укажите информацию в правильном формате: " + System.lineSeparator()
                    + "Пример: Иванов Иван Иванович, 21.02.1997, Краснодар");
        }
        String[] fio = userInfo[0].split(" ");
        if (fio.length != 3) {
            log.error("not valid user  information: {}", userInfo[0]);
            throw new ParsingException("Полностью укажите ФИО: " + System.lineSeparator()
                    + "Пример: Иванов Иван Иванович");
        }
        City city = City.getEnumByName(userInfo[2].trim());
        if (city == null) {
            log.error("not valid city: {}", userInfo[2]);
            throw new ParsingException("В текущий момент город " + userInfo[2] + " не осуществляет регистрацию через Telegram, " +
                    "Поддерживаемые города: " + Arrays.stream(City.values()).map(City::getName).collect(Collectors.joining(", ")));
        }
        return new User(chatId, fio[0], fio[1], fio[2], userInfo[1].trim(), city.getName());
    }
}
