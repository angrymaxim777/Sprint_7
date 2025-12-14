package steps;

import api.OrderApi;
import dto.OrderDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.notNullValue;

public class OrderSteps {
    private final OrderApi orderApi = new OrderApi();

    @Step("Создать заказ с цветами {order.color}")
    public Response createOrder(OrderDto order) {
        return orderApi.createOrder(order);
    }

    @Step("Проверить успешное создание заказа (код 201, есть track)")
    public void checkOrderCreatedSuccessfully(Response response) {
        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Step("Получить список заказов")
    public Response getOrdersList() {
        return orderApi.getOrdersList();
    }

    @Step("Проверить получение списка заказов (код 200, есть orders)")
    public void checkOrdersListReceived(Response response) {
        response.then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
