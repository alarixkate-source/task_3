package ru.praktikum.uiburger.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends BasePage {

    @FindBy(xpath = "//a[text()='Профиль']")
    private WebElement profileLink;

    @FindBy(xpath = "//a[text()='История заказов']")
    private WebElement orderHistoryLink;

    @FindBy(xpath = "//button[text()='Выход']")
    private WebElement logoutButton;

    @FindBy(xpath = "//input[@name='Name']")
    private WebElement nameInput;

    @FindBy(xpath = "//input[@name='name' and @type='text']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@type='password' and @disabled]")
    private WebElement passwordInput;

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    @Step("Проверяем, отображается ли ссылка «Профиль»")
    public boolean isProfileLinkDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(profileLink)).isDisplayed();
    }

    @Step("Проверяем, отображается ли ссылка «История заказов»")
    public boolean isOrderHistoryLinkDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(orderHistoryLink)).isDisplayed();
    }

    @Step("Проверяем, отображается ли кнопка «Выход»")
    public boolean isLogoutButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(logoutButton)).isDisplayed();
    }

    @Step("Получаем имя пользователя из профиля")
    public String getUserName() {
        wait.until(ExpectedConditions.visibilityOf(nameInput));
        return nameInput.getAttribute("value");
    }

    @Step("Получаем email пользователя из профиля")
    public String getUserEmail() {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        return emailInput.getAttribute("value");
    }

    @Step("Проверяем, что поле пароля замаскировано (значение не равно реальному паролю)")
    public boolean isPasswordMasked(String actualPassword) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        String masked = passwordInput.getAttribute("value");
        // Сравниваем, что значение не равно реальному паролю и длина > 0
        return !masked.equals(actualPassword) && !masked.isEmpty();
    }

    @Step("Нажимаем кнопку «Выйти»")
    public void clickLogoutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        logoutButton.click();
    }
}