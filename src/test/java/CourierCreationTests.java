import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import static data.TestData.*;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreationTests extends BaseTest{
    Courier courier = new Courier(LOGIN, PASSWORD, FIRST_NAME);

    @After
    public void CleanUp() {
        courier.courierDelete(courier.courierLogin(courier).jsonPath().getString("id"));
    }

    @Test
    @DisplayName("Проверка кода ответа при успешном создании курьера")
    @Description("Код 201 в ответе на успешный запрос")
    public void courierCreatedSucсessfullyTest() {
        courier.courierCreate(courier).then()
                .log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("Проверка тела ответа при успешном создании курьера")
    @Description("ok:true в ответе на успешный запрос")
    public void courierCreatedResponseBodyTest() {
        courier.courierCreate(courier).then()
                .log().all()
                .body("ok", equalTo(true));
    }
    @Test
    @DisplayName("Ошибка при создании пользователя с логином, который уже есть")
    @Description("Код 409 при создании пользователя с логином, который уже есть")
    public void courierUnableToCreateTwoIndenticalTest() {
        Courier imposter = new Courier(courier.getLogin(), "PASSWORD", FIRST_NAME);
        courier.courierCreate(courier).then()
                .log().all()
                .statusCode(201);
        Response imposterResponse = courier.courierCreate(imposter);
        imposterResponse.then().statusCode(409);

        if (imposterResponse.getStatusCode() == 201) {
            imposter.courierDelete(imposter.courierLogin(imposter).jsonPath().getString("id"));
        }
    }
    @Test
    @DisplayName("Сообщение об ошибке при создании пользователя с логином, который уже есть")
    @Description("Ожидается сообщение *Этот логин уже используется*")
    public void courierUnableToCreateTwoIdenticalErrorMessageTest() {
        Courier imposter = new Courier(courier.getLogin(), "PASSWORD", FIRST_NAME);
        courier.courierCreate(courier).then()
                .log().all()
                .statusCode(201);
        Response imposterResponse = courier.courierCreate(imposter);
        imposterResponse.then().body("message", equalTo("Этот логин уже используется"));

        if (imposter.courierCreate(imposter).getStatusCode() == 201) {
            imposter.courierDelete(imposter.courierLogin(imposter).jsonPath().getString("id"));
        }

    }

    @Test
    @DisplayName("Ошибка при создании курьера без логина")
    @Description("Код 409 при создании курьера без логина")
    public void courierCreatingWithoutLoginTest() {
        Courier dummy = new Courier();
        dummy.setPassword("1234");
        dummy.courierCreate(dummy).then().statusCode(400);

        if (dummy.courierCreate(dummy).getStatusCode() == 201) {
            dummy.courierDelete(dummy.courierLogin(dummy).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Сообщение об ошибке при создании курьера без логина")
    @Description("Ожидается сообщение *Недостаточно данных для создания учетной записи*")
    public void courierCreatingWithoutLoginErrorMessageTest() throws Exception {
        Courier dummy = new Courier();
        dummy.setPassword("1234");
           if (dummy.courierCreate(dummy).getStatusCode() == 400) {
               dummy.courierCreate(dummy).then().body("message", equalTo("Недостаточно данных для создания учетной записи"));
           } else if (dummy.courierCreate(dummy).getStatusCode() == 201) {
               dummy.courierDelete(dummy.courierLogin(dummy).jsonPath().getString("id"));
               throw new Exception("Статус ответа отличается от ожидаемого");
           } else {
               throw new Exception("Статус ответа отличается от ожидаемого");
           }
    }

    @Test
    @DisplayName("Ошибка при создании курьера без пароля")
    @Description("Код 400 при создании курьера без пароля")
    public void courierCreatingWithoutPasswordTest() {
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin() + "123");
        dummy.courierCreate(dummy).then().statusCode(400);

        if (dummy.courierCreate(dummy).getStatusCode() == 201) {
            dummy.courierDelete(dummy.courierLogin(dummy).jsonPath().getString("id"));
        }
    }
    @Test
    @DisplayName("Сообщение об ошибке при создании курьера без пароля")
    @Description("Ожидается сообщение *Недостаточно данных для создания учетной записи*")
    public void courierCreatingWithoutPasswordErrorMessageTest() throws Exception {
        Courier dummy = new Courier();
        dummy.setLogin(courier.getLogin() + "123");
        if (dummy.courierCreate(dummy).getStatusCode() == 400) {
            dummy.courierCreate(dummy).then().body("message", equalTo("Недостаточно данных для создания учетной записи"));
        } else if (dummy.courierCreate(dummy).getStatusCode() == 201) {
            dummy.courierDelete(dummy.courierLogin(dummy).jsonPath().getString("id"));
            throw new Exception("Статус ответа отличается от ожидаемого");
        } else {
            throw new Exception("Статус ответа отличается от ожидаемого");
        }
    }

    @Test
    @DisplayName("Ошибка при создании курьера без логина и пароля")
    @Description("Код 400 при создании курьера без логина и пароля")
    public void courierCreatingWithoutLoginAndPasswordTest() {
        Courier dummy = new Courier();
        dummy.courierCreate(dummy).then().statusCode(400);

        if (dummy.courierCreate(dummy).getStatusCode() == 201) {
            dummy.courierDelete(dummy.courierLogin(dummy).jsonPath().getString("id"));
        }
    }

    @Test
    @DisplayName("Сообщение об ошибке при создании курьера без логина и пароля")
    @Description("Ожидается сообщение *Недостаточно данных для создания учетной записи*")
    public void courierCreatingWithoutLoginAndPasswordErrorMessageTest() throws Exception {
        Courier dummy = new Courier();
        if (dummy.courierCreate(dummy).getStatusCode() == 400) {
            dummy.courierCreate(dummy).then().body("message", equalTo("Недостаточно данных для создания учетной записи"));
        } else if (dummy.courierCreate(dummy).getStatusCode() == 201) {
            dummy.courierDelete(dummy.courierLogin(dummy).jsonPath().getString("id"));
            throw new Exception("Статус ответа отличается от ожидаемого");
        } else {
            throw new Exception("Статус ответа отличается от ожидаемого");
        }
    }
}
