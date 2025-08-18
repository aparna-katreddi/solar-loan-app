package com.solar.base;


import com.solar.reports.ExtentTestManager;
import com.solar.utils.ApiUtils;
import com.solar.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import java.lang.reflect.Method;
import java.util.Properties;
import com.solar.utils.ConfigManager;

public class BaseTest {
    protected WebDriver driver;
    protected Properties config;
    protected String baseUrl = ConfigManager.get("baseUrl");
    protected String username = System.getProperty("TEST_USERNAME");
    protected String password = System.getProperty("TEST_PASSWORD");

    @BeforeMethod
    public void setUp(Method method) {
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("isHeadless" , "false"));
        System.out.println("Running test: " + method.getName());
        System.out.println("is headless: " +isHeadless);
        String browser = System.getProperty("browser", "chrome");
        System.out.println("browser: " +browser);
        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                }
                driver = new ChromeDriver(chromeOptions);
                System.out.println("driver: " +driver);
                break;
        }
        DriverManager.setDriver(driver);
        driver.manage().window().maximize();
        String dealerId = ApiUtils.loginAndGetDealerId(baseUrl, username, password);
        System.out.println("baseUrl: " +baseUrl);
        System.out.println("dealerId: " + dealerId);
        System.out.println("username: " + username);
         System.out.println("password: " + password);
        driver.get(baseUrl);
        ApiUtils.injectDealerIdIntoLocalStorage(driver, dealerId);
        driver.get(baseUrl + "/quote");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            ExtentTestManager.getTest().info("🧹 Closing browser");
            driver.quit();
            DriverManager.unload();
        }
    }
}
