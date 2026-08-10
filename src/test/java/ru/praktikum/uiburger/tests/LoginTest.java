package ru.praktikum.uiburger.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.praktikum.uiburger.pages.ForgotPasswordPage;
import ru.praktikum.uiburger.pages.LoginPage;
import ru.praktikum.uiburger.pages.MainPage;
import ru.praktikum.uiburger.pages.RegisterPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты входа")
public class LoginTest extends BaseTest {

    @BeforeEach
    public void createUser() {
        Response registerResponse = userClient.register(user);
        if (registerResponse.statusCode() == 200) {
            accessToken = registerResponse.jsonPath().getString("accessToken");
        } else {
            throw new RuntimeException("Не удалось создать пользователя: " + registerResponse.body().asString());
        }
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    public void loginViaMainButton() {
        driver.get(BASE_URL);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButtonOnMain();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());
        // Теперь проверяем кнопку на главной, используя ожидание внутри mainPage
        assertTrue(mainPage.isOrderButtonDisplayed(), "Кнопка 'Оформить заказ' не отображается после входа");
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    public void loginViaPersonalAccount() {
        driver.get(BASE_URL);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());
        assertTrue(mainPage.isOrderButtonDisplayed(), "Кнопка 'Оформить заказ' не отображается после входа");
    }

    @Test
    @DisplayName("Вход через ссылку в форме регистрации")
    public void loginViaRegisterForm() {
        // Переходим сразу на страницу регистрации
        driver.get(BASE_URL + "register");
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickLoginLink(); // кликаем по ссылке "Войти"
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());
        MainPage mainPage = new MainPage(driver);
        assertTrue(mainPage.isOrderButtonDisplayed(), "Кнопка 'Оформить заказ' не отображается после входа");
    }

    @Test
    @DisplayName("Вход через ссылку в форме восстановления пароля")
    public void loginViaForgotPasswordForm() {
        // Переходим сразу на страницу восстановления пароля
        driver.get(BASE_URL + "forgot-password");
        ForgotPasswordPage forgotPage = new ForgotPasswordPage(driver);
        forgotPage.clickLoginLink(); // ссылка "Войти" на странице восстановления
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());
        MainPage mainPage = new MainPage(driver);
        assertTrue(mainPage.isOrderButtonDisplayed(), "Кнопка 'Оформить заказ' не отображается после входа");
    }
}