package api;

import dto.OrderDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static utils.Config.BASE_URL;

public class OrderApi {
    @Step("Создать заказ")
    public Response createOrder(OrderDto order) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Получить список заказов")
    public Response getOrdersList() {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .when()
                .get("/api/v1/orders");
    }

    @Step("Отменить заказ по track номеру")
    public Response cancelOrder(int track) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .when()
                .put("/api/v1/orders/cancel?track=" + track);
    }
}
