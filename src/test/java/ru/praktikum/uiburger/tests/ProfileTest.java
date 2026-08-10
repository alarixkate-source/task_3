package ru.praktikum.uiburger.tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.praktikum.uiburger.pages.LoginPage;
import ru.praktikum.uiburger.pages.MainPage;
import ru.praktikum.uiburger.pages.ProfilePage;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты личного кабинета")
public class ProfileTest extends BaseTest {

    @BeforeEach
    public void createAndLoginUser() {
        Response registerResponse = userClient.register(user);
        if (registerResponse.statusCode() == 200) {
            accessToken = registerResponse.jsonPath().getString("accessToken");
        } else {
            throw new RuntimeException("Не удалось создать пользователя");
        }

        driver.get(BASE_URL);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButtonOnMain();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());
        // Ожидаем, что главная загрузилась
        mainPage.isOrderButtonDisplayed();
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    @Description("Проверяет наличие элементов профиля и корректность данных пользователя")
    public void goToProfile() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();
        ProfilePage profilePage = new ProfilePage(driver);

        assertTrue(profilePage.isProfileLinkDisplayed(), "Ссылка 'Профиль' не отображается");
        assertTrue(profilePage.isOrderHistoryLinkDisplayed(), "Ссылка 'История заказов' не отображается");
        assertTrue(profilePage.isLogoutButtonDisplayed(), "Кнопка 'Выход' не отображается");

        assertEquals(user.getName(), profilePage.getUserName(), "Имя пользователя не совпадает");
        assertEquals(user.getEmail(), profilePage.getUserEmail(), "Email пользователя не совпадает");

        // Проверяем, что поле пароля замаскировано (не равно реальному паролю)
        assertTrue(profilePage.isPasswordMasked(user.getPassword()),
                "Поле пароля не замаскировано (значение совпадает с реальным паролем или пусто)");
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по кнопке 'Конструктор'")
    public void goToConstructorFromProfile() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();
        mainPage.clickConstructor();
        assertTrue(mainPage.isOrderButtonDisplayed(), "Не удалось перейти в конструктор");
    }

    @Test
    @DisplayName("Переход из личного кабинета на главную по логотипу")
    public void goToMainByLogo() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();
        mainPage.clickLogo();
        // После клика по логотипу проверяем, что кнопка "Оформить заказ" появилась
        assertTrue(mainPage.isOrderButtonDisplayed(), "Не удалось перейти на главную по логотипу");
    }

    @Test
    @DisplayName("Выход из аккаунта")
    @Description("Проверяет выход по кнопке 'Выйти' в личном кабинете")
    public void logout() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickLogoutButton();

        // После выхода мы на странице логина, проверяем наличие формы
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isLoginFormDisplayed(), "Не удалось перейти на страницу логина после выхода");
    }
}