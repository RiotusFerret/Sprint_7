import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Test;
import static data.TestData.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTests  extends BaseTest{
    Courier courier = new Courier(LOGIN, PASSWORD, FIRST_NAME);

    @After
    public void CleanUp() {
        courier.courierDelete(courier.courierLogin(courier).jsonPath().getString("id"));
    }

    @Test
    @DisplayName("Проверка кода ответа при успешной авторизации курьера")
    @Description("Код 200 в ответе на успешный запрос")
    public void courierLoggedInSuccessfullyTest() {
        courier.courierCreate(courier);
        courier.courierLogin(courier).then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Проверка тела ответа при успешной авторизации курьера")
    @Description("Успешный запрос возвращает id")
    public void courierIdReturnedTest() throws Exception {
        courier.courierCreate(courier);
        courier.courierLogin(courier);
        if (courier.courierLogin(courier).getStatusCode() == 200) {
            MatcherAssert.assertThat(courier.getId(), notNullValue());
        } else {
            throw new Exception("Статус ответа отличается от ожидаемого");
        }
    }

    @Test
    @DisplayName("Ошибка при авторизации под несуществующим логином и существующим паролем")
    @Description("Код 404 при авторизации под несуществующим логином и существующим паролем")
    public void courierNotExistingLoginTest() {
        courier.courierCreate(courier);
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin() + "123");
        dummy.setPassword(courier.getPassword());
        dummy.courierLogin(dummy).then()
                .statusCode(404);

        if (dummy.courierCreate(dummy).getStatusCode() == 201) {
            dummy.courierDelete(dummy.courierLogin(dummy).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации под несуществующим логином и существующим паролем")
    @Description("Ожидается сообщение *Учетная запись не найдена*")
    public void courierNotExistingLoginErrorMessageTest() {
        courier.courierCreate(courier);
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin() + "123");
        dummy.setPassword(courier.getPassword());
        dummy.courierLogin(dummy).then()
                .body("message", equalTo("Учетная запись не найдена"));

        if (dummy.courierCreate(dummy).getStatusCode() == 201) {
            dummy.courierDelete(dummy.courierLogin(dummy).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Ошибка при авторизации под существующим логином и несуществующим паролем")
    @Description("Код 404 при авторизации под существующим логином и несуществующим паролем")
    public void courierNotExistingPasswordTest() {
        courier.courierCreate(courier);
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin());
        dummy.setPassword(courier.getPassword() + "123");
        dummy.courierLogin(dummy).then()
                .statusCode(404);

        if (dummy.courierCreate(dummy).getStatusCode() == 201) {
            dummy.courierDelete(dummy.courierLogin(dummy).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации под существующим логином и несуществующим паролем")
    @Description("Ожидается сообщение *Учетная запись не найдена*")
    public void courierNotExistingPasswordErrorMessageTest() {
        courier.courierCreate(courier);
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin());
        dummy.setPassword(courier.getPassword() + "123");
        dummy.courierLogin(dummy).then()
                .body("message", equalTo("Учетная запись не найдена"));

        if (dummy.courierCreate(dummy).getStatusCode() == 201) {
            dummy.courierDelete(dummy.courierLogin(dummy).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Ошибка при авторизации под неуществующим логином и несуществующим паролем")
    @Description("Код 404 при авторизации под несуществующим логином и несуществующим паролем")
    public void courierNotExistingLoginAndPasswordTest() {
        courier.courierCreate(courier);
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin() + "123");
        dummy.setPassword(courier.getPassword() + "123");
        dummy.courierLogin(dummy).then()
                .statusCode(404);

        if (dummy.courierCreate(dummy).getStatusCode() == 201) {
            dummy.courierDelete(dummy.courierLogin(dummy).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации под снеуществующим логином и несуществующим паролем")
    @Description("Ожидается сообщение *Учетная запись не найдена*")
    public void courierNotExistingLoginAndPasswordErrorMessageTest() {
        courier.courierCreate(courier);
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin() + "123");
        dummy.setPassword(courier.getPassword() + "123");
        dummy.courierLogin(dummy).then()
                .body("message", equalTo("Учетная запись не найдена"));

        if (dummy.courierCreate(dummy).getStatusCode() == 201) {
            dummy.courierDelete(dummy.courierLogin(dummy).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Ошибка при авторизации курьера без логина")
    @Description("Код 400 при авторизации курьера без логина")
    public void courierLoginWithoutLoginTest() {
        Courier dummy = new Courier();
        dummy.setPassword("1234");
        courier.courierCreate(courier);
        dummy.courierLogin(dummy).then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации курьера без логина")
    @Description("Ожидается сообщение *Недостаточно данных для входа*")
    public void courierLoginWithoutLoginErrorTest() {
        Courier dummy = new Courier();
        dummy.setPassword("1234");
        courier.courierCreate(courier);
        dummy.courierLogin(dummy).then()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Ошибка при авторизации курьера без пароля")
    @Description("Код 400 при авторизации курьера без пароля")
    public void courierLoginWithoutPasswordTest() {
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin());
        courier.courierCreate(courier);
        dummy.courierLogin(dummy).then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации курьера без пароля")
    @Description("Ожидается сообщение *Недостаточно данных для входа*")
    public void courierLoginWithoutPasswordErrorTest() {
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin());
        dummy.courierCreate(courier);
        courier.courierLogin(dummy).then()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Ошибка при авторизации с существующим логином и несуществующей парой логин-пароль")
    @Description("Код 404 при авторизации с существующим логином и несуществующей парой логин-пароль")
    public void courierLoginWithWrongPasswordTest() {
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin());
        dummy.setPassword(courier.getPassword() + "1234");
        courier.courierCreate(courier);
        dummy.courierLogin(dummy).then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации с существующим логином и несуществующей парой логин-пароль")
    @Description("Ожидается сообщение *Учетная запись не найдена*")
    public void courierLoginWithWrongPasswordErrorTest() {
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin());
        dummy.setPassword(courier.getPassword() + "1234");
        courier.courierCreate(courier);
        dummy.courierLogin(dummy).then()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Ошибка при авторизации без логина и пароля")
    @Description("Код 404 при авторизации без логина и пароля")
    public void courierLoginWithoutLoginAndPasswordTest() {
        Courier dummy = new Courier();
        courier.courierCreate(courier);
        dummy.courierLogin(dummy).then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации без логина и пароля")
    @Description("Ожидается сообщение *Учетная запись не найдена*")
    public void courierLoginWithoutLoginAndPasswordErrorTest() {
        Courier dummy = new Courier();
        courier.courierCreate(courier);
        dummy.courierLogin(dummy).then()
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
