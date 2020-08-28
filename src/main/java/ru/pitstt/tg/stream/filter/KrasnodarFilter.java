package ru.pitstt.tg.stream.filter;

import ru.pitstt.User;
import ru.pitstt.tg.model.City;

public class KrasnodarFilter {
    public static boolean apply(Long key, User value) {
        return City.getEnumByName(value.getCity()) == City.KRASNODAR;
    }
}