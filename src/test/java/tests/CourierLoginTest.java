package tests;

import dto.CourierDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import utils.DataGenerator;

public class CourierLoginTest extends BaseTest {

    @Test
    @DisplayName("Логин курьера: успешная авторизация возвращает id")
    public void courierCanLoginSuccessfully() {
        // Arrange
        createTestCourierAndGetId();

        // Act
        Response response = courierSteps.loginCourier(
                testCourier.getLogin(),
                testCourier.getPassword()
        );

        // Assert
        courierSteps.checkLoginSuccessAndGetId(response);
    }

    @Test
    @DisplayName("Логин курьера: без логина возвращает ошибку")
    public void loginWithoutLoginReturnsError() {
        // Arrange
        createTestCourierAndGetId();

        // Act - отправляем запрос без логина (передаем null)
        Response response = courierApi.loginCourier(null, testCourier.getPassword());

        // Assert - по документации 400: "Недостаточно данных для входа"
        courierSteps.checkLoginError(response);
    }

    @Test
    @DisplayName("Логин курьера: без пароля возвращает ошибку")
    public void loginWithoutPasswordReturnsError() {
        // Arrange
        createTestCourierAndGetId();

        // Act - отправляем запрос без пароля (передаем null)
        Response response = courierApi.loginCourier(testCourier.getLogin(), null);

        // Assert - 400: "Недостаточно данных для входа"
        courierSteps.checkLoginError(response);
    }

    @Test
    @DisplayName("Логин курьера: неправильный пароль возвращает ошибку")
    public void loginWithWrongPasswordReturnsError() {
        // Arrange
        createTestCourierAndGetId();

        // Act
        Response response = courierApi.loginCourier(testCourier.getLogin(), "wrong_password");

        // Assert - 400: "Недостаточно данных для входа"
        courierSteps.checkLoginError(response);
    }

    @Test
    @DisplayName("Логин курьера: несуществующий пользователь возвращает ошибку")
    public void loginNonExistentUserReturnsError() {
        // Arrange - создаем случайные данные для несуществующего пользователя
        CourierDto randomCourier = DataGenerator.generateRandomCourier();

        // Act
        Response response = courierApi.loginCourier(
                randomCourier.getLogin(),
                randomCourier.getPassword()
        );

        // Assert - 400: "Недостаточно данных для входа"
        courierSteps.checkLoginError(response);
    }
}
