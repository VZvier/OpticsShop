package com.vzv.shop.data;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Translate {

    private static final String[] eng = {"id", "nomination", "brand", "model", "frameType", "frameSize", "gender",
            "lensType", "sp", "cyl", "distance", "price", "volume", "description", "available", "ready-glasses",
            "frame", "lens", "liquid", "medical-equipment", "plastic", "metal", "plastic semi-rimless",
            "metal semi-rimless", "rimless", "childish", "adult", "man", "woman", "universal", "stigmatic", "astigmatic",
            "computer", "blue-block", "photochrom(brown)", "photochrom(gray)", "aspherical", "orange", "UV-protection",
            "true", "false", "coefficient", "country"};

    private static final String[] rus = {"Код товара", "Наименование", "Производитель", "Модель", "Оправа", "Размер",
            "Пол", "Тип линзы", "Сфера", "Цылиндр", "Расстояние", "Цена", "Объем", "Описание", "Наличие", "Очки",
            "Оправа", "Линза", "Жидкость", "Мед. оборудование", "Пластик", "Металл", "Пластик полуоправая",
            "Металл полуоправая", "Безоправная", "Для детей", "Для взрослого", "Для мужчины", "Для женщины", "Унисекс",
            "Стигматическая", "Астигматическая", "Компьютерная", "Компьютерная(Blue-block)", "Фотохромная (коричневая)",
            "Фотохромная(серая)", "Асферическая", "Водительская", "Солнце-защитная", "Да", "Нет", "Коэффициент", "Страна"};

    private static final Map<String, String> EN_TO_RU = new HashMap<>();

    static {
        for (String word : eng) {
            EN_TO_RU.put(word, rus[List.of(eng).indexOf(word)]);
        }
    }

    private static final Map<String, String> RU_TO_EN = new HashMap<>();

    static {
        for (String word : rus) {
            RU_TO_EN.put(word, eng[List.of(rus).indexOf(word)]);
        }
    }

    public static String getRu(String word) {
        return EN_TO_RU.get(word);
    }

    public static String getEn(String word) {
        return RU_TO_EN.get(word);
    }
}
