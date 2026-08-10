package ru.praktikum.uiburger.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverCreator {
// Ну все я сдалась. Указываю прямой путь к драйверам для хрома и яндекса, по другому шайтан машина не заводится)) пусть хоть как то работает))
    // Явный путь к драйверу для Chrome
    private static final String CHROME_DRIVER_PATH = "C:\\tools\\chromedriver-win64-151\\chromedriver.exe";

    // Значения по умолчанию для Яндекс.Браузера (если переменные окружения не заданы)
    private static final String DEFAULT_YANDEX_DRIVER_DIR = "C:\\tools\\chromedriver-win64-148";
    private static final String DEFAULT_YANDEX_DRIVER_FILENAME = "chromedriver.exe";
    private static final String DEFAULT_YANDEX_BROWSER_PATH =
            "C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe";

    public static WebDriver createWebDriver() {
        String browser = System.getProperty("browser");
        if (browser == null) {
            return createChromeDriver();
        }
        switch (browser.toLowerCase()) {
            case "yandex":
                return createYandexDriver();
            case "chrome":
            default:
                return createChromeDriver();
        }
    }

    private static WebDriver createChromeDriver() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        return new ChromeDriver();
    }

    private static WebDriver createYandexDriver() {
        // Читаем переменные окружения
        String driversDir = System.getenv("WEBDRIVERS");
        String driverFileName = System.getenv("YANDEX_BROWSER_DRIVER_FILENAME");
        String browserPath = System.getenv("YANDEX_BROWSER_PATH");

        // Если переменные не заданы, используем значения по умолчанию
        if (driversDir == null || driversDir.isEmpty()) {
            driversDir = DEFAULT_YANDEX_DRIVER_DIR;
            System.out.println("Переменная WEBDRIVERS не задана, используем: " + driversDir);
        }
        if (driverFileName == null || driverFileName.isEmpty()) {
            driverFileName = DEFAULT_YANDEX_DRIVER_FILENAME;
            System.out.println("Переменная YANDEX_BROWSER_DRIVER_FILENAME не задана, используем: " + driverFileName);
        }
        if (browserPath == null || browserPath.isEmpty()) {
            browserPath = DEFAULT_YANDEX_BROWSER_PATH;
            System.out.println("️Переменная YANDEX_BROWSER_PATH не задана, используем: " + browserPath);
        }

        String fullDriverPath = String.format("%s/%s", driversDir, driverFileName);
        System.setProperty("webdriver.chrome.driver", fullDriverPath);
        System.out.println("Запуск Яндекс.Браузера с драйвером: " + fullDriverPath);
        System.out.println("Путь к браузеру: " + browserPath);

        ChromeOptions options = new ChromeOptions();
        options.setBinary(browserPath);
        return new ChromeDriver(options);
    }
}