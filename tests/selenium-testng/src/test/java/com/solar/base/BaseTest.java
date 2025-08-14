package com.solar.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import java.util.Properties;
import com.solar.utils.ConfigManager;

public class BaseTest {
    protected WebDriver driver;
    protected Properties config;
    protected String baseUrl = ConfigManager.get("baseUrl");

    @BeforeMethod
    public void setUp() throws Exception {
        //config = new Properties();
        //config.load(new FileInputStream("test-config.properties"));
        //config.load(getClass().getClassLoader().getResourceAsStream("test-config.properties"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
