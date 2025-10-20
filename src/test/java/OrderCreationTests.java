import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static constants.Colors.BLACK;
import static constants.Colors.GREY;
import static data.TestData.*;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTests  extends BaseTest{

    Order order = new Order(ORDER_FIRST_NAME, ORDER_LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT);
    private final String[] color;

    public OrderCreationTests(String[] color) {
        this.color = color;
    }
    private final static String[] colorEmpty = {};
    private final static String[] colorBlack = {BLACK};
    private final static String[] colorGrey = {GREY};
    private final static String[] colorBoth = {BLACK, GREY};

    @After
    public void ShutDown() {
        order.deleteOrder(order);
    }

    @Parameterized.Parameters
    public static Object[][] getListData() {
        return new Object[][] {
                {colorEmpty},
                {colorBlack},
                {colorGrey},
                {colorBoth},
        };
    }

    @Test
    @DisplayName("Успешное создание заказа с разными опциями выбора цвета")
    @Description("Код 201 создание заказа")
    public void orderSuccessfullyCreatedNoColorTest() {
        order.setColor(color);
        order.createOrder(order).then()
                .log().all()
                .statusCode(201);
    }
    @Test
    @DisplayName("Проверка тела ответа при создании заказа с разными опциями выбора цвета")
    @Description("Успешный запрос возвращает track")
    public void orderTrackReturnedTest() {
        order.setColor(color);
        order.createOrder(order).then()
                .log().all()
                .body("track", notNullValue());
    }

}
