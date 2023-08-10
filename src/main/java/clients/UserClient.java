package clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.CreateUserRequest;
import pojo.LoginUserRequest;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient {
    private final String userBaseUri = "/api/auth";
    @Step("Создаем пользователя")
    public ValidatableResponse create(CreateUserRequest createUserRequest) {
        return given()
                .spec(getSpec())
                .body(createUserRequest)
                .when()
                .post(userBaseUri + "/register")
                .then();
    }
    @Step("Авторизируем пользователя")
    public ValidatableResponse login(LoginUserRequest loginUserRequest) {
        return given()
                .spec(getSpec())
                .body(loginUserRequest)
                .when()
                .post(userBaseUri + "/login")
                .then();
    }
    @Step("Удаляем пользователя")
    public ValidatableResponse delete(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(userBaseUri + "/user")
                .then();
    }
}

