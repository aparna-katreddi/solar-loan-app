package com.solar.tests;

import com.solar.base.BaseTest;
import com.solar.pages.LoginPage;
import com.solar.utils.AssertUtils;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;


public class LoginUITests extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
                {"", "","Username and password are required"},
                {"invalidUser", "","Username and password are required"},
                {"", "invalidPwd","Username and password are required"},
                {"invalidUser", "invalidPwd","Invalid credentials"},
        };
    }

    @Test(dataProvider = "loginData")
    public void testInvalidLoginErrorMessages(String username, String password, String errorMessage) throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        AssertUtils.assertEquals(driver.getCurrentUrl(), baseUrl,
                "Step: Navigated to login page");
        loginPage.login(username, password);
        String actualErrorMessage = loginPage.getErrorMessage();
        AssertUtils.assertEquals(actualErrorMessage, errorMessage,
                String.format("Step: Validating error message for Username: '%s', Password: '%s'", username, password));
    }
}

