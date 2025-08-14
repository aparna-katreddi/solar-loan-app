package com.solar.utils;

import com.aventstack.extentreports.Status;
import com.solar.reports.ExtentTestManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebUIUtils {

    public static void clearField(WebDriver driver, By locator, String fieldName) {
        WebElement element = driver.findElement(locator);
        element.clear();
        ExtentTestManager.getTest().log(Status.INFO, "Cleared " + fieldName + " field");
    }

    public static void typeText(WebDriver driver, By locator, String text, String fieldName) {
        WebElement element = driver.findElement(locator);
        element.sendKeys(text);
        ExtentTestManager.getTest().log(Status.INFO, "Entered '" + text + "' into " + fieldName + " field");
    }

    public static void click(WebDriver driver, By locator, String elementName) {
        WebElement element = driver.findElement(locator);
        element.click();
        ExtentTestManager.getTest().log(Status.INFO, "Clicked on " + elementName);
    }

    public static String getText(WebDriver driver, By locator, String elementName) {
        WebElement element = driver.findElement(locator);
        String text = element.getText();
        ExtentTestManager.getTest().log(Status.INFO, "Retrieved text from " + elementName + ": " + text);
        return text;
    }

    public static String waitForVisibleText(WebDriver driver, By locator, String elementName) {
        int timeout = ConfigManager.getInt("errorMessageExplicitWaitSeconds");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        String text = element.getText();
        ExtentTestManager.getTest().log(Status.INFO,
                "Captured text from " + elementName + ": " + text);
        return text;
    }
}

