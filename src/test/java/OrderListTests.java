import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import static data.TestData.*;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTests extends BaseTest{
    Courier courier = new Courier(LOGIN, PASSWORD, FIRST_NAME);
    Order order = new Order(ORDER_FIRST_NAME, ORDER_LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT);
    OrderRequests orderObject = new OrderRequests(order);
    CourierRequests courierObject = new CourierRequests(courier);

    @After
    public void ShutDown() {
        courierObject.courierDelete(courierObject.courierLogin(courier).jsonPath().getString("id"));
        orderObject.deleteOrder(order);
    }
    @Test
    @DisplayName("Проверка тела ответа в запросе на получение списка заказов")
    @Description("Поле объектов *orders* не пустое")
    public void orderListTest() {
        courierObject.courierCreate(courier);
        courierObject.courierLogin(courier);
        orderObject.createOrder(order);
        orderObject.getOrder(order);
        orderObject.acceptOrder(order, courier);
        orderObject.orderList(courier)
                .then()
                .body("orders", notNullValue());
    }
}
