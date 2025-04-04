package br.com.enviodeemails.core;

import br.com.enviodeemails.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", Config.get("chrome_driver_path"));

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("user-data-dir=" + Config.get("chrome_user_data"));
            options.addArguments("--profile-directory=" + Config.get("chrome_profile"));
            options.addArguments("--remote-allow-origins=*");

            driver = new ChromeDriver(options);
        }
        return driver;
    }

    public static void fecharDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}