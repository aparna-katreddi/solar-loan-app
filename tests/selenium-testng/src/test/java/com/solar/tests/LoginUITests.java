package com.solar.tests;

import com.solar.base.BaseUITest;
import com.solar.listeners.TestListener;
import com.solar.pages.LoginPage;
import com.solar.utils.AssertUtils;
import com.solar.utils.TestDataProvider;
import org.json.JSONObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(TestListener.class)
public class LoginUITests extends BaseUITest {


    @Test(dataProvider = "invalidLoginData", dataProviderClass = TestDataProvider.class)
    public void testInvalidLoginErrorMessages(JSONObject data ) throws InterruptedException {
        String userName=data.getString("userName");
        String password = data.getString("password");
        LoginPage loginPage = new LoginPage(driver);
        AssertUtils.assertEquals(driver.getCurrentUrl(), baseUrl+"/",
                "Step: Navigated to login page");
        loginPage.login(userName,password );
        String actualErrorMessage = loginPage.getErrorMessage();
        AssertUtils.assertEquals(actualErrorMessage, data.getString("errorMessage"),
                String.format("Step: Validating error message for Username: '%s', Password: '%s'", userName, password));
    }
}

