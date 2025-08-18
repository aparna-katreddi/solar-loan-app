package com.solar.tests;

import com.solar.base.BaseTest;
import com.solar.listeners.TestListener;
import com.solar.utils.ApiUtils;
import com.solar.utils.TestDataProvider;
import org.json.JSONObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static com.solar.utils.AssertUtils.assertEquals;


@Listeners(TestListener.class)
public class QuoteAPITest extends BaseTest{


    @Test(dataProvider = "quoteData", dataProviderClass = TestDataProvider.class)
    public void testCreateQuoteViaAPI(JSONObject data) {
        String dealerId = data.getString("dealerId");
        String firstName = data.getString("firstName");
        String lastName = data.getString("lastName");
        String address = data.getString("address");
        String panel = data.getString("panel");
        int panelCount = data.getInt("panelCount");
        String state = data.getString("state");
        String financeType = data.getString("financeType");
        String solarType = data.getString("solarType");
        String batteryOption = data.getString("batteryOption");
        int year = data.getInt("year");
        double apr = data.getDouble("apr");
        String actualMonthlyRent = ApiUtils.createQuoteAndGetRent(
                baseUrl,
                dealerId,
                firstName,
                lastName,
                address,
                panel,
                panelCount,
                state,
                financeType,
                solarType,
                "No Storage",
                year,
                apr
        );
        String expectedMonthlyRent = ApiUtils.calculateExpectedMonthlyRent(
                panelCount,
                solarType,
                "No Storage",
                year,
                apr
        );
        assertEquals(actualMonthlyRent , expectedMonthlyRent,"Monthly rent should match expected value");

    }

}
