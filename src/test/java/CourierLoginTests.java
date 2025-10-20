import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static data.TestData.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTests extends BaseTest{
    Courier courier = new Courier(LOGIN, PASSWORD, FIRST_NAME);
    CourierRequests courierObject = new CourierRequests(courier);

    @Before
    public void StartUp() {
        courierObject.courierCreate(courier);
    }

    @After
    public void CleanUp() {
        courierObject.courierDelete(courierObject.courierLogin(courier).jsonPath().getString("id"));
    }

    @Test
    @DisplayName("Успешная авторизации курьера")
    @Description("Код 200 и id в ответе на успешный запрос")
    public void courierLoggedInSuccessfullyTest() {
        courierObject.courierLogin(courier).then()
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Ошибка при авторизации под несуществующим логином и существующим паролем")
    @Description("Код 404 и сообщение при авторизации под несуществующим логином и существующим паролем")
    public void courierNotExistingLoginTest() {
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin() + "123");
        dummy.setPassword(courier.getPassword());
        courierObject.courierLogin(dummy).then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));

        if (courierObject.courierCreate(dummy).getStatusCode() == SC_CREATED) {
            courierObject.courierDelete(courierObject.courierLogin(dummy).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Ошибка при авторизации под существующим логином и несуществующим паролем")
    @Description("Код 404 и сообщение при авторизации под существующим логином и несуществующим паролем")
    public void courierNotExistingPasswordTest() {
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin());
        dummy.setPassword(courier.getPassword() + "123");
        courierObject.courierLogin(dummy).then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));

        if (courierObject.courierCreate(dummy).getStatusCode() == SC_CREATED) {
            courierObject.courierDelete(courierObject.courierLogin(dummy).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Ошибка при авторизации под несуществующим логином и несуществующим паролем")
    @Description("Код 404 и сообщение при авторизации под несуществующим логином и несуществующим паролем")
    public void courierNotExistingLoginAndPasswordTest() {
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin() + "123");
        dummy.setPassword(courier.getPassword() + "123");
        courierObject.courierLogin(dummy).then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));

        if (courierObject.courierCreate(dummy).getStatusCode() == SC_CREATED) {
            courierObject.courierDelete(courierObject.courierLogin(dummy).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Ошибка при авторизации курьера без логина")
    @Description("Код 400 и сообщение при авторизации курьера без логина")
    public void courierLoginWithoutLoginTest() {
        Courier dummy = new Courier();
        dummy.setPassword("1234");
        courierObject.courierLogin(dummy).then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));

        if (courierObject.courierCreate(dummy).getStatusCode() == SC_CREATED) {
            courierObject.courierDelete(courierObject.courierLogin(dummy).jsonPath().getString("id"));
        }
    }


    @Test
    @DisplayName("Ошибка при авторизации курьера без пароля")
    @Description("Код 400 и сообщение при авторизации курьера без пароля")
    public void courierLoginWithoutPasswordTest() {
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin());
        courierObject.courierLogin(dummy).then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));

        if (courierObject.courierCreate(dummy).getStatusCode() == SC_CREATED) {
            courierObject.courierDelete(courierObject.courierLogin(dummy).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Ошибка при авторизации с существующим логином и несуществующей парой логин-пароль")
    @Description("Код 404 и сообщение при авторизации с существующим логином и несуществующей парой логин-пароль")
    public void courierLoginWithWrongPasswordTest() {
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin());
        dummy.setPassword(courier.getPassword() + "1234");
        courierObject.courierLogin(dummy).then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));

        if (courierObject.courierCreate(dummy).getStatusCode() == SC_CREATED) {
            courierObject.courierDelete(courierObject.courierLogin(dummy).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Ошибка при авторизации без логина и пароля")
    @Description("Код 404 и сообщение при авторизации без логина и пароля")
    public void courierLoginWithoutLoginAndPasswordTest() {
        Courier dummy = new Courier();
        courierObject.courierLogin(dummy).then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));

        if (courierObject.courierCreate(dummy).getStatusCode() == SC_CREATED) {
            courierObject.courierDelete(courierObject.courierLogin(dummy).jsonPath().getString("id"));
        }
    }
}
