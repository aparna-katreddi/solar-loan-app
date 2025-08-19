package com.solar.pages;

import com.solar.utils.WebUIUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;

    private final By usernameField = By.xpath("//input[@placeholder='Username']");
    private final By passwordField = By.xpath("//input[@placeholder='Password']");
    private final By loginButton = By.xpath("//button[text()='Login']");
    private final By loginErrorMessage = By.xpath("//p[contains(@style, 'color: red')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public QuotePage loginAndNavigateToQuotePage(String username, String password) {
        WebUIUtils.clearField(driver, usernameField, "username");
        WebUIUtils.typeText(driver, usernameField, username, "username");
        WebUIUtils.clearField(driver, passwordField, "password");
        WebUIUtils.typeText(driver, passwordField, password, "password");
        WebUIUtils.click(driver, loginButton, "Login button");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/quote"));
        return new QuotePage(driver);
    }

    public void login(String username, String password) {
        WebUIUtils.clearField(driver, usernameField, "username");
        WebUIUtils.typeText(driver, usernameField, username, "username");
        WebUIUtils.clearField(driver, passwordField, "password");
        WebUIUtils.typeText(driver, passwordField, password, "password");
        WebUIUtils.click(driver, loginButton, "Login button");
    }

    public String getErrorMessage() {
        return WebUIUtils.waitForVisibleText(driver, loginErrorMessage, "Login Error Message");
    }
}
