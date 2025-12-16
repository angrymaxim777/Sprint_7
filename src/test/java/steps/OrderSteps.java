package steps;

import api.OrderApi;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class OrderSteps {
    private final OrderApi orderApi = new OrderApi();

    @Step("Получить список заказов")
    public Response getOrdersList() {
        return orderApi.getOrdersList();
    }

    @Step("Проверить получение списка заказов (код 200, есть orders)")
    public void checkOrdersListReceived(Response response) {
        response.then()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }

    @Step("Отменить заказ по track номеру {track}")
    public Response cancelOrder(int track) {
        return orderApi.cancelOrder(track);
    }
}
