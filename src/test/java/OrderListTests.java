import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import static data.TestData.*;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTests  extends BaseTest{
    Courier courier = new Courier(LOGIN, PASSWORD, FIRST_NAME);
    Order order1 = new Order(ORDER_FIRST_NAME, ORDER_LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT);

    @After
    public void ShutDown() {
        courier.courierDelete(courier.courierLogin(courier).jsonPath().getString("id"));
        order1.deleteOrder(order1);
    }
    @Test
    @DisplayName("Проверка тела ответа в запросе на получение списка заказов")
    @Description("Поле объектов *orders* не пустое")
    public void orderListTest() {
        courier.courierCreate(courier);
        courier.courierLogin(courier);
        order1.createOrder(order1);
        order1.getOrder(order1);
        order1.acceptOrder(order1, courier);
                order1.orderList(courier)
               .then()
                .body("orders", notNullValue());

    }
}
