package ru.praktikum.uiburger.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ForgotPasswordPage extends BasePage {

    @FindBy(name = "email")
    private WebElement emailInput;

    @FindBy(xpath = "//button[text()='Восстановить']")
    private WebElement resetButton;

    @FindBy(xpath = "//a[text()='Войти']")
    private WebElement loginLink;

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    @Step("Нажимаем ссылку «Войти» на странице восстановления")
    public void clickLoginLink() {
        loginLink.click();
    }
}