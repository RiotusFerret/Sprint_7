import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static constants.APIs.*;
import static io.restassured.RestAssured.given;

public class CourierRequests extends Courier{

    public CourierRequests(Courier courier) {}

    @Step("Запрос на создание курьера")
    public Response courierCreate(Courier courier) {
        Response createResponse = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_CREATE);
        return createResponse;
    }

    @Step("Запрос на логин курьера")
    public Response courierLogin(Courier courier) {
        Response loginResponse = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_LOGIN);
        courier.setId(loginResponse.jsonPath().getString("id"));
        return loginResponse;
    }

    @Step("Запрос на удаление курьера")
    public Response courierDelete(String id) {
        Response deleteResponse = given()
                .log().all()
                .delete(COURIER_DELETE + id);
        return deleteResponse;
    }

}
