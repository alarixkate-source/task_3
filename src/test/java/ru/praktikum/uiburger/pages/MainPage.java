package ru.praktikum.uiburger.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage {

    @FindBy(xpath = "//button[text()='Войти в аккаунт']")
    private WebElement loginButtonMain;

    @FindBy(xpath = "//*[contains(@class, 'AppHeader_header__linkText') and text()='Личный Кабинет']")
    private WebElement personalAccountButton;

    @FindBy(xpath = "//div[@class='AppHeader_header__logo__2D0X2']/a")
    private WebElement logo;

    @FindBy(xpath = "//*[contains(@class, 'AppHeader_header__linkText') and text()='Конструктор']")
    private WebElement constructorButton;

    @FindBy(xpath = "//button[contains(@class, 'button_button_type_primary') and text()='Оформить заказ']")
    private WebElement orderButton;

    @FindBy(xpath = "//span[text()='Булки']/parent::div")
    private WebElement bunsTab;

    @FindBy(xpath = "//span[text()='Соусы']/parent::div")
    private WebElement saucesTab;

    @FindBy(xpath = "//span[text()='Начинки']/parent::div")
    private WebElement fillingsTab;

    @FindBy(xpath = "//h2[text()='Булки']")
    private WebElement bunsHeader;

    @FindBy(xpath = "//h2[text()='Соусы']")
    private WebElement saucesHeader;

    @FindBy(xpath = "//h2[text()='Начинки']")
    private WebElement fillingsHeader;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Нажимаем кнопку «Войти в аккаунт» на главной")
    public void clickLoginButtonOnMain() {
        loginButtonMain.click();
    }

    @Step("Нажимаем кнопку «Личный кабинет»")
    public void clickPersonalAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton));
        personalAccountButton.click();
    }

    @Step("Нажимаем на логотип Stellar Burgers")
    public void clickLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(logo));
        try {
            logo.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logo);
        }
        // Ожидаем, что произойдёт переход на главную страницу
        wait.until(ExpectedConditions.urlToBe("https://qa-stellarburgers.education-services.ru/"));
    }

    @Step("Нажимаем на кнопку «Конструктор»")
    public void clickConstructor() {
        wait.until(ExpectedConditions.elementToBeClickable(constructorButton));
        try {
            constructorButton.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", constructorButton);
        }
    }

    @Step("Проверяем, отображается ли кнопка «Оформить заказ»")
    public boolean isOrderButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(orderButton));
            return orderButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Нажимаем на таб «Булки»")
    public void clickBunsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsTab));
        try {
            bunsTab.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bunsTab);
        }
    }

    @Step("Нажимаем на таб «Соусы»")
    public void clickSaucesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesTab));
        try {
            saucesTab.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saucesTab);
        }
    }

    @Step("Нажимаем на таб «Начинки»")
    public void clickFillingsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsTab));
        try {
            fillingsTab.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fillingsTab);
        }
    }

    @Step("Проверяем, отображается ли заголовок раздела «Булки»")
    public boolean isBunsHeaderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(bunsHeader)).isDisplayed();
    }

    @Step("Проверяем, отображается ли заголовок раздела «Соусы»")
    public boolean isSaucesHeaderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(saucesHeader)).isDisplayed();
    }

    @Step("Проверяем, отображается ли заголовок раздела «Начинки»")
    public boolean isFillingsHeaderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(fillingsHeader)).isDisplayed();
    }
}