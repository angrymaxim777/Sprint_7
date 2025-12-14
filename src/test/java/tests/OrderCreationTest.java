package tests;

import api.OrderApi;
import dto.OrderDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.DataGenerator;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTest extends BaseTest {

    private final OrderApi orderApi = new OrderApi();
    private final String colorDescription;
    private final List<String> colors;

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

        System.out.println("Создан заказ с цветами: " + colorDescription + ", track: " +
                response.jsonPath().getInt("track"));
    }
}
