package tests;

import api.CourierApi;
import dto.CourierDto;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import steps.CourierSteps;
import utils.DataGenerator;

public class BaseTest {
    protected CourierApi courierApi = new CourierApi();
    protected CourierSteps courierSteps = new CourierSteps();
    protected CourierDto testCourier;
    protected String courierId;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        cleanupTestCourier();
    }

    protected void cleanupTestCourier() {
        if (courierId != null && !courierId.isEmpty()) {
            try {
                Response deleteResponse = courierSteps.deleteCourier(courierId);
                if (deleteResponse.statusCode() == 200) {
                    System.out.println("Курьер успешно удален: " + courierId);
                } else {
                    System.out.println("Не удалось удалить курьера. Код ответа: " + deleteResponse.statusCode());
                }
            } catch (Exception e) {
                System.out.println("Исключение при удалении курьера: " + e.getMessage());
            } finally {
                courierId = null;
                testCourier = null;
            }
        }
    }

    @io.qameta.allure.Step("Создать тестового курьера и получить его ID")
    protected void createTestCourierAndGetId() {
        testCourier = DataGenerator.generateRandomCourier();
        Response createResponse = courierSteps.createCourier(testCourier);
        courierSteps.checkCourierCreatedSuccessfully(createResponse);

        Response loginResponse = courierSteps.loginCourier(
                testCourier.getLogin(),
                testCourier.getPassword()
        );
        courierId = courierSteps.checkLoginSuccessAndGetId(loginResponse);
        System.out.println("Создан тестовый курьер с ID: " + courierId);
    }
}
