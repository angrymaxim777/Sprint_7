package tests;

import api.OrderApi;
import dto.OrderDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.DataGenerator;
import steps.OrderSteps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTest extends BaseTest {

    private final OrderApi orderApi = new OrderApi();
    private final OrderSteps orderSteps = new OrderSteps();
    private final String colorDescription;
    private final List<String> colors;
    private final List<Integer> tracks = new ArrayList<>();

    public OrderCreationTest(String colorDescription, List<String> colors) {
        this.colorDescription = colorDescription;
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "Цвет заказа: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(DataGenerator.getColorVariants());
    }

    @Test
    @DisplayName("Создание заказа с параметризацией цветов")
    public void createOrderWithDifferentColorOptions() {
        // Arrange
        OrderDto order = DataGenerator.generateOrderWithColor(colors);

        // Act
        Response response = orderApi.createOrder(order);

        // Assert
        response.then()
                .statusCode(201)
                .body("track", notNullValue());

        int track = response.jsonPath().getInt("track");
        tracks.add(track);
        System.out.println("Создан заказ с цветами: " + colorDescription + ", track: " + track);
    }

    @After
    public void tearDown() {
        // Отменяем все созданные заказы
        for (Integer track : tracks) {
            try {
                Response cancelResponse = orderSteps.cancelOrder(track);
                if (cancelResponse.statusCode() == 200) {
                    System.out.println("Заказ с track " + track + " отменен");
                }
            } catch (Exception e) {
                System.out.println("Не удалось отменить заказ с track " + track + ": " + e.getMessage());
            }
        }

        // Очищаем список tracks
        tracks.clear();
        super.tearDown();
    }
}
