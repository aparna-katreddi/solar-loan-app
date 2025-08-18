package com.solar.tests;

import com.solar.base.BaseTest;
import com.solar.listeners.TestListener;
import com.solar.pages.QuotePage;
import com.solar.utils.ApiUtils;
import com.solar.utils.AssertUtils;
import com.solar.utils.TestDataProvider;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.util.Objects;

@Listeners(TestListener.class)
public class CreateQuoteUITests extends BaseTest {

    @Test(dataProvider = "quoteData", dataProviderClass = TestDataProvider.class)
    public void testCreateQuoteFromUIAndValidateRents(JSONObject data) {
        String dealerId = data.getString("dealerId");
        String firstName = data.getString("firstName");
        String lastName = data.getString("lastName");
        String address = data.getString("address");
        String panel = data.getString("panel");
        int panelCount = data.getInt("panelCount");
        String state = data.getString("state");
        String financeType = "Loan";
        String solarType = data.getString("solarType");
        String batteryOption = data.getString("batteryOption");
        int year = data.getInt("year");
        double apr = data.getDouble("apr");
        QuotePage quotePage = new QuotePage(driver);
        quotePage.fillForm(firstName, lastName, address, state, panel, panelCount, solarType, financeType, year, apr);
        quotePage.clickNext();
        AssertUtils.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/quote"), "Navigated to /loan page");
        String currentUrl = driver.getCurrentUrl();
        String quoteId = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
        Response response = ApiUtils.getQuoteById(baseUrl, quoteId);
        String apiMonthlyRent = response.jsonPath().getString("monthlyRent");
        String actualMonthlyRent = quotePage.getDisplayedMonthlyRent();
        AssertUtils.assertEquals(actualMonthlyRent, "Estimated Monthly Rent: $"+apiMonthlyRent, "Monthly Rent matches API response");
        double basePrice = panelCount * 500;
        double taxCredit  = 0.30 * basePrice;
        double netLoanAmount = basePrice - taxCredit;
        double monthlyRate = apr/100/12;
        double monthlyRent = (netLoanAmount * monthlyRate) /
                (1 - Math.pow(1 + monthlyRate, - (year*12)));
        String expectedMonthlyRent = String.format("%.2f", monthlyRent);
        AssertUtils.assertEquals(actualMonthlyRent, "Estimated Monthly Rent: $"+expectedMonthlyRent,"Calculated Monthly Rent do not match");

    }
}

