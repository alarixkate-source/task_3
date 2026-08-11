package ru.praktikum.uiburger.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverCreator {
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
           // 1. Сначала ищем в системных свойствах (-D...)
           String driverPath = System.getProperty("webdriver.chrome.driver");
           // 2. Если не нашли, пробуем переменную окружения
           if (driverPath == null || driverPath.isEmpty()) {
               driverPath = System.getenv("webdriver.chrome.driver");
           }
           // 3. Если всё равно пусто – ошибка с подсказкой
           if (driverPath == null || driverPath.isEmpty()) {
               throw new IllegalStateException(
                       "Для Chrome необходимо задать:\n" +
                               "  - системное свойство: -Dwebdriver.chrome.driver=путь\\chromedriver.exe\n" +
                               "  - или переменную окружения: webdriver.chrome.driver=путь\\chromedriver.exe"
               );
           }
           System.setProperty("webdriver.chrome.driver", driverPath);
           return new ChromeDriver();
    }

    private static WebDriver createYandexDriver() {
        // Путь к драйверу и браузеру задаются через переменные окружения
        String driversDir = System.getenv("WEBDRIVERS");
        String driverFileName = System.getenv("YANDEX_BROWSER_DRIVER_FILENAME");
        String browserPath = System.getenv("YANDEX_BROWSER_PATH");

        if (driversDir == null || driversDir.isEmpty() ||
                driverFileName == null || driverFileName.isEmpty() ||
                browserPath == null || browserPath.isEmpty()) {
            throw new IllegalStateException(
                    "Для Яндекс.Браузера задайте переменные окружения:\n" +
                            "  WEBDRIVERS – путь к папке с драйверами\n" +
                            "  YANDEX_BROWSER_DRIVER_FILENAME – имя файла драйвера\n" +
                            "  YANDEX_BROWSER_PATH – путь к исполняемому файлу браузера"
            );
        }

        String fullDriverPath = String.format("%s/%s", driversDir, driverFileName);
        System.setProperty("webdriver.chrome.driver", fullDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.setBinary(browserPath);
        return new ChromeDriver(options);
    }
}