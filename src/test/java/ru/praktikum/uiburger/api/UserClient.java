package ru.praktikum.uiburger.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.praktikum.uiburger.api.model.User;

import static io.restassured.RestAssured.given;

public class UserClient {

    private static final String BASE_URL = "https://qa-stellarburgers.education-services.ru";
    private static final String REGISTER_PATH = BASE_URL + "/api/auth/register";
    private static final String LOGIN_PATH = BASE_URL + "/api/auth/login";
    private static final String USER_PATH = BASE_URL + "/api/auth/user";

    public Response register(User user) {
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .post(REGISTER_PATH);
    }

    public Response login(User user) {
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .post(LOGIN_PATH);
    }

    public Response getUser(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .get(USER_PATH);
    }

    public Response updateUser(String accessToken, User user) {
        RequestSpecification request = given()
                .header("Content-Type", "application/json")
                .body(user);
        if (accessToken != null) {
            request.header("Authorization", accessToken);
        }
        return request.patch(USER_PATH);
    }

    public Response deleteUser(String accessToken) {
        if (accessToken == null) {
            throw new IllegalArgumentException("Access token cannot be null for deletion");
        }
        System.out.println("   Удаление через API с токеном: " + accessToken);
        Response response = given()
                .header("Authorization", accessToken)
                .delete(USER_PATH);
        System.out.println("   Статус удаления: " + response.statusCode());
        return response;
    }
}