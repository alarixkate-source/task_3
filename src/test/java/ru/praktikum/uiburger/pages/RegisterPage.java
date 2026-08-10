package ru.praktikum.uiburger.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends BasePage {

    @FindBy(xpath = "//div[contains(@class, 'input') and .//label[text()='Имя']]//input")
    private WebElement nameInput;

    @FindBy(xpath = "//div[contains(@class, 'input') and .//label[text()='Email']]//input")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@name='Пароль']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[text()='Зарегистрироваться']")
    private WebElement registerButton;

    @FindBy(xpath = "//a[text()='Войти']")
    private WebElement loginLink;

    @FindBy(xpath = "//p[contains(@class, 'input__error')]")
    private WebElement errorMessage;

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Step("Вводим имя: {name}")
    public void setName(String name) {
        wait.until(ExpectedConditions.visibilityOf(nameInput));
        nameInput.sendKeys(name);
    }

    @Step("Вводим email: {email}")
    public void setEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.sendKeys(email);
    }

    @Step("Вводим пароль")
    public void setPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.sendKeys(password);
    }

    @Step("Нажимаем кнопку «Зарегистрироваться»")
    public void clickRegisterButton() {
        registerButton.click();
    }

    @Step("Нажимаем ссылку «Войти» на странице регистрации")
    public void clickLoginLink() {
        loginLink.click();
    }

    @Step("Получаем текст сообщения об ошибке")
    public String getErrorMessageText() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText();
    }

    @Step("Выполняем регистрацию: имя={name}, email={email}")
    public void register(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
    }
}