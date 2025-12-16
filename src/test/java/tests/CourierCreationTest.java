package tests;

import dto.CourierDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import utils.DataGenerator;

public class CourierCreationTest extends BaseTest {

    @Test
    @DisplayName("Создание курьера: успешное создание возвращает ok: true")
    public void courierCanBeCreatedSuccessfully() {
        // Arrange
        testCourier = DataGenerator.generateRandomCourier();

        // Act
        Response response = courierSteps.createCourier(testCourier);

        // Assert
        courierSteps.checkCourierCreatedSuccessfully(response);

        // Получаем ID для удаления в tearDown
        Response loginResponse = courierSteps.loginCourier(
                testCourier.getLogin(),
                testCourier.getPassword()
        );
        courierId = courierSteps.checkLoginSuccessAndGetId(loginResponse);
    }

    @Test
    @DisplayName("Создание курьера: нельзя создать двух одинаковых курьеров")
    public void cannotCreateTwoIdenticalCouriers() {
        // Arrange - создаем первого курьера
        testCourier = DataGenerator.generateRandomCourier();
        Response firstResponse = courierSteps.createCourier(testCourier);
        courierSteps.checkCourierCreatedSuccessfully(firstResponse);

        // Получаем ID первого курьера для очистки
        Response loginResponse = courierSteps.loginCourier(
                testCourier.getLogin(),
                testCourier.getPassword()
        );
        courierId = courierSteps.checkLoginSuccessAndGetId(loginResponse);

        // Act - пытаемся создать второго с теми же данными
        Response secondResponse = courierSteps.createCourier(testCourier);

        // Assert - ожидаем 409 Conflict
        courierSteps.checkDuplicateCourierError(secondResponse);
    }

    @Test
    @DisplayName("Создание курьера: без логина возвращает ошибку")
    public void createCourierWithoutLoginReturnsError() {
        // Arrange
        CourierDto courier = DataGenerator.generateCourierWithoutField("login");

        // Act
        Response response = courierSteps.createCourier(courier);

        // Assert
        courierSteps.checkCourierCreationError(response);
    }

    @Test
    @DisplayName("Создание курьера: без пароля возвращает ошибку")
    public void createCourierWithoutPasswordReturnsError() {
        // Arrange
        CourierDto courier = DataGenerator.generateCourierWithoutField("password");

        // Act
        Response response = courierSteps.createCourier(courier);

        // Assert
        courierSteps.checkCourierCreationError(response);
    }

    @Test
    @DisplayName("Создание курьера: без имени возвращает ошибку")
    public void createCourierWithoutFirstNameReturnsError() {
        // Arrange
        CourierDto courier = DataGenerator.generateCourierWithoutField("firstName");

        // Act
        Response response = courierSteps.createCourier(courier);

        // Assert
        courierSteps.checkCourierCreationError(response);
    }
}
