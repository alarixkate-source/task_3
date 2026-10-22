package ru.praktikum.uiburger.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.praktikum.uiburger.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты переключения разделов в конструкторе")
public class ConstructorTest extends BaseTest {

    @Test
    @DisplayName("Переключение на раздел 'Булки'")
    @Description("Проверяет, что после клика на таб 'Булки' он становится активным")
    public void switchToBuns() {
        driver.get(BASE_URL);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickBunsTab();
        assertTrue(mainPage.isBunsTabActive(), "Таб 'Булки' не активен");
    }

    @Test
    @DisplayName("Переключение на раздел 'Соусы'")
    @Description("Проверяет, что после клика на таб 'Соусы' таб активен")
    public void switchToSauces() {
        driver.get(BASE_URL);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickSaucesTab();
        assertTrue(mainPage.isSaucesTabActive(), "Таб 'Соусы' не активен");
    }

    @Test
    @DisplayName("Переключение на раздел 'Начинки'")
    @Description("Проверяет, что после клика на таб 'Начинки' таб активен")
    public void switchToFillings() {
        driver.get(BASE_URL);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickFillingsTab();
        assertTrue(mainPage.isFillingsTabActive(), "таб 'Начинки' не активен");
    }
}