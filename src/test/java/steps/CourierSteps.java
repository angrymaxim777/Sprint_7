package steps;

import api.CourierApi;
import dto.CourierDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class CourierSteps {
    private final CourierApi courierApi = new CourierApi();

    @Step("Создать курьера с логином {courier.login}, паролем {courier.password} и именем {courier.firstName}")
    public Response createCourier(CourierDto courier) {
        return courierApi.createCourier(courier);
    }

    @Step("Проверить успешное создание курьера (код 201, ok: true)")
    public void checkCourierCreatedSuccessfully(Response response) {
        response.then()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
    }

    @Step("Проверить ошибку при создании курьера (код 400): Недостаточно данных для создания учетной записи")
    public void checkCourierCreationError(Response response) {
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Проверить конфликт при создании дубликата курьера (код 409): Этот логин уже используется")
    public void checkDuplicateCourierError(Response response) {
        response.then()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Step("Авторизовать курьера с логином {login}")
    public Response loginCourier(String login, String password) {
        return courierApi.loginCourier(login, password);
    }

    @Step("Проверить успешную авторизацию (код 200, есть id)")
    public String checkLoginSuccessAndGetId(Response response) {
        response.then()
                .statusCode(SC_OK)
                .body("id", notNullValue());
        return response.jsonPath().getString("id");
    }

    @Step("Проверить ошибку авторизации (код 400): Недостаточно данных для входа")
    public void checkLoginError(Response response) {
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step("Проверить ошибку авторизации (код 404): Учетная запись не найдена")
    public void checkLoginErrorWithWrongLoginOrPassword (Response response) {
        response.then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Step("Удалить курьера с id {id}")
    public Response deleteCourier(String id) {
        return courierApi.deleteCourier(id);
    }
}
