import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import static constants.APIs.*;
import static io.restassured.RestAssured.given;

public class OrderRequests {

    public OrderRequests(Order order) {}

    @Step("Запрос на создание заказа")
    public Response createOrder(Order order) {
        Response orderCreateResponse = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(ORDER_CREATE);
        order.setTrack(orderCreateResponse.jsonPath().getString("track"));
        return orderCreateResponse;
    }

    @Step("Запрос на удаление заказа")
    public Response deleteOrder(Order order) {
        Response orderDeleteResponse = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .put(ORDER_CANCEL + "?track=" + order.getTrack());
        return orderDeleteResponse;
    }

    @Step("Запрос на получение id заказа")
    public void getOrder(Order order) {
        String jsonString = given()
                .log().all()
                .get(ORDER_GET + "?t=" + order.getTrack())
                .asString();
        JSONObject json = new JSONObject(jsonString);
        int orderId = json.getJSONObject("order").getInt("id");
        order.setId(orderId);
    }

    @Step("Запрос на принятие заказа курьером")
    public Response acceptOrder(Order order, Courier courier) {
        Response orderAcceptResponse = given()
                .log().all()
                .put(ORDER_ACCEPT + order.getTrack() + "?courierId=" + courier.getId());
        return orderAcceptResponse;
    }

    @Step("Запрос на получение списка заказов для указанного курьера")
    public Response orderList(Courier courier) {
        Response orderAcceptResponse = given()
                .log().all()
                .get(ORDER_LIST + "?courierId=" + courier.getId());
        return orderAcceptResponse;
    }
}
