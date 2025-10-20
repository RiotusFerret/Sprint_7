import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static constants.APIs.*;
import static io.restassured.RestAssured.given;

public class Courier {
private String login;
private String password;
private String firstName;
private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Courier(String login, String password, String firstName) {
        this.firstName = firstName;
        this.login = login;
        this.password = password;
    }

    public Courier() {
    }

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
        courier.id = loginResponse.jsonPath().getString("id");
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
