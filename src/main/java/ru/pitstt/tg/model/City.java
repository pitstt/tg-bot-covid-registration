package ru.pitstt.tg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum City {
    MOSCOW("Москва"),
    KRASNODAR("Краснодар");

    private final String name;

    public static City getEnumByName(String name) {
        for (City e : City.values()) {
            if (e.name.equals(name)) {
                return e;
            }
        }
        return null;
    }
}
