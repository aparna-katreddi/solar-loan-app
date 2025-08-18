package com.solar.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.solar.reports.ExtentReportManager;
import com.solar.reports.ExtentTestManager;
import com.solar.utils.ScreenshotUtils;
import org.testng.*;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ExtentTestManager.startSuite(context.getSuite().getName()); // e.g. Solar Loan Suite
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getTestContext().getName();     // E2E Tests
        String className = result.getTestClass().getRealClass().getSimpleName();
        String methodName = result.getMethod().getMethodName();
        ExtentTest testNode = ExtentTestManager.startMethodNode(testName, className, methodName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().log(Status.PASS, "Test passed");
    }


    @Override
    public void onTestFailure(ITestResult result) {
        try {
            // Capture screenshot and get its path
            String screenshotPath = ScreenshotUtils.captureScreenshot(result.getMethod().getMethodName());
            // Log failure + attach screenshot
            ExtentTestManager.getTest().fail(
                    "Test failed: " + result.getThrowable(),
                    com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
            );
        } catch (Exception e) {
            // If screenshot capture itself fails, at least log the failure
            ExtentTestManager.getTest().fail("Test failed but screenshot capture failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.getInstance().flush();
    }
}

