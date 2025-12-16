package utils;

import dto.CourierDto;
import dto.OrderDto;
import io.qameta.allure.Step;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Collections;

public class DataGenerator {

    @Step("Сгенерировать случайного курьера")
    public static CourierDto generateRandomCourier() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        return new CourierDto(
                "courier_" + uniqueId,
                "password_" + uniqueId,
                "CourierName_" + uniqueId
        );
    }

    @Step("Сгенерировать курьера без поля {missingField}")
    public static CourierDto generateCourierWithoutField(String missingField) {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String login = "courier_" + uniqueId;
        String password = "password_" + uniqueId;
        String firstName = "CourierName_" + uniqueId;

        switch (missingField) {
            case "login":
                login = null;
                break;
            case "password":
                password = null;
                break;
            case "firstName":
                firstName = null;
                break;
        }

        return new CourierDto(login, password, firstName);
    }

    @Step("Сгенерировать заказ с цветами {colors}")
    public static OrderDto generateOrderWithColor(List<String> colors) {
        return new OrderDto(
                "Иван",
                "Иванов",
                "Москва, ул. Ленина, д. 1",
                "4",
                "+7 800 355 35 35",
                5,
                "2024-12-31",
                "Комментарий к заказу",
                colors
        );
    }

    @Step("Получить варианты цветов для параметризации")
    public static Object[][] getColorVariants() {
        return new Object[][]{
                {"BLACK", Collections.singletonList("BLACK")},
                {"GREY", Collections.singletonList("GREY")},
                {"BLACK и GREY", Arrays.asList("BLACK", "GREY")},
                {"Без цвета", null}
        };
    }
}
