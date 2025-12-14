package api;

import dto.CourierDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CourierApi {
    private static final String BASE_URL = "http://qa-scooter.praktikum-services.ru";

    @Step("Создать курьера")
    public Response createCourier(CourierDto courier) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Авторизовать курьера")
    public Response loginCourier(String login, String password) {
        String requestBody = String.format(
                "{\"login\":\"%s\",\"password\":\"%s\"}",
                login, password
        );

        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(requestBody)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удалить курьера")
    public Response deleteCourier(String id) {
        String requestBody = String.format("{\"id\":\"%s\"}", id);

        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(requestBody)
                .when()
                .delete("/api/v1/courier/" + id);
    }
}
