package api;

import dto.CourierDto;
import dto.CourierLoginDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static utils.Config.BASE_URL;

public class CourierApi {
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
        CourierLoginDto loginData = new CourierLoginDto(login, password);
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(loginData)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удалить курьера")
    public Response deleteCourier(String id) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .when()
                .delete("/api/v1/courier/" + id);
    }
}
