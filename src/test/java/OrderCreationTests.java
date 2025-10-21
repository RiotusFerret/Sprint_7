import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static constants.Colors.BLACK;
import static constants.Colors.GREY;
import static data.TestData.*;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTests extends BaseTest{

    Order order = new Order(ORDER_FIRST_NAME, ORDER_LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT);
    OrderRequests orderObject = new OrderRequests(order);
    private final String[] color;

    public OrderCreationTests(String[] color) {
        this.color = color;
    }
    private final static String[] COLOR_EMPTY = {};
    private final static String[] COLOR_BLACK = {BLACK};
    private final static String[] COLOR_GREY = {GREY};
    private final static String[] COLOR_BOTH = {BLACK, GREY};

    @After
    public void ShutDown() {
        orderObject.deleteOrder(order);
    }

    @Parameterized.Parameters
    public static Object[][] getListData() {
        return new Object[][] {
                {COLOR_EMPTY},
                {COLOR_BLACK},
                {COLOR_GREY},
                {COLOR_BOTH},
        };
    }

    @Test
    @DisplayName("Успешное создание заказа с разными опциями выбора цвета")
    @Description("Код 201 и получение track при создании заказа")
    public void orderSuccessfullyCreatedNoColorTest() {
        order.setColor(color);
        orderObject.createOrder(order).then()
                .log().all()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
    }
}
