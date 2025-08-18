package com.solar.utils;

import com.aventstack.extentreports.Status;
import com.solar.reports.ExtentTestManager;
import org.testng.Assert;

public class AssertUtils {

    public static void assertEquals(String actual, String expected, String stepDescription) {
        try {
            Assert.assertEquals(actual, expected);
            ExtentTestManager.getTest().log(Status.PASS,
                    stepDescription + "<br><b>Expected:</b> " + expected + "<br><b>Actual:</b> " + actual);
        } catch (AssertionError e) {
            ExtentTestManager.getTest().log(Status.FAIL,
                    stepDescription + "<br><b>Expected:</b> " + expected + "<br><b>Actual:</b> " + actual +
                            "<br><b>Assertion failed:</b> " + e.getMessage());
            throw e;
        }
    }

    public static void assertEquals(int actual, int expected, String stepDescription) {
        try {
            Assert.assertEquals(actual, expected);
            ExtentTestManager.getTest().log(Status.PASS,
                    stepDescription + "<br><b>Expected:</b> " + expected + "<br><b>Actual:</b> " + actual);
        } catch (AssertionError e) {
            ExtentTestManager.getTest().log(Status.FAIL,
                    stepDescription + "<br><b>Expected:</b> " + expected + "<br><b>Actual:</b> " + actual +
                            "<br><b>Assertion failed:</b> " + e.getMessage());
            throw e;
        }
    }

    public static void assertTrue(boolean condition, String message) {
        try {
            Assert.assertTrue(condition);
            ExtentTestManager.getTest().log(Status.PASS, message);
        } catch (AssertionError e) {
            ExtentTestManager.getTest().log(Status.FAIL, message + " — Failed");
            throw e;
        }
    }


}

