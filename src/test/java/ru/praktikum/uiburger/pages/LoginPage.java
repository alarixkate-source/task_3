package ru.praktikum.uiburger.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//div[contains(@class, 'input') and .//label[text()='Email']]//input")
    private WebElement emailInput;

    @FindBy(xpath = "//div[contains(@class, 'input') and .//label[text()='Пароль']]//input")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[contains(@class, 'button_button_type_primary') and text()='Войти']")
    private WebElement loginButton;

    @FindBy(xpath = "//a[text()='Зарегистрироваться']")
    private WebElement registerLink;

    @FindBy(xpath = "//a[text()='Восстановить пароль']")
    private WebElement forgotPasswordLink;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Вводим email: {email}")
    public void setEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    @Step("Вводим пароль")
    public void setPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    @Step("Нажимаем кнопку «Войти»")
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        try {
            loginButton.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        }
    }

    @Step("Переходим по ссылке «Зарегистрироваться»")
    public void clickRegisterLink() {
        registerLink.click();
    }

    @Step("Выполняем вход: email={email}")
    public void login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();

        // Явное ожидание редиректа или появления ошибки
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlToBe("https://qa-stellarburgers.education-services.ru/"),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(@class, 'input__error')]"))
            ));
        } catch (Exception e) {

        }
    }

    @Step("Проверяем, что отображается форма логина")
    public boolean isLoginFormDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emailInput));
            return emailInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}