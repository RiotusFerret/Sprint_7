import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import static constants.APIs.*;
import static io.restassured.RestAssured.given;

public class Order {

    public Order(String track) {
        this.track = track;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String[] getColor() {
        return color;
    }

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;
    private boolean cancelled;
    private boolean finished;
    private boolean inDelivery;
    private String courierFirstName;
    private String createdAt;
    private String updatedAt;
    private int id;
    private int status;
    private String track;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCourierFirstName() {
        return courierFirstName;
    }

    public void setCourierFirstName(String courierFirstName) {
        this.courierFirstName = courierFirstName;
    }

    public boolean isInDelivery() {
        return inDelivery;
    }

    public void setInDelivery(boolean inDelivery) {
        this.inDelivery = inDelivery;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public void setColor(String[] color) {
        this.color = color;
    }

    public Order(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
    }

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
                .put(ORDER_ACCEPT + order.track + "?courierId=" + courier.getId());
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
