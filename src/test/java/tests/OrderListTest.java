package tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import steps.OrderSteps;

public class OrderListTest extends BaseTest {

    private final OrderSteps orderSteps = new OrderSteps();

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrdersListReturnsListOfOrders() {
        // Act
        Response response = orderSteps.getOrdersList();

        // Assert
        orderSteps.checkOrdersListReceived(response);
    }
}
