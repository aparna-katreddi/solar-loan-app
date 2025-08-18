package com.solar.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestDataProvider {

    @DataProvider(name = "quoteData")
    public static Object[][] getQuoteData() throws Exception {
        return loadTestDataFromConfigKey("quotesData");
    }

    @DataProvider(name = "invalidLoginData")
    public static Object[][] getInvalidLoginData() throws Exception {
        return loadTestDataFromConfigKey("invalidLoginData");
    }

    private static Object[][] loadTestDataFromConfigKey(String configKey) throws Exception {
        String filePath = ConfigManager.get(configKey);
        System.out.println("Loading test data from: *****" + filePath);
        // Load file as resource stream
        try (InputStream inputStream = TestDataProvider.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new RuntimeException("Resource not found: " + filePath);
            }
            String content = readInputStreamAsString(inputStream);
            //String content = new String(Files.readAllBytes(Paths.get(filePath)));
            System.out.println("Content length: " + content.length());
            JSONArray jsonArray = new JSONArray(content);
            System.out.println("Number of test cases: " + jsonArray.length());
            Object[][] testData = new Object[jsonArray.length()][1];
            for (int i = 0; i < jsonArray.length(); i++) {
                testData[i][0] = jsonArray.getJSONObject(i);
            }
            return testData;
        }
    }

    private static String readInputStreamAsString(InputStream is) throws IOException {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}

