package ru.praktikum.uiburger.tests;

import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import ru.praktikum.uiburger.api.UserClient;
import ru.praktikum.uiburger.api.model.User;
import ru.praktikum.uiburger.driver.WebDriverCreator;

@ExtendWith(AllureJunit5.class)
public abstract class BaseTest {

    protected WebDriver driver;
    protected UserClient userClient;
    protected User user;
    protected String accessToken;
    protected final String BASE_URL = "https://qa-stellarburgers.education-services.ru/";

    @BeforeEach
    public void setUp() {
        driver = WebDriverCreator.createWebDriver();
        driver.manage().window().maximize();
        userClient = new UserClient();

        long timestamp = System.currentTimeMillis() + Thread.currentThread().getId();
        String email = "test_" + timestamp + "@yandex.ru";
        String password = "password" + timestamp;
        String name = "User" + timestamp;
        user = new User(email, password, name);

        System.out.println("🔹 Сгенерирован пользователь:");
        System.out.println("   Email: " + user.getEmail());
        System.out.println("   Пароль: " + user.getPassword());
        System.out.println("   Имя: " + user.getName());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (accessToken != null) {
            System.out.println("🔸 Удаление пользователя: " + user.getEmail());
            userClient.deleteUser(accessToken);
            System.out.println("✅ Пользователь " + user.getEmail() + " удалён");
        } else {
            System.out.println("⚠️ Токен отсутствует, пользователь не удалён");
        }
    }


}