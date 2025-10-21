import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static data.TestData.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreationTests extends BaseTest{
    Courier courier = new Courier(LOGIN, PASSWORD, FIRST_NAME);
    CourierRequests courierObject = new CourierRequests(courier);

    @After
    public void CleanUp() {
        courierObject.courierDelete(courierObject.courierLogin(courier).jsonPath().getString("id"));
    }

    @Test
    @DisplayName("Проверка успешного создании курьера")
    @Description("Код 201 и сообщение в ответе на успешный запрос")
    public void courierCreatedSuccessfullyTest() {
        courierObject.courierCreate(courier).then()
                .log().all()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
    }


    @Test
    @DisplayName("Ошибка при создании пользователя с логином, который уже есть")
    @Description("Код 409 и сообщение при создании пользователя с логином, который уже есть")
    public void courierUnableToCreateTwoIdenticalTest() {
        Courier imposter = new Courier(courier.getLogin(), "PASSWORD", FIRST_NAME);
        courierObject.courierCreate(courier).then()
                .log().all()
                .statusCode(SC_CREATED);
        Response imposterResponse = courierObject.courierCreate(imposter);
        imposterResponse.then().statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));

        if (imposterResponse.getStatusCode() == SC_CREATED) {
            courierObject.courierDelete(courierObject.courierLogin(imposter).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Ошибка при создании курьера без логина")
    @Description("Код 409 и сообщение при создании курьера без логина")
    public void courierCreatingWithoutLoginTest() {
        Courier dummy = new Courier();
        dummy.setPassword("1234");
        courierObject.courierCreate(dummy).then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

        if (courierObject.courierCreate(dummy).getStatusCode() == SC_CREATED) {
            courierObject.courierDelete(courierObject.courierLogin(dummy).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Ошибка при создании курьера без пароля")
    @Description("Код 400 и сообщение при создании курьера без пароля")
    public void courierCreatingWithoutPasswordTest() {
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin() + "123");
        courierObject.courierCreate(dummy).then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

        if (courierObject.courierCreate(dummy).getStatusCode() == SC_CREATED) {
            courierObject.courierDelete(courierObject.courierLogin(dummy).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Ошибка при создании курьера без логина и пароля")
    @Description("Код 400 и сообщение при создании курьера без логина и пароля")
    public void courierCreatingWithoutLoginAndPasswordTest() {
        Courier dummy = new Courier();
        courierObject.courierCreate(dummy).then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

        if (courierObject.courierCreate(dummy).getStatusCode() == SC_CREATED) {
            courierObject.courierDelete(courierObject.courierLogin(dummy).jsonPath().getString("id"));
        }
    }
}
