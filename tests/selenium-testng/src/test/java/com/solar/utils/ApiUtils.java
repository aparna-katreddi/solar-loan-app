package com.solar.utils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class ApiUtils {

    public static String loginAndGetDealerId(String baseUrl, String username, String password) {
        Response response = RestAssured.given()
                .contentType("application/json")
                .body("{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}")
                .post(baseUrl + "/api/login");
        response.then().statusCode(200);
        return response.jsonPath().getString("userId");  // dealerId
    }

    public static void injectDealerIdIntoLocalStorage(WebDriver driver, String dealerId) {
        ((JavascriptExecutor) driver).executeScript(
                "window.localStorage.setItem('dealerId', arguments[0]);", dealerId);
    }

    public static String createQuoteAndGetRent(
            String baseUrl, String dealerId, String firstName, String lastName, String address, String panel,
            int panelCount, String state, String financeType, String solarType, String batteryOption,
            int year, double apr
    ) {
        JSONObject payload = new JSONObject();
        payload.put("dealerId", dealerId);
        payload.put("firstName", firstName);
        payload.put("lastName", lastName);
        payload.put("address", address);
        payload.put("panel", panel);
        payload.put("panelCount", panelCount);
        payload.put("state", state);
        payload.put("financeType", financeType);
        payload.put("solarType", solarType);
        payload.put("batteryOption", batteryOption);
        payload.put("year", year);
        payload.put("apr", apr);
        Response response = RestAssured
                .given()
                .baseUri(baseUrl)
                .basePath("/api/quote")
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .post();
        response.then().statusCode(200);
        JsonPath jPath = response.jsonPath();
        String responseSr = response.getBody().asString();
        System.out.println("response.jsonpath() is::: "+response.jsonPath());
        System.out.println("response.getBody() ::::: "+response.getBody().asString());
        return response.jsonPath().getString("monthlyRent");
    }

    public static Response getQuoteById(String baseUrl, String quoteId) {
        return RestAssured
                .given()
                .baseUri(baseUrl)
                .when()
                .get("/api/quote/" + quoteId)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    public static String calculateExpectedMonthlyRent(int panelCount, String solarType, String batteryOption, int year, double apr) {
        double panelPrice = 500;
        double basePrice = panelCount * panelPrice;

        // Add battery price
        double batteryPrice = 0;
        if (solarType.equals("Solar + Storage")) {
            if (batteryOption.equals("Tesla Powerwall")) batteryPrice = 12000;
            else if (batteryOption.equals("Enphase IQ")) batteryPrice = 10000;
        }

        double systemPrice = basePrice + batteryPrice;
        double taxCredit = 0.30 * systemPrice;
        double netLoanAmount = systemPrice - taxCredit;
        double monthlyRate = apr / 100 / 12;
        int totalMonths = year * 12;

        double monthlyRent = (netLoanAmount * monthlyRate) /
                (1 - Math.pow(1 + monthlyRate, -totalMonths));

        return String.format("%.2f", monthlyRent);
    }


}

