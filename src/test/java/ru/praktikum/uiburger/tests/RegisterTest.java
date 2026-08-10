package ru.praktikum.uiburger.tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.praktikum.uiburger.pages.LoginPage;
import ru.praktikum.uiburger.pages.MainPage;
import ru.praktikum.uiburger.pages.RegisterPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты регистрации")
public class RegisterTest extends BaseTest {

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Проверяет, что после регистрации пользователь перенаправляется на страницу логина")
    public void successfulRegistration() throws Exception {
        // 1. Регистрация через UI
        driver.get(BASE_URL + "register");
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(user.getName(), user.getEmail(), user.getPassword());

        // 2. Ожидаем редирект на страницу /login
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/login"));

        // 3. Проверяем, что форма логина отображается. Не будем логинится в ui здесь. Ниже будем через апи получать токен для удаления, проверим что регистрация ок там
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isLoginFormDisplayed(),
                "После регистрации не произошёл редирект на страницу логина");

        // 4. Получаем токен через API-логин для удаления пользователя
        Response loginResponse = userClient.login(user);
        if (loginResponse.statusCode() == 200) {
            accessToken = loginResponse.jsonPath().getString("accessToken");
        } else {
            throw new RuntimeException("Не удалось получить токен для удаления пользователя: " +
                    loginResponse.body().asString());
        }
    }

    @Test
    @DisplayName("Ошибка при пароле менее 6 символов")
    @Description("Проверяет, что при пароле <6 символов появляется сообщение об ошибке")
    public void shortPasswordError() {
        driver.get(BASE_URL);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButtonOnMain();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(user.getName(), user.getEmail(), "12345");

        String errorText = registerPage.getErrorMessageText();
        assertTrue(errorText.contains("Некорректный пароль") ||
                        errorText.contains("Пароль должен быть не менее 6 символов"),
                "Неверное сообщение об ошибке: " + errorText);
        // Пользователь не создан, удаление не требуется
    }
}